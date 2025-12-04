package hn.com.tigo.equipmentaccessoriesbilling.services;

import hn.com.tigo.equipmentaccessoriesbilling.entities.*;
import hn.com.tigo.equipmentaccessoriesbilling.exceptions.BadRequestException;
import hn.com.tigo.equipmentaccessoriesbilling.models.*;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.*;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBulkAuthorizeService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BulkAuthorizeServiceImpl implements IBulkAuthorizeService {

    private final IBillingRepository billingRepository;
    private final IConfigParametersService configParametersService;
    private final IControlAuthEmissionRepository controlAuthEmissionRepository;
    private final IControlUserPermissionsRepository controlUserPermissionsRepository;
    private final ITypeUserRepository typeUserRepository;
    private final IUserBranchOfficesRepository userBranchOfficesRepository;

    private final ILogsService logsService;

    public BulkAuthorizeServiceImpl(
            IBillingRepository billingRepository,
            IConfigParametersService configParametersService,
            IControlAuthEmissionRepository controlAuthEmissionRepository,
            IControlUserPermissionsRepository controlUserPermissionsRepository,
            ITypeUserRepository typeUserRepository, IUserBranchOfficesRepository userBranchOfficesRepository,
            ILogsService logsService
    ) {
        this.billingRepository = billingRepository;
        this.configParametersService = configParametersService;
        this.controlAuthEmissionRepository = controlAuthEmissionRepository;
        this.controlUserPermissionsRepository = controlUserPermissionsRepository;
        this.typeUserRepository = typeUserRepository;
        this.userBranchOfficesRepository = userBranchOfficesRepository;
        this.logsService = logsService;
    }

    /**
     * Obtiene las prefacturas pendientes de autorización que puede ver un usuario dado su tipo y permisos.
     *
     * @param pageable paginación
     * @param seller   usuario solicitante
     * @return página de modelos de prefacturas pendientes de autorización
     * @throws BadRequestException si el usuario no existe, su tipo es inválido o no tiene permisos para ver facturas
     */
    @Override
    public Page<BillingModel> getBulkAuthorize(Pageable pageable, String seller) {

        // 1) Construir contexto de usuario (valida existencia, tipo y sucursales)
        final String sellerUpper = Optional.ofNullable(seller)
                .map(String::toUpperCase)
                .orElseThrow(() -> new BadRequestException(String.format(Constants.ERROR_USER_NOT_EXISTS, "null")));
        final UserVisibilityContext userCtx = buildUserVisibilityContext(sellerUpper);

        // 2) Cargar parámetros de visibilidad por tipo de usuario desde app 1002
        final VisibilityParams visParams = loadVisibilityParams();

        // 3) Resolver la consulta según reglas de visibilidad
        Page<BillingEntity> page = resolveVisibilityQuery(
                pageable,
                sellerUpper,
                userCtx,
                visParams
        );

        // 4) Mapear entidades a modelos
        return page.map(BillingEntity::entityToModel);
    }


    @Override
    public Page<BillingModel> searchByCustomerOrCustomerId(Pageable pageable,
                                                           String seller,
                                                           String customer,
                                                           String customerId) {

        if (StringUtils.isBlank(customer) && StringUtils.isBlank(customerId)) {
            throw new BadRequestException("You must provide at least one of the parameters: customer or customerId.");
        }

        final String sellerUpper = Optional.ofNullable(seller)
                .map(String::toUpperCase)
                .orElseThrow(() -> new BadRequestException(String.format(Constants.ERROR_USER_NOT_EXISTS, "null")));
        final UserVisibilityContext userCtx = buildUserVisibilityContext(sellerUpper);
        final VisibilityParams visParams = loadVisibilityParams();

        final String type = userCtx.typeUser.getTypeUser();

        if ("CAJERO".equalsIgnoreCase(type)) {
            return Page.<BillingEntity>empty(pageable).map(BillingEntity::entityToModel);
        }

        Page<BillingEntity> page;
        if (visParams.seeAllInvoices.contains(type)) {
            page = billingRepository.searchInvoicesByCustomerOrCustomerId(pageable, 0L, customer, customerId);
        } else if (visParams.seeOnlyTheirInvoices.contains(type)) {
            List<Long> branchIds = ensureBranchIds(userCtx);
            page = billingRepository.searchInvoicesByCustomerOrCustomerIdBySellerBranchOffice(
                    pageable, 0L, sellerUpper, branchIds, customer, customerId);
        } else if (visParams.seeAllInvoicesBranchOffice.contains(type)) {
            List<Long> branchIds = ensureBranchIds(userCtx);
            page = billingRepository.searchInvoicesByCustomerOrCustomerIdByBranchOffice(
                    pageable, 0L, branchIds, customer, customerId);
        } else {
            throw new BadRequestException("User does not have permission to view invoices");
        }

        return page.map(BillingEntity::entityToModel);
    }


    @Override
    @Transactional
    public BulkAuthorizeResponse authorizeInvoices(BulkAuthorizeRequest request) {
        validateRequest(request);
        AuthContext ctx = buildAuthContext(request);
        Map<Long, BillingEntity> invoices = preloadInvoices(request.getInvoiceIds());

        BulkAuthorizeResponse response = new BulkAuthorizeResponse();
        response.setTotalRequested(ctx.ids.size());

        List<ControlAuthEmissionEntity> controlRows = new ArrayList<>();
        List<BillingEntity> toUpdate = new ArrayList<>();

        for (Long id : ctx.ids) {
            long start = System.currentTimeMillis();
            try {
                BillingEntity entity = invoices.get(id);
                Optional<String> validationError = validateInvoiceForAuthorization(entity, ctx);
                if (validationError.isPresent()) {
                    response.getResults().add(new BulkAuthorizeItemResult(id, false, validationError.get()));
                    continue;
                }

                ControlAuthEmissionEntity ctrl = buildControlRow(id, ctx);
                controlRows.add(ctrl);

                markAuthorizedInMemory(entity, ctx.now, ctx.user);
                toUpdate.add(entity);

                response.getResults().add(new BulkAuthorizeItemResult(id, true, "Authorized successfully."));
            } catch (Exception e) {
                logsService.saveLog(7, id, "Error occurred while creating the authorization: " + e.getMessage(),
                        ctx.user, System.currentTimeMillis() - start);
                response.getResults().add(new BulkAuthorizeItemResult(id, false, e.getMessage()));
            }
        }

        if (!controlRows.isEmpty()) controlAuthEmissionRepository.saveAll(controlRows);
        if (!toUpdate.isEmpty()) billingRepository.saveAll(toUpdate);

        int ok = (int) response.getResults().stream().filter(BulkAuthorizeItemResult::isSuccess).count();
        response.setTotalAuthorized(ok);
        response.setTotalFailed(response.getTotalRequested() - ok);
        return response;
    }

    // =========================================================
    // ======= SUBMODULES/HELPERS OBTENER FACTURAS  =========
    // =========================================================

    /**
     * Contexto de visibilidad del usuario autenticado para consultas de prefacturas.
     */
    private static final class UserVisibilityContext {
        final ControlUserPermissionsEntity userPerm;
        final TypeUserEntity typeUser;
        final List<Long> branchOfficeIds;

        UserVisibilityContext(ControlUserPermissionsEntity userPerm,
                              TypeUserEntity typeUser,
                              List<Long> branchOfficeIds) {
            this.userPerm = userPerm;
            this.typeUser = typeUser;
            this.branchOfficeIds = branchOfficeIds;
        }

        boolean isExemptFromBranchValidation() {
            String t = typeUser.getTypeUser();
            return "ADMINISTRATIVO".equalsIgnoreCase(t) || "CREDITOS".equalsIgnoreCase(t);
        }
    }

    /**
     * Parámetros de visibilidad por tipo de usuario (fuente: configuración app 1002).
     */
    private static final class VisibilityParams {
        final Set<String> seeAllInvoices;
        final Set<String> seeOnlyTheirInvoices;
        final Set<String> seeAllInvoicesBranchOffice;

        VisibilityParams(Set<String> seeAllInvoices,
                         Set<String> seeOnlyTheirInvoices,
                         Set<String> seeAllInvoicesBranchOffice) {
            this.seeAllInvoices = seeAllInvoices;
            this.seeOnlyTheirInvoices = seeOnlyTheirInvoices;
            this.seeAllInvoicesBranchOffice = seeAllInvoicesBranchOffice;
        }
    }

    /**
     * Crea el contexto del usuario y valida:
     * - Existe el registro en ControlUserPermissions.
     * - Su TypeUser es válido.
     * - Si no es ADMINISTRATIVO/CREDITOS, debe tener sucursales activas asociadas.
     */
    private UserVisibilityContext buildUserVisibilityContext(String sellerUpper) {
        ControlUserPermissionsEntity userPerm = controlUserPermissionsRepository.findByUserName(sellerUpper);
        if (userPerm == null) {
            throw new BadRequestException(String.format(Constants.ERROR_USER_NOT_EXISTS, sellerUpper));
        }

        TypeUserEntity typeUser = typeUserRepository.findById(userPerm.getTypeUser()).orElse(null);
        if (typeUser == null) {
            throw new BadRequestException(Constants.ERROR_USER_TYPE);
        }

        List<UserBranchOfficesEntity> branches = userBranchOfficesRepository.findByIdUserActivated(userPerm.getIdUser());
        boolean hasBranches = branches != null && !branches.isEmpty();

        if (!hasBranches && !("ADMINISTRATIVO".equalsIgnoreCase(typeUser.getTypeUser())
                || "CREDITOS".equalsIgnoreCase(typeUser.getTypeUser()))) {
            throw new BadRequestException(Constants.ERROR_USER_BRANCHOFFICE);
        }

        List<Long> branchIds = hasBranches
                ? branches.stream().map(UserBranchOfficesEntity::getIdBranchOffices).collect(Collectors.toList())
                : Collections.emptyList();

        return new UserVisibilityContext(userPerm, typeUser, branchIds);
    }

    /**
     * Lee y normaliza los parámetros de visibilidad por tipo de usuario desde la app 1002.
     */
    private VisibilityParams loadVisibilityParams() {
        List<ConfigParametersModel> listTypeUser = configParametersService.getByIdApplication(1002L);

        Map<String, List<String>> grouped = new HashMap<>();
        for (ConfigParametersModel p : listTypeUser) {
            grouped.computeIfAbsent(p.getParameterName(), k -> new ArrayList<>()).add(p.getParameterValue());
        }

        Set<String> userSeeAllInvoice = new HashSet<>(grouped.getOrDefault("USERS_SEE_ALL_INVOICES", Collections.emptyList()));
        Set<String> userSeeOnlyTheirInvoices = new HashSet<>(grouped.getOrDefault("USERS_SEE_ONLY_THEIR_INVOICES", Collections.emptyList()));
        Set<String> userSeeAllInvoiceBranchOffice = new HashSet<>(grouped.getOrDefault("USERS_SEE_ALL_INVOICES_BRACHOFFICE", Collections.emptyList()));

        return new VisibilityParams(userSeeAllInvoice, userSeeOnlyTheirInvoices, userSeeAllInvoiceBranchOffice);
    }

    /**
     * Resuelve la consulta al repositorio según el tipo de usuario y sus permisos parametrizados.
     */
    private Page<BillingEntity> resolveVisibilityQuery(Pageable pageable,
                                                       String sellerUpper,
                                                       UserVisibilityContext userCtx,
                                                       VisibilityParams visParams) {

        String type = userCtx.typeUser.getTypeUser();

        // CAJERO no tiene permiso para ver/autorizar facturas
        if ("CAJERO".equalsIgnoreCase(type)) {
            return Page.empty(pageable);
        }

        // Ve todas las facturas pendientes
        if (visParams.seeAllInvoices.contains(type)) {
            return billingRepository.getCreditBill(pageable, 0L);
        }

        // Ve solo sus facturas en sus sucursales
        if (visParams.seeOnlyTheirInvoices.contains(type)) {
            List<Long> branchIds = ensureBranchIds(userCtx);
            return billingRepository.getCreditInvoicesBySellerBranchOffice(pageable, 0L, sellerUpper, branchIds);
        }

        // Ve todas las facturas de sus sucursales
        if (visParams.seeAllInvoicesBranchOffice.contains(type)) {
            List<Long> branchIds = ensureBranchIds(userCtx);
            return billingRepository.getCreditInvoicesByBranchOffice(pageable, 0L, branchIds);
        }

        // Sin coincidencias en reglas
        throw new BadRequestException("User does not have permission to view invoices");
    }


    /**
     * Garantiza que el usuario tenga sucursales cuando la regla lo requiere.
     * Si el tipo es exento (ADMINISTRATIVO/CREDITOS), retorna lista vacía (no usada).
     */
    private List<Long> ensureBranchIds(UserVisibilityContext userCtx) {
        if (userCtx.isExemptFromBranchValidation()) {
            return Collections.emptyList();
        }
        if (userCtx.branchOfficeIds == null || userCtx.branchOfficeIds.isEmpty()) {
            throw new BadRequestException(Constants.ERROR_USER_BRANCHOFFICE);
        }
        return userCtx.branchOfficeIds;
    }

    // =========================================================
    // ======= SUBMÉTODOS/HELPERS AUTORIZACION MASIVA  =========
    // =========================================================

    /**
     * Validaciones rápidas del request
     */
    private void validateRequest(BulkAuthorizeRequest request) {
        if (request == null || request.getInvoiceIds() == null || request.getInvoiceIds().isEmpty()) {
            throw new BadRequestException("Invoice IDs list cannot be empty.");
        }
        if (StringUtils.isBlank(request.getUser())) {
            throw new BadRequestException("user is required.");
        }
    }

    /**
     * Carga config, permisos de usuario, normaliza IDs y devuelve un contexto reusable
     */
    private AuthContext buildAuthContext(BulkAuthorizeRequest request) {
        String user = request.getUser().toUpperCase();
        List<Long> ids = request.getInvoiceIds().stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());

        // Config app 1002 solo una vez
        List<ConfigParametersModel> cfg = this.configParametersService.getByIdApplication(1002L);
        Map<String, List<String>> paramsList = toParamList(cfg);
        Map<String, String> paramsMap = toParamMap(cfg);

        long typeApprovalAuthorization = parseRequiredLong(paramsMap, "TYPE_APPROVAL_AUTHORIZATION");

        Set<String> typeUserAuthorization = new HashSet<>(paramsList.getOrDefault("TYPE_USER_AUTHORIZATION", Collections.emptyList()));
        Set<String> creditInvoiceTypes = new HashSet<>(paramsList.getOrDefault("CREDIT_INVOICE_TYPE", Collections.emptyList()));
        Set<String> cashInvoiceTypes = new HashSet<>(paramsList.getOrDefault("CASH_INVOICE_TYPE", Collections.emptyList()));
        Set<String> typeApprovalAuthorizationsList = new HashSet<>(paramsList.getOrDefault("TYPE_APPROVAL_AUTHORIZATION", Collections.emptyList()));

        // Validar usuario/permiso una sola vez
        ControlUserPermissionsEntity userPerm = controlUserPermissionsRepository.findByUserName(user);
        if (userPerm == null) {
            throw new BadRequestException(String.format(Constants.NOT_USER_RECORDS_FOUND, user));
        }
        TypeUserEntity typeUser = typeUserRepository.findById(userPerm.getTypeUser()).orElse(null);
        if (typeUser == null) {
            throw new BadRequestException(Constants.ERROR_USER_TYPE);
        }
        if (!typeUserAuthorization.contains(typeUser.getTypeUser())) {
            throw new BadRequestException(Constants.ERROR_TYPE_USER_AUTH);
        }

        return new AuthContext(
                user,
                ids,
                typeApprovalAuthorization,
                typeApprovalAuthorizationsList,
                creditInvoiceTypes,
                cashInvoiceTypes,
                LocalDateTime.now(),
                request.getDescription()
        );
    }

    /**
     * Carga todas las facturas en un solo viaje a DB y devuelve un mapa id->entity
     */
    private Map<Long, BillingEntity> preloadInvoices(List<Long> ids) {
        List<BillingEntity> found = billingRepository.findAllById(ids);
        return found.stream().collect(Collectors.toMap(BillingEntity::getId, x -> x));
    }

    /**
     * Valida una factura para autorización.
     * Devuelve Optional con el motivo de rechazo; vacío si pasa.
     */
    private Optional<String> validateInvoiceForAuthorization(BillingEntity entity, AuthContext ctx) {
        if (entity == null) return Optional.of("Invoice not found.");

        // Estado debe ser pendiente (0)
        if (!Objects.equals(entity.getStatus(), 0L)) {
            return Optional.of(Constants.ERROR_INVOICE_AUTHORIZED);
        }

        // Validar que el tipo de aprobación exista en config
        if (ctx.typeApprovalAuthorizationsList.isEmpty()
                || !ctx.typeApprovalAuthorizationsList.contains(String.valueOf(ctx.typeApprovalAuthorization))) {
            return Optional.of(Constants.ERROR_INVALID_TYPE_APPROVAL);
        }

        // Debe ser tipo crédito
        if (!ctx.creditInvoiceTypes.isEmpty() && !ctx.creditInvoiceTypes.contains(nullSafe(entity.getInvoiceType()))) {
            return Optional.of(Constants.ERROR_INVOICE_TYPE);
        }

        // No debe ser contado
        if (!ctx.cashInvoiceTypes.isEmpty() && ctx.cashInvoiceTypes.contains(nullSafe(entity.getInvoiceType()))) {
            return Optional.of(Constants.ERROR_CASH_INVOICE_AUTHORIZED);
        }

        return Optional.empty();
    }

    /**
     * Aplica los cambios de autorización en memoria (sin guardar todavía)
     */
    private void markAuthorizedInMemory(BillingEntity entity, LocalDateTime now, String user) {
        entity.setAuthorizingUser(user);
        entity.setAuthorizationDate(now);
        entity.setStatus(1L);
    }

    /**
     * Construye la fila de control de autorización para guardar en batch
     */
    private ControlAuthEmissionEntity buildControlRow(Long id, AuthContext ctx) {
        ControlAuthEmissionEntity ctrl = new ControlAuthEmissionEntity();
        ctrl.setIdPrefecture(id);
        ctrl.setTypeApproval(ctx.typeApprovalAuthorization);
        ctrl.setDescription(ctx.description);
        ctrl.setUserCreate(ctx.user);
        ctrl.setCreated(ctx.now);
        return ctrl;
    }

    // --------- utilidades de parámetros ---------
    private Map<String, List<String>> toParamList(List<ConfigParametersModel> list) {
        Map<String, List<String>> out = new HashMap<>();
        for (ConfigParametersModel p : list) {
            out.computeIfAbsent(p.getParameterName(), k -> new ArrayList<>()).add(p.getParameterValue());
        }
        return out;
    }

    private Map<String, String> toParamMap(List<ConfigParametersModel> list) {
        Map<String, String> out = new HashMap<>();
        for (ConfigParametersModel p : list) {
            out.put(p.getParameterName(), p.getParameterValue());
        }
        return out;
    }

    private long parseRequiredLong(Map<String, String> map, String key) {
        String v = map.get(key);
        if (v == null) throw new BadRequestException("Missing config " + key);
        try {
            return Long.parseLong(v);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid config " + key + ": " + v);
        }
    }

    private String nullSafe(String s) {
        return s == null ? "" : s;
    }

    // ================== CONTEXTO ==================
    private static class AuthContext {
        final String user;
        final List<Long> ids;
        final long typeApprovalAuthorization;
        final Set<String> typeApprovalAuthorizationsList;
        final Set<String> creditInvoiceTypes;
        final Set<String> cashInvoiceTypes;
        final LocalDateTime now;
        final String description;

        AuthContext(String user,
                    List<Long> ids,
                    long typeApprovalAuthorization,
                    Set<String> typeApprovalAuthorizationsList,
                    Set<String> creditInvoiceTypes,
                    Set<String> cashInvoiceTypes,
                    LocalDateTime now,
                    String description) {
            this.user = user;
            this.ids = ids;
            this.typeApprovalAuthorization = typeApprovalAuthorization;
            this.typeApprovalAuthorizationsList = typeApprovalAuthorizationsList;
            this.creditInvoiceTypes = creditInvoiceTypes;
            this.cashInvoiceTypes = cashInvoiceTypes;
            this.now = now;
            this.description = description;
        }
    }
}
