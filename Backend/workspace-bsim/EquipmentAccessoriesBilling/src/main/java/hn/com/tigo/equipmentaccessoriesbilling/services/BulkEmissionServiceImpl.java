package hn.com.tigo.equipmentaccessoriesbilling.services;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlUserPermissionsEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.TypeUserEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.UserBranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.exceptions.BadRequestException;
import hn.com.tigo.equipmentaccessoriesbilling.models.BillingModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkEmissionBatchResult;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkEmissionContext;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkEmissionItemResult;
import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.*;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBulkEmissionExecutor;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBulkEmissionService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BulkEmissionServiceImpl implements IBulkEmissionService {

    private final IBillingRepository billingRepository;
    private final IConfigParametersService configParametersService;
    private final IControlUserPermissionsRepository controlUserPermissionsRepository;
    private final ITypeUserRepository typeUserRepository;
    private final IUserBranchOfficesRepository userBranchOfficesRepository;
    private final IBulkEmissionExecutor emissionExecutor;


    @Override
    public Page<BillingModel> getBulkEmission(Pageable pageable, String seller) {
        final String sellerUpper = Optional.ofNullable(seller)
                .map(String::toUpperCase)
                .orElseThrow(() -> new BadRequestException(String.format(Constants.ERROR_USER_NOT_EXISTS, "null")));
        final UserVisibilityContext userCtx = buildUserVisibilityContext(sellerUpper);
        final VisibilityParams visParams = loadVisibilityParams();
        Page<BillingEntity> page = resolveVisibilityQuery(pageable, sellerUpper, userCtx, visParams);
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
            page = billingRepository.searchEmissionByCustomerOrCustomerId(pageable, customer, customerId);
        } else if (visParams.seeOnlyTheirInvoices.contains(type)) {
            List<Long> branchIds = ensureBranchIds(userCtx);
            page = billingRepository.searchEmissionByCustomerOrCustomerIdBySellerBranchOffice(
                    pageable, sellerUpper, branchIds, customer, customerId);
        } else if (visParams.seeAllInvoicesBranchOffice.contains(type)) {
            List<Long> branchIds = ensureBranchIds(userCtx);
            page = billingRepository.searchEmissionByCustomerOrCustomerIdByBranchOffice(
                    pageable, branchIds, customer, customerId);
        } else {
            throw new BadRequestException("User does not have permission to view invoices");
        }

        return page.map(BillingEntity::entityToModel);
    }
    
    // ==========================
    // ==== EMISIÓN MASIVA ====
    // ==========================

    /**
     * Orquesta la emisión masiva: valida usuario y parámetros, arma el contexto y
     * delega cada item al ejecutor transaccional (REQUIRES_NEW) para aislar fallos.
     */
    @Override
    public BulkEmissionBatchResult emitBulk(List<Long> idsPreInvoices,
                                            String userCreate,
                                            String description,
                                            Long idBranchOffices,
                                            String paymentCode) {

        if (idsPreInvoices == null || idsPreInvoices.isEmpty()) {
            throw new BadRequestException("You must provide at least one pre-invoice ID..");
        }

        // Limpia nulls y duplicados
        List<Long> normalizedIds = idsPreInvoices.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (normalizedIds.isEmpty()) {
            throw new BadRequestException("You must provide at least one valid pre-invoice ID..");
        }

        final String userUpper = Optional.ofNullable(userCreate)
                .map(String::toUpperCase)
                .orElseThrow(() -> new BadRequestException(String.format(Constants.ERROR_USER_NOT_EXISTS, "null")));

        // Validar usuario y tipo
        ControlUserPermissionsEntity userPerm = controlUserPermissionsRepository.findByUserName(userUpper);
        if (userPerm == null) {
            throw new BadRequestException(String.format(Constants.ERROR_USER_NOT_EXISTS, userUpper));
        }

        TypeUserEntity typeUser = typeUserRepository.findById(userPerm.getTypeUser()).orElse(null);
        if (typeUser == null) {
            throw new BadRequestException(Constants.ERROR_USER_TYPE);
        }

        // Cargar parámetros
        BulkEmissionExecutor.Params1002 p1002 = loadParams1002();
        Map<String, String> params1001 = loadParams1001();

        // Contexto para reducir parámetros
        BulkEmissionContext ctx = BulkEmissionContext.builder()
                .userUpper(userUpper)
                .description(description)
                .idBranchOffices(idBranchOffices)
                .paymentCode(paymentCode)
                .userPerm(userPerm)
                .typeUser(typeUser)
                .params1002(p1002)
                .params1001(params1001)
                .build();

        List<BulkEmissionItemResult> items = new ArrayList<>();
        int ok = 0;
        int fail = 0;

        for (Long id : idsPreInvoices) {
            System.out.println("--------- PROCESANDO FACTURA: " + id);
            BulkEmissionItemResult res = emissionExecutor.emitOneIsolated(id, ctx);
            items.add(res);
            if (res.isSuccess()) {
                ok++;
            } else {
                fail++;
            }
        }

        return BulkEmissionBatchResult.builder()
                .totalRequested(idsPreInvoices.size())
                .totalSuccess(ok)
                .totalFailed(fail)
                .items(items)
                .build();
    }

    // ==========================
    // ==== Loaders de parámetros (1001 / 1002) ====
    // ==========================

    private BulkEmissionExecutor.Params1002 loadParams1002() {
        List<ConfigParametersModel> list = configParametersService.getByIdApplication(1002L);

        Map<String, List<String>> multi = new HashMap<>();
        Map<String, String> flat = new HashMap<>();
        for (ConfigParametersModel p : list) {
            multi.computeIfAbsent(p.getParameterName(), k -> new ArrayList<>()).add(p.getParameterValue());
            flat.put(p.getParameterName(), p.getParameterValue());
        }

        BulkEmissionExecutor.Params1002 p = new BulkEmissionExecutor.Params1002();
        p.setTypeApprovalIssue(parseLong(flat.get("TYPE_APPROVAL_ISSUE"), 2L));
        p.setMillisecondsPayUpfront(parseLong(flat.get("MILLISECONDS_PAYUPFRONT"), 0L));
        p.setCreditInvoiceTypes(new HashSet<>(multi.getOrDefault("CREDIT_INVOICE_TYPE", Collections.emptyList())));
        p.setCashInvoiceTypes(new HashSet<>(multi.getOrDefault("CASH_INVOICE_TYPE", Collections.emptyList())));
        p.setTypeUserEmit(new ArrayList<>(multi.getOrDefault("TYPE_USER_EMIT", Collections.emptyList())));
        return p;
    }

    private Map<String, String> loadParams1001() {
        List<ConfigParametersModel> list = configParametersService.getByIdApplication(1001L);
        Map<String, String> params = new HashMap<>();
        for (ConfigParametersModel p : list) {
            params.put(p.getParameterName(), p.getParameterValue());
        }
        return params;
    }

    private long parseLong(String value, long def) {
        try {
            return value == null ? def : Long.parseLong(value);
        } catch (NumberFormatException nfe) {
            return def;
        }
    }

    // ==========================
    // ==== Helpers ====
    // ==========================
    private static final class UserVisibilityContext {
        final ControlUserPermissionsEntity userPerm;
        final TypeUserEntity typeUser;
        final List<Long> branchOfficeIds;

        UserVisibilityContext(ControlUserPermissionsEntity up, TypeUserEntity tu, List<Long> ids) {
            this.userPerm = up;
            this.typeUser = tu;
            this.branchOfficeIds = ids;
        }

        boolean isExemptFromBranchValidation() {
            String t = typeUser.getTypeUser();
            return "ADMINISTRATIVO".equalsIgnoreCase(t) || "CREDITOS".equalsIgnoreCase(t);
        }
    }

    private static final class VisibilityParams {
        final Set<String> seeAllInvoices;
        final Set<String> seeOnlyTheirInvoices;
        final Set<String> seeAllInvoicesBranchOffice;

        VisibilityParams(Set<String> a, Set<String> b, Set<String> c) {
            this.seeAllInvoices = a;
            this.seeOnlyTheirInvoices = b;
            this.seeAllInvoicesBranchOffice = c;
        }
    }

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

    private VisibilityParams loadVisibilityParams() {
        List<ConfigParametersModel> listTypeUser = configParametersService.getByIdApplication(1002L);
        Map<String, List<String>> grouped = new HashMap<>();
        for (ConfigParametersModel p : listTypeUser) {
            grouped.computeIfAbsent(p.getParameterName(), k -> new ArrayList<>()).add(p.getParameterValue());
        }
        Set<String> a = new HashSet<>(grouped.getOrDefault("USERS_SEE_ALL_INVOICES", Collections.emptyList()));
        Set<String> b = new HashSet<>(grouped.getOrDefault("USERS_SEE_ONLY_THEIR_INVOICES", Collections.emptyList()));
        Set<String> c = new HashSet<>(grouped.getOrDefault("USERS_SEE_ALL_INVOICES_BRACHOFFICE", Collections.emptyList()));
        return new VisibilityParams(a, b, c);
    }

    private Page<BillingEntity> resolveVisibilityQuery(Pageable pageable,
                                                       String sellerUpper,
                                                       UserVisibilityContext userCtx,
                                                       VisibilityParams visParams) {
        String type = userCtx.typeUser.getTypeUser();

        if ("CAJERO".equalsIgnoreCase(type)) return Page.empty(pageable);

        if (visParams.seeAllInvoices.contains(type)) {
            return billingRepository.getInvoicesForEmission(pageable);
        }
        if (visParams.seeOnlyTheirInvoices.contains(type)) {
            List<Long> branchIds = ensureBranchIds(userCtx);
            return billingRepository.getInvoicesForEmissionBySellerBranchOffice(pageable, sellerUpper, branchIds);
        }
        if (visParams.seeAllInvoicesBranchOffice.contains(type)) {
            List<Long> branchIds = ensureBranchIds(userCtx);
            return billingRepository.getInvoicesForEmissionByBranchOffice(pageable, branchIds);
        }
        throw new BadRequestException("User does not have permission to view invoices");
    }


    private List<Long> ensureBranchIds(UserVisibilityContext userCtx) {
        if (userCtx.isExemptFromBranchValidation()) return Collections.emptyList();
        if (userCtx.branchOfficeIds == null || userCtx.branchOfficeIds.isEmpty()) {
            throw new BadRequestException(Constants.ERROR_USER_BRANCHOFFICE);
        }
        return userCtx.branchOfficeIds;
    }
}
