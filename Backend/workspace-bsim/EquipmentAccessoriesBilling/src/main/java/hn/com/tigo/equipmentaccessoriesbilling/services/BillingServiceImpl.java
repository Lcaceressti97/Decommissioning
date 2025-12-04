package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.BadRequestException;

import java.util.Map;

import hn.com.tigo.equipmentaccessoriesbilling.dto.ProcessResult;
import hn.com.tigo.equipmentaccessoriesbilling.entities.*;
import hn.com.tigo.equipmentaccessoriesbilling.models.*;
import hn.com.tigo.equipmentaccessoriesbilling.provider.ProcessQueue;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.*;
import hn.com.tigo.equipmentaccessoriesbilling.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.provider.CustomerInfoProvider;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBillingService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IExchangeRateService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IUserBranchOfficesService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IUsersService;

@Slf4j
@Service
public class BillingServiceImpl implements IBillingService {

    private final IBillingRepository billingRepository;
    private final ILogsService logsService;
    private final IInvoiceDetailRepository invoiceDetailRepository;
    private final IBillingServicesRepository billingServicesRepository;
    private final IUserBranchOfficesService userBranchOfficesService;
    private final IUsersService usersService;
    private final IBranchOfficesRepository branchOfficesRepository;
    private final IControlUserPermissionsRepository controlUserPermissionsRepository;
    private final IConfigParametersService configParametersService;
    private final ITypeUserRepository typeUserRepository;
    private final IUserBranchOfficesRepository userBranchOfficesRepository;
    private final IChannelRepository channelRepository;
    private final AuthenticationBsimService authenticationService;
    private final ReserveSerialBsimService reserveSeriesService;
    private final IExchangeRateService exchangeRateService;
    private final IInsuranceRatesRepository insuranceRatesRepository;
    private final CustomerInfoProvider customerInfoProvider;
    private final ProcessQueue processQueue;
    private final IControlAuthEmissionRepository controlAuthEmissionRepository;

    public BillingServiceImpl(IBillingRepository billingRepository, ILogsService logsService,
                              IInvoiceDetailRepository invoiceDetailRepository, IBillingServicesRepository billingServicesRepository,
                              IUserBranchOfficesService userBranchOfficesService, IUsersService usersService,
                              IBranchOfficesRepository branchOfficesRepository,
                              IControlUserPermissionsRepository controlUserPermissionsRepository,
                              IConfigParametersService configParametersService, ITypeUserRepository typeUserRepository,
                              IUserBranchOfficesRepository userBranchOfficesRepository, IChannelRepository channelRepository,
                              AuthenticationBsimService authenticationService, ReserveSerialBsimService reserveSeriesService,
                              CustomerInfoProvider customerInfoProvider, IExchangeRateService exchangeRateService,
                              IInsuranceRatesRepository insuranceRatesRepository, ProcessQueue processQueue, IControlAuthEmissionRepository controlAuthEmissionRepository) {
        super();
        this.billingRepository = billingRepository;
        this.logsService = logsService;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.billingServicesRepository = billingServicesRepository;
        this.userBranchOfficesService = userBranchOfficesService;
        this.usersService = usersService;
        this.branchOfficesRepository = branchOfficesRepository;
        this.controlUserPermissionsRepository = controlUserPermissionsRepository;
        this.configParametersService = configParametersService;
        this.typeUserRepository = typeUserRepository;
        this.userBranchOfficesRepository = userBranchOfficesRepository;
        this.channelRepository = channelRepository;
        this.authenticationService = authenticationService;
        this.reserveSeriesService = reserveSeriesService;
        this.exchangeRateService = exchangeRateService;
        this.insuranceRatesRepository = insuranceRatesRepository;
        this.customerInfoProvider = customerInfoProvider;
        this.processQueue = processQueue;
        this.controlAuthEmissionRepository = controlAuthEmissionRepository;
    }

    @Override
    public Page<BillingModel> getAll(Pageable pageable, Long status) {
        // Obtener las facturas del día
        Page<BillingEntity> entities = this.billingRepository.getInvoicesOfTheDay(pageable, status);

        return entities.map(BillingEntity::entityToModel);
    }

    @Override
    public Page<BillingModel> getInvoicesAuthorizeIssue(Pageable pageable, Long status, String seller) {
        ControlUserPermissionsEntity controlUserPermissionsEntity = controlUserPermissionsRepository
                .findByUserName(seller.toUpperCase());
        if (controlUserPermissionsEntity == null)
            throw new BadRequestException(String.format(Constants.ERROR_USER_NOT_EXISTS, seller));

        TypeUserEntity typeUserEntity = this.typeUserRepository.findById(controlUserPermissionsEntity.getTypeUser())
                .orElse(null);
        if (typeUserEntity == null)
            throw new BadRequestException(String.format(Constants.ERROR_USER_TYPE));

        List<UserBranchOfficesEntity> userBranchOfficesEntity = this.userBranchOfficesRepository
                .findByIdUserActivated(controlUserPermissionsEntity.getIdUser());

        if ((userBranchOfficesEntity == null || userBranchOfficesEntity.isEmpty())
                && !typeUserEntity.getTypeUser().equalsIgnoreCase("ADMINISTRATIVO")
                && !typeUserEntity.getTypeUser().equalsIgnoreCase("CREDITOS"))
            throw new BadRequestException(String.format(Constants.ERROR_USER_BRANCHOFFICE));

        List<ConfigParametersModel> listTypeUser = this.configParametersService.getByIdApplication(1002L);
        Map<String, List<String>> parametersTypeUser = new HashMap<>();
        Map<String, String> paramsTypeUser = new HashMap<>();

        for (ConfigParametersModel parameter : listTypeUser) {
            String paramName = parameter.getParameterName();
            String paramValue = parameter.getParameterValue();

            if (!parametersTypeUser.containsKey(paramName)) {
                parametersTypeUser.put(paramName, new ArrayList<>());
            }

            parametersTypeUser.get(paramName).add(paramValue);
            paramsTypeUser.put(paramName, paramValue);
        }

        List<String> userSeeAllInvoice = parametersTypeUser.get("USERS_SEE_ALL_INVOICES");
        List<String> userSeeOnlyTheirInvoices = parametersTypeUser.get("USERS_SEE_ONLY_THEIR_INVOICES");
        List<String> userSeeAllInvoiceBrachOffice = parametersTypeUser.get("USERS_SEE_ALL_INVOICES_BRACHOFFICE");

        Page<BillingEntity> billingEntities;

        // Obtener una lista de IDs de las sucursales del usuario
        List<Long> branchOfficeIds = userBranchOfficesEntity.stream().map(UserBranchOfficesEntity::getIdBranchOffices)
                .collect(Collectors.toList());

        // Verificar si el tipo de usuario es CAJERO
        if (typeUserEntity.getTypeUser().equalsIgnoreCase("CAJERO")) {
            billingEntities = billingRepository.getInvoicesAuthorizeIssueByCashInvoices(pageable, status);
        } else if (userSeeAllInvoice != null && userSeeAllInvoice.contains(typeUserEntity.getTypeUser())) {
            billingEntities = billingRepository.getInvoicesAuthorizeIssue(pageable, status);
        } else if (userSeeOnlyTheirInvoices != null
                && userSeeOnlyTheirInvoices.contains(typeUserEntity.getTypeUser())) {
            billingEntities = billingRepository.getInvoicesAuthorizeIssueBySellerBranchOffice(pageable, status, seller.toUpperCase(),
                    branchOfficeIds);
        } else if (userSeeAllInvoiceBrachOffice != null
                && userSeeAllInvoiceBrachOffice.contains(typeUserEntity.getTypeUser())) {
            billingEntities = billingRepository.getInvoicesAuthorizeIssueByBranchOffice(pageable, status,
                    branchOfficeIds);
        } else {
            throw new BadRequestException("User  does not have permission to view invoices");
        }

        return billingEntities.map(BillingEntity::entityToModel);
    }

    @Override
    public Page<BillingModel> getInvoicesCancel(Pageable pageable, Long status, String seller) {

        ControlUserPermissionsEntity controlUserPermissionsEntity = controlUserPermissionsRepository
                .findByUserName(seller.toUpperCase());
        if (controlUserPermissionsEntity == null)
            throw new BadRequestException(String.format(Constants.ERROR_USER_NOT_EXISTS, seller));

        TypeUserEntity typeUserEntity = this.typeUserRepository.findById(controlUserPermissionsEntity.getTypeUser())
                .orElse(null);
        if (typeUserEntity == null)
            throw new BadRequestException(String.format(Constants.ERROR_USER_TYPE));

        List<UserBranchOfficesEntity> userBranchOfficesEntity = this.userBranchOfficesRepository
                .findByIdUserActivated(controlUserPermissionsEntity.getIdUser());
        if (userBranchOfficesEntity == null && !typeUserEntity.getTypeUser().equalsIgnoreCase("ADMINISTRATIVO")
                && !typeUserEntity.getTypeUser().equalsIgnoreCase("CREDITOS"))
            throw new BadRequestException(String.format(Constants.ERROR_USER_BRANCHOFFICE));

        List<ConfigParametersModel> listTypeUser = this.configParametersService.getByIdApplication(1002L);
        Map<String, List<String>> parametersTypeUser = new HashMap<>();
        Map<String, String> paramsTypeUser = new HashMap<>();

        for (ConfigParametersModel parameter : listTypeUser) {
            String paramName = parameter.getParameterName();
            String paramValue = parameter.getParameterValue();

            if (!parametersTypeUser.containsKey(paramName)) {
                parametersTypeUser.put(paramName, new ArrayList<>());
            }

            parametersTypeUser.get(paramName).add(paramValue);
            paramsTypeUser.put(paramName, paramValue);
        }

        List<String> userSeeAllInvoice = parametersTypeUser.get("USERS_SEE_ALL_INVOICES");
        List<String> userSeeOnlyTheirInvoices = parametersTypeUser.get("USERS_SEE_ONLY_THEIR_INVOICES");
        List<String> userSeeAllInvoiceBrachOffice = parametersTypeUser.get("USERS_SEE_ALL_INVOICES_BRACHOFFICE");

        Pageable descendingPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("created").descending());

        Page<BillingEntity> billingEntities;

        // Obtener una lista de IDs de las sucursales del usuario
        List<Long> branchOfficeIds = userBranchOfficesEntity.stream().map(UserBranchOfficesEntity::getIdBranchOffices)
                .collect(Collectors.toList());

        // Verificar si el tipo de usuario es CAJERO
        if (typeUserEntity.getTypeUser().equalsIgnoreCase("CAJERO")) {
            billingEntities = billingRepository.getInvoicesAuthorizeIssueByCashInvoices(pageable, status);
        } else if (userSeeAllInvoice != null && userSeeAllInvoice.contains(typeUserEntity.getTypeUser())) {
            billingEntities = billingRepository.getInvoicesCancel(descendingPageable, status);
        } else if (userSeeOnlyTheirInvoices != null
                && userSeeOnlyTheirInvoices.contains(typeUserEntity.getTypeUser())) {
            billingEntities = billingRepository.getInvoicesCancelBySellerBranchOffice(descendingPageable, status,
                    seller.toUpperCase(), branchOfficeIds);
        } else if (userSeeAllInvoiceBrachOffice != null
                && userSeeAllInvoiceBrachOffice.contains(typeUserEntity.getTypeUser())) {
            billingEntities = billingRepository.getInvoicesCancelByBranchOffice(descendingPageable, status,
                    branchOfficeIds);

        } else {
            throw new BadRequestException(String.format("User does not have permission to view invoices"));

        }

        return billingEntities.map(BillingEntity::entityToModel);
    }

    @Override
    public Page<BillingModel> findByFilter(Pageable pageable, int type, String value) {

        Page<BillingEntity> entities = this.billingRepository.findByFilter(pageable, type, value);
        return entities.map(BillingEntity::entityToModel);
    }

    @Override
    public Page<BillingModel> getBillsByDateRange(Pageable pageable, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
        if (!startDate.isPresent() || !endDate.isPresent()) {
            throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
        }
        LocalDate startDateTime = startDate.get();
        LocalDate endDateTime = endDate.get().plusDays(1);

        // Obtener las facturas en el rango de fechas
        Page<BillingEntity> entities = billingRepository.getBillsByDateRange(pageable, startDateTime, endDateTime);

        return entities.map(BillingEntity::entityToModel);
    }

    @Override
    public Page<BillingModel> filterInvoices(Pageable pageable, List<Long> status, List<String> warehouses, List<String> agencies,
                                             List<String> territories, Optional<LocalDate> startDate, Optional<LocalDate> endDate, String seller) {
        ControlUserPermissionsEntity controlUserPermissionsEntity = controlUserPermissionsRepository
                .findByUserName(seller.toUpperCase());
        if (controlUserPermissionsEntity == null)
            throw new BadRequestException(String.format(Constants.ERROR_USER_NOT_EXISTS, seller));

        TypeUserEntity typeUserEntity = this.typeUserRepository.findById(controlUserPermissionsEntity.getTypeUser())
                .orElse(null);
        if (typeUserEntity == null)
            throw new BadRequestException(String.format(Constants.ERROR_USER_TYPE));

        List<UserBranchOfficesEntity> userBranchOfficesEntities = this.userBranchOfficesRepository
                .findByIdUserActivated(controlUserPermissionsEntity.getIdUser());
        if (userBranchOfficesEntities.isEmpty())
            throw new BadRequestException(String.format(Constants.ERROR_USER_BRANCHOFFICE));

        List<ConfigParametersModel> listTypeUser = this.configParametersService.getByIdApplication(1003L);
        Map<String, List<String>> parametersTypeUser = new HashMap<>();
        Map<String, String> paramsTypeUser = new HashMap<>();

        for (ConfigParametersModel parameter : listTypeUser) {
            String paramName = parameter.getParameterName();
            String paramValue = parameter.getParameterValue();

            if (!parametersTypeUser.containsKey(paramName)) {
                parametersTypeUser.put(paramName, new ArrayList<>());
            }

            parametersTypeUser.get(paramName).add(paramValue);
            paramsTypeUser.put(paramName, paramValue);
        }

        List<String> userSeeAllReport = parametersTypeUser.get("SEE_ALL_REPORT");
        List<String> userSeeYourBranch = parametersTypeUser.get("SEE_ONLY_YOUR_BRANCH");

        List<BranchOfficesEntity> branchOfficesEntities = new ArrayList<>();

        for (UserBranchOfficesEntity userBranchOfficesEntity : userBranchOfficesEntities) {
            Long idBranchOffices = userBranchOfficesEntity.getIdBranchOffices();
            BranchOfficesEntity branchOfficesEntity = this.branchOfficesRepository.findById(idBranchOffices)
                    .orElse(null);
            if (branchOfficesEntity != null) {
                branchOfficesEntities.add(branchOfficesEntity);
            }
        }

        Specification<BillingEntity> spec = InvoiceSpecifications.filterByParameters(status, warehouses, agencies,
                territories, startDate, endDate, typeUserEntity, branchOfficesEntities, seller.toUpperCase(), userSeeAllReport,
                userSeeYourBranch);

        Page<BillingEntity> filteredEntities = billingRepository.findAll(spec, pageable);


        // Enriquecemos el mapeo para incluir la descripción
        return filteredEntities.map(entity -> {
            BillingModel model = entity.entityToModel();

            // Busca el último control para esta prefactura
            ControlAuthEmissionEntity lastCtrl =
                    controlAuthEmissionRepository.findTopByIdPrefectureOrderByCreatedDesc(entity.getId());

            model.setAuthEmissionDescription(lastCtrl != null ? lastCtrl.getDescription() : null);

            return model;
        });
    }

    @Override
    public BillingModel getBillsByIdAuthorizeIssue(Long id) {
        BillingEntity entity = this.billingRepository.getBillsByIdAuthorizeIssue(id);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

        // Verificar si el channel
        if (entity.getChannel() == 2 || entity.getChannel() == 4) {
            throw new BadRequestException("Invoices are not allowed for channel 2 and 4.");
        }

        return entity.entityToModel();
    }

    @Override
    public BillingModel getBillsByIdInvoicesCancel(Long id) {
        BillingEntity entity = this.billingRepository.getBillsByIdInvoicesCancel(id);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

        // Verificar si el channel
        if (entity.getChannel() == 2 || entity.getChannel() == 4) {
            throw new BadRequestException("Invoices are not allowed for channel 2 and 4.");
        }

        return entity.entityToModel();
    }

    @Override
    public BillingModel getById(Long id) {
        BillingEntity entity = this.billingRepository.findById(id).orElse(null);

        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

        return entity.entityToModel();
    }

    /**
     * Este método se utiliza para el VoucherService
     *
     * @param id
     * @return
     */
    @Override
    public BillingModel findById(Long id) {
        BillingEntity entity = this.billingRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return entity.entityToModel();
    }

    /**
     * Método que se utiliza para el getVoucher
     */
    @Override
    public BillingModel findByIdReference(Long idReference) {
        List<BillingEntity> entities = this.billingRepository.findByIdReference(idReference);

        if (entities.isEmpty()) {
            return null;
        }

        return entities.get(0).entityToModel();

    }

    @Override
    public Long add(BillingModel model) throws MalformedURLException {

        List<UsersEntity> user = this.usersService.findByUserName(model.getSeller().toUpperCase());

        // Validamos si el usuario existe en la tabla de usuarios
        if (user.isEmpty()) {
            throw new BadRequestException(String.format(Constants.USER_NOT_FOUND, model.getSeller()));
        }

        // Validamos si existe o está asignado a una sucursal
        List<UserBranchOfficesEntity> userBranchOffices = this.userBranchOfficesService
                .findByIdUser(user.get(0).getId());

        if (userBranchOffices.isEmpty()) {
            throw new BadRequestException(String.format(Constants.USER_HAS_NOT_ASSIGNED, model.getSeller()));
        }

        UserBranchOfficesEntity userActive = null;

        for (UserBranchOfficesEntity validateUser : userBranchOffices) {

            if (validateUser.getStatus() == 1) {
                userActive = validateUser;
                break;
            }

        }

        /**
         * Validamos si el usuario está activo en alguna sucursal
         *
         */
        if (userActive == null) {
            throw new BadRequestException(String.format(Constants.USER_NOT_ACTIVE_BRANCHE_OFFICES, model.getSeller()));
        }

        BranchOfficesEntity branchOffices = null;
        if (model.getChannel() == 2) {
            branchOffices = this.branchOfficesRepository.findById(userActive.getIdBranchOffices()).orElse(null);
        } else {
            branchOffices = this.branchOfficesRepository.getBranchOfficeByWineryCode(model.getWarehouse());
        }

        if (branchOffices == null && model.getChannel() != 2) {
            throw new BadRequestException(
                    String.format(Constants.ERROR_NOT_FOUND_RECORD_BRANCHE_OFFICES, model.getWarehouse()));
        }

        ChannelEntity channelEntity = this.channelRepository.findById(model.getChannel()).orElse(null);

        if (model.getChannel() != null) {
            if (channelEntity == null) {
                throw new BadRequestException(
                        String.format(Constants.ERROR_NOT_FOUND_RECORD_CHANNEL, model.getChannel()));

            } else if (channelEntity.getStatus() == 0) {
                throw new BadRequestException(String.format(Constants.VALIDATE_CHANNEL_STATUS, model.getChannel()));
            }
        }

        
        if (channelEntity.getReserveSerialNumber() == 1 && channelEntity.getId() == 2) {
            // Reservar los números de serie
            for (InvoiceDetailEntity detail : model.getInvoiceDetails()) {
                if (detail.getSerieOrBoxNumber() != null && !detail.getSerieOrBoxNumber().isEmpty()) {
                    // Obtener el token de autenticación
                    AuthenticationBsimResponse accessToken = authenticationService.getAccessToken();
                    
                    List<InvoiceDetailEntity> detailList = Arrays.asList(detail);
                    System.out.println(detailList.get(0));
                    reserveSeriesService.reserveSeries(accessToken.getAccess_token(), model.getInventoryType(),
                            detail.getModel(), model.getWarehouse(), model.getSubWarehouse(), detailList, model.getChannel(), model.getSeller(), channelEntity);
                }
            }
        } 

        // Consumo del servicio CustomerInfo
        CustomerInfoModel customerInfo = null;
        if (model.getChannel() == 1) {
            ConfigParametersModel configModel = this.configParametersService.getByName("CBS_QUERY_CUSTOMER_INFO_TASK_URL");
            customerInfo = this.customerInfoProvider.getCustomerInfoByAcctCode(model.getAcctCode(), configModel.getParameterValue());
        }

        BillingEntity entity = new BillingEntity();

        entity.setInvoiceNo(model.getInvoiceNo());
        entity.setInvoiceType(model.getInvoiceType());
        entity.setIdReference(model.getIdReference());
        entity.setAcctCode(model.getAcctCode());
        entity.setPrimaryIdentity(model.getPrimaryIdentity());
        entity.setDocumentNo(model.getDocumentNo());
        entity.setDiplomaticCardNo(model.getDiplomaticCardNo());
        entity.setCorrelativeOrdenExemptNo(model.getCorrelativeOrdenExemptNo());
        entity.setCorrelativeCertificateExoNo(model.getCorrelativeCertificateExoNo());
        entity.setXml(model.getXml());
        entity.setCai(model.getCai());
        entity.setCustomer(model.getChannel() == 1 ? customerInfo.getCustomerName() : model.getCustomer());
        entity.setCustomerId(model.getChannel() == 1 ? customerInfo.getCustomerId() : model.getCustomerId());
        entity.setCashierName(model.getCashierName());
        entity.setSeller(model.getSeller().toUpperCase());
        entity.setSellerCode(model.getSellerCode());
        entity.setIdBranchOffices(
                model.getIdBranchOffices() != null ? model.getIdBranchOffices() : branchOffices.getId());
        entity.setAgency(model.getAgency() != null ? model.getAgency() : branchOffices.getName());
        entity.setWarehouse(model.getWarehouse() != null && !model.getWarehouse().isEmpty() ? model.getWarehouse()
                : branchOffices.getWineryCode());
        entity.setDueDate(LocalDateTime.now().plusMonths(1));
        entity.setInitialRank(model.getInitialRank());
        entity.setFinalRank(model.getFinalRank());
        entity.setIdCompany(model.getIdCompany());
        entity.setIdSystem(model.getIdSystem());
        entity.setNumberDei(model.getNumberDei());
        entity.setDeadLine(model.getDeadLine());
        entity.setIdVoucher(model.getIdVoucher());

        entity.setAmountExo(model.getAmountExo());
        entity.setAuthorizingUser(model.getAuthorizingUser());
        entity.setAuthorizationDate(model.getAuthorizationDate());
        entity.setUserIssued(model.getUserIssued());
        entity.setDateOfIssue(model.getDateOfIssue());
        entity.setExonerationStatus(0L);
        entity.setStatus(0L);
        entity.setCreated(LocalDateTime.now());
        entity.setInventoryType(model.getInventoryType());
        entity.setSubWarehouse(model.getSubWarehouse());
        entity.setChannel(model.getChannel());
        entity.setExemptAmount(model.getExemptAmount());
        entity.setCustomerAddress(
                model.getChannel() == 1 ? customerInfo.getCustomerAddress() : model.getCustomerAddress());
        entity.setCustomerRtnId(model.getChannel() == 1 ? customerInfo.getCustomerRtn() : model.getCustomerRtnId());
        entity.setSellerName(user.get(0).getName());
        entity.setIncludesInsurance(model.getIncludesInsurance());
        entity.setFiscalProcess(model.getFiscalProcess());
        entity.setIdInsuranceClaim(model.getIdInsuranceClaim());
        entity.setUti(model.getUti());

        List<InvoiceDetailEntity> invoiceDetails = new ArrayList<>();
        for (@Valid
        InvoiceDetailEntity detailModel : model.getInvoiceDetails()) {
            InvoiceDetailEntity detailEntity = new InvoiceDetailEntity();

            String detailDescription;
            if (StringUtils.isBlank(detailModel.getDescription())) {
                BillingServicesEntity billingServicesEntity = this.billingServicesRepository
                        .findByServiceCode(Long.parseLong(detailModel.getModel()));
                if (billingServicesEntity == null)
                    throw new BadRequestException(
                            String.format(Constants.ERROR_NOT_FOUND_SERVICE_CODE, detailModel.getModel()));
                detailDescription = billingServicesEntity.getServiceName();
            } else {
                detailDescription = detailModel.getDescription();
            }

            detailEntity.setBilling(entity);
            detailEntity.setModel(detailModel.getModel().trim());
            detailEntity.setDescription(detailDescription);
            detailEntity.setQuantity(detailModel.getQuantity());
            detailEntity.setUnitPrice(detailModel.getUnitPrice());

            int roundedValue = (model.getChannel() == 2) ? 4 : 2;

            // Calculo del subtotal
            Double subtotal = (detailModel.getSubtotal() != null) ? detailModel.getSubtotal()
                    : detailModel.getQuantity() * detailModel.getUnitPrice();
            BigDecimal roundedSubTotal = BigDecimal.valueOf(subtotal).setScale(roundedValue, RoundingMode.HALF_UP);
            detailEntity.setSubtotal(roundedSubTotal.doubleValue());

            Double discount = (detailModel.getDiscount() != null) ? detailModel.getDiscount() : 0.00;
            detailEntity.setDiscount(discount);

            // Calculo del impuesto
            Double taxableAmount = subtotal - discount;
            Double tax = (detailModel.getTax() != null) ? detailModel.getTax()
                    : taxableAmount * getTaxPercentage() / 100;
            BigDecimal roundedTax = BigDecimal.valueOf(tax).setScale(roundedValue, RoundingMode.HALF_UP);

            detailEntity.setTax(roundedTax.doubleValue());

            // Calculo del total
            Double amountTotal = (detailModel.getAmountTotal() != null) ? detailModel.getAmountTotal()
                    : subtotal + tax - discount;


            BigDecimal roundedAmountTotal = BigDecimal.valueOf(amountTotal).setScale(roundedValue, RoundingMode.HALF_UP);
            detailEntity.setAmountTotal(roundedAmountTotal.doubleValue());
            detailEntity.setSerieOrBoxNumber(detailModel.getSerieOrBoxNumber());
            detailEntity.setStatus(1L);
            detailEntity.setCreated(LocalDateTime.now());
            detailEntity.setReserveKey(detailModel.getReserveKey());
            invoiceDetails.add(detailEntity);
        }

        // Agregar detalle del seguro
        if ("S".equals(model.getIncludesInsurance())) {
            addInsuranceDetail(entity, invoiceDetails);
        }

        // Ahora calculamos los totales usando la lista actualizada de detalles de
        // factura
        TotalInvoiceModel totals = calculateInvoiceTotals(invoiceDetails, model.getChannel());

        entity.setSubtotal(model.getSubtotal() != null ? model.getSubtotal() : totals.getTotalSubtotal());
        entity.setExchangeRate(model.getExchangeRate() != null ? model.getExchangeRate() : totals.getExchangeRate());
        entity.setTaxPercentage(
                model.getTaxPercentage() != null ? model.getTaxPercentage() : totals.getTaxPercentage());
        entity.setAmountTax(model.getAmountTax() != null ? model.getAmountTax() : totals.getTotalTax());
        entity.setDiscountPercentage(
                model.getDiscountPercentage() != null ? model.getDiscountPercentage() : totals.getDiscountPercentage());
        entity.setDiscount(model.getDiscount() != null ? model.getDiscount() : totals.getTotalDiscount());
        entity.setAmountTotal(model.getAmountTotal() != null ? model.getAmountTotal() : totals.getTotalAmount());

        entity = this.billingRepository.save(entity);
        Long generatedId = entity.getId();
        this.invoiceDetailRepository.saveAll(invoiceDetails);

        return generatedId;

    }

    @Override
    public Long addInvoiceInsuranceClaim(BillingModel model) throws MalformedURLException {
        List<UsersEntity> user = this.usersService.findByUserName(model.getSeller().toUpperCase());

        // Validamos si el usuario existe en la tabla de usuarios
        if (user.isEmpty()) {
            throw new BadRequestException(String.format(Constants.USER_NOT_FOUND, model.getSeller()));
        }

        // Validamos si existe o está asignado a una sucursal
        List<UserBranchOfficesEntity> userBranchOffices = this.userBranchOfficesService
                .findByIdUser(user.get(0).getId());

        if (userBranchOffices.isEmpty()) {
            throw new BadRequestException(String.format(Constants.USER_HAS_NOT_ASSIGNED, model.getSeller()));
        }

        UserBranchOfficesEntity userActive = null;

        for (UserBranchOfficesEntity validateUser : userBranchOffices) {

            if (validateUser.getStatus() == 1) {
                userActive = validateUser;
                break;
            }

        }

        /**
         * Validamos si el usuario está activo en alguna sucursal
         *
         */
        if (userActive == null) {
            throw new BadRequestException(String.format(Constants.USER_NOT_ACTIVE_BRANCHE_OFFICES, model.getSeller()));
        }

        BranchOfficesEntity branchOffices = null;
        if (model.getChannel() == 2) {
            branchOffices = this.branchOfficesRepository.findById(userActive.getIdBranchOffices()).orElse(null);
        } else {
            branchOffices = this.branchOfficesRepository.getBranchOfficeByWineryCode(model.getWarehouse());
        }

        if (branchOffices == null && model.getChannel() != 2) {
            throw new BadRequestException(
                    String.format(Constants.ERROR_NOT_FOUND_RECORD_BRANCHE_OFFICES, model.getWarehouse()));
        }

        ChannelEntity channelEntity = this.channelRepository.findById(model.getChannel()).orElse(null);

        if (model.getChannel() != null) {
            if (channelEntity == null) {
                throw new BadRequestException(
                        String.format(Constants.ERROR_NOT_FOUND_RECORD_CHANNEL, model.getChannel()));

            } else if (channelEntity.getStatus() == 0) {
                throw new BadRequestException(String.format(Constants.VALIDATE_CHANNEL_STATUS, model.getChannel()));
            }
        }

        if (channelEntity.getReserveSerialNumber() == 1) {
            // Reservar los números de serie
            for (InvoiceDetailEntity detail : model.getInvoiceDetails()) {
                if (detail.getSerieOrBoxNumber() != null && !detail.getSerieOrBoxNumber().isEmpty()) {
                    // Obtener el token de autenticación
                    AuthenticationBsimResponse accessToken = authenticationService.getAccessToken();

                    List<InvoiceDetailEntity> detailList = Arrays.asList(detail);
                    reserveSeriesService.reserveSeries(accessToken.getAccess_token(), model.getInventoryType(),
                            detail.getModel(), model.getWarehouse(), model.getSubWarehouse(), detailList, model.getChannel(), model.getSeller(), channelEntity);
                }
            }
        }

        // Consumo del servicio CustomerInfo
        CustomerInfoModel customerInfo = null;
        if (model.getChannel() == 1) {
            ConfigParametersModel configModel = this.configParametersService.getByName("CBS_QUERY_CUSTOMER_INFO_TASK_URL");
            customerInfo = this.customerInfoProvider.getCustomerInfoByAcctCode(model.getAcctCode(), configModel.getParameterValue());
        }

        BillingEntity entity = new BillingEntity();

        entity.setInvoiceNo(model.getInvoiceNo());
        entity.setInvoiceType(model.getInvoiceType());
        entity.setIdReference(model.getIdReference());
        entity.setAcctCode(model.getAcctCode());
        entity.setPrimaryIdentity(model.getPrimaryIdentity());
        entity.setDocumentNo(model.getDocumentNo());
        entity.setDiplomaticCardNo(model.getDiplomaticCardNo());
        entity.setCorrelativeOrdenExemptNo(model.getCorrelativeOrdenExemptNo());
        entity.setCorrelativeCertificateExoNo(model.getCorrelativeCertificateExoNo());
        entity.setXml(model.getXml());
        entity.setCai(model.getCai());
        entity.setCustomer(model.getChannel() == 1 ? customerInfo.getCustomerName() : model.getCustomer());
        entity.setCustomerId(model.getChannel() == 1 ? customerInfo.getCustomerId() : model.getCustomerId());
        entity.setCashierName(model.getCashierName());
        entity.setSeller(model.getSeller().toUpperCase());
        entity.setSellerCode(model.getSellerCode());
        entity.setIdBranchOffices(
                model.getIdBranchOffices() != null ? model.getIdBranchOffices() : branchOffices.getId());
        entity.setAgency(model.getAgency() != null ? model.getAgency() : branchOffices.getName());
        entity.setWarehouse(model.getWarehouse() != null && !model.getWarehouse().isEmpty() ? model.getWarehouse()
                : branchOffices.getWineryCode());
        entity.setDueDate(LocalDateTime.now().plusMonths(1));
        entity.setInitialRank(model.getInitialRank());
        entity.setFinalRank(model.getFinalRank());
        entity.setIdCompany(model.getIdCompany());
        entity.setIdSystem(model.getIdSystem());
        entity.setNumberDei(model.getNumberDei());
        entity.setDeadLine(model.getDeadLine());
        entity.setIdVoucher(model.getIdVoucher());

        entity.setAmountExo(model.getAmountExo());
        entity.setAuthorizingUser(model.getAuthorizingUser());
        entity.setAuthorizationDate(model.getAuthorizationDate());
        entity.setUserIssued(model.getUserIssued());
        entity.setDateOfIssue(model.getDateOfIssue());
        entity.setExonerationStatus(0L);
        entity.setStatus(0L);
        entity.setCreated(LocalDateTime.now());
        entity.setInventoryType(model.getInventoryType());
        entity.setSubWarehouse(model.getSubWarehouse());
        entity.setChannel(model.getChannel());
        entity.setExemptAmount(model.getExemptAmount());
        entity.setCustomerAddress(
                model.getChannel() == 1 ? customerInfo.getCustomerAddress() : model.getCustomerAddress());
        entity.setCustomerRtnId(model.getChannel() == 1 ? customerInfo.getCustomerRtn() : model.getCustomerRtnId());
        entity.setSellerName(user.get(0).getName());
        entity.setIncludesInsurance(model.getIncludesInsurance());
        entity.setFiscalProcess(model.getFiscalProcess());
        entity.setIdInsuranceClaim(model.getIdInsuranceClaim());
        entity.setUti(model.getUti());

        List<InvoiceDetailEntity> invoiceDetails = new ArrayList<>();
        for (@Valid
        InvoiceDetailEntity detailModel : model.getInvoiceDetails()) {
            InvoiceDetailEntity detailEntity = new InvoiceDetailEntity();

            String detailDescription;
            if (StringUtils.isBlank(detailModel.getDescription())) {
                BillingServicesEntity billingServicesEntity = this.billingServicesRepository
                        .findByServiceCode(Long.parseLong(detailModel.getModel()));
                if (billingServicesEntity == null)
                    throw new BadRequestException(
                            String.format(Constants.ERROR_NOT_FOUND_SERVICE_CODE, detailModel.getModel()));
                detailDescription = billingServicesEntity.getServiceName();
            } else {
                detailDescription = detailModel.getDescription();
            }

            detailEntity.setBilling(entity);
            detailEntity.setModel(detailModel.getModel().trim());
            detailEntity.setDescription(detailDescription);
            detailEntity.setQuantity(detailModel.getQuantity());
            detailEntity.setUnitPrice(detailModel.getUnitPrice());

            // Calculo del subtotal
            Double subtotal = (detailModel.getSubtotal() != null) ? detailModel.getSubtotal()
                    : detailModel.getQuantity() * detailModel.getUnitPrice();
            BigDecimal roundedSubTotal = BigDecimal.valueOf(subtotal).setScale(4, RoundingMode.HALF_UP);
            detailEntity.setSubtotal(roundedSubTotal.doubleValue());

            Double discount = (detailModel.getDiscount() != null) ? detailModel.getDiscount() : 0.00;
            detailEntity.setDiscount(discount);

            // Calculo del impuesto
            Double taxableAmount = subtotal - discount;
            Double tax = (detailModel.getTax() != null) ? detailModel.getTax()
                    : taxableAmount * getTaxPercentage() / 100;
            BigDecimal roundedTax = BigDecimal.valueOf(tax).setScale(4, RoundingMode.HALF_UP);

            detailEntity.setTax(roundedTax.doubleValue());

            // Calculo del total
            Double amountTotal = (detailModel.getAmountTotal() != null) ? detailModel.getAmountTotal()
                    : subtotal + tax - discount;
            // Redondear a 4 decimales
            BigDecimal roundedAmountTotal = BigDecimal.valueOf(amountTotal).setScale(4, RoundingMode.HALF_UP);
            detailEntity.setAmountTotal(roundedAmountTotal.doubleValue());
            detailEntity.setSerieOrBoxNumber(detailModel.getSerieOrBoxNumber());
            detailEntity.setStatus(1L);
            detailEntity.setCreated(LocalDateTime.now());
            invoiceDetails.add(detailEntity);
        }

        // Agregar detalle del seguro
        if ("S".equals(model.getIncludesInsurance())) {
            addInsuranceDetail(entity, invoiceDetails);
        }

        // Ahora calculamos los totales usando la lista actualizada de detalles de
        // factura
        TotalInvoiceModel totals = calculateInvoiceTotals(invoiceDetails, model.getChannel());

        entity.setSubtotal(model.getSubtotal() != null ? model.getSubtotal() : totals.getTotalSubtotal());
        entity.setExchangeRate(model.getExchangeRate() != null ? model.getExchangeRate() : totals.getExchangeRate());
        entity.setTaxPercentage(
                model.getTaxPercentage() != null ? model.getTaxPercentage() : totals.getTaxPercentage());
        entity.setAmountTax(model.getAmountTax() != null ? model.getAmountTax() : totals.getTotalTax());
        entity.setDiscountPercentage(
                model.getDiscountPercentage() != null ? model.getDiscountPercentage() : totals.getDiscountPercentage());
        entity.setDiscount(model.getDiscount() != null ? model.getDiscount() : totals.getTotalDiscount());
        entity.setAmountTotal(model.getAmountTotal() != null ? model.getAmountTotal() : totals.getTotalAmount());

        entity = this.billingRepository.save(entity);
        Long generatedId = entity.getId();
        this.invoiceDetailRepository.saveAll(invoiceDetails);

        return generatedId;
    }

    private void addInsuranceDetail(BillingEntity entity, List<InvoiceDetailEntity> invoiceDetails) {
        InvoiceDetailEntity insuranceDetail = new InvoiceDetailEntity();

        String model = null;
        Double unitPrice = null;
        boolean modelFound = false; // Variable para verificar si se encontró un modelo válido

        for (InvoiceDetailEntity detail : invoiceDetails) {
            model = detail.getModel();
            unitPrice = detail.getUnitPrice();

            // Verificar si el modelo y el precio son válidos
            if (model != null && unitPrice != null) {
                modelFound = true; // Se encontró un modelo válido
                break; // Salir del bucle ya que solo necesitamos el primer modelo válido
            }
        }

        // Si no se encontró un modelo válido, lanzar una excepción
        if (!modelFound) {
            throw new IllegalArgumentException("No se puede agregar seguro porque el modelo no existe.");
        }

        Double unitPriceInsurance = insuranceRatesRepository.getMonthlyPremiumByModel(model, unitPrice);
        if (unitPriceInsurance == null) {
            return;
        }

        insuranceDetail.setBilling(entity);
        insuranceDetail.setModel("200");
        insuranceDetail.setDescription("SEGURO DE EQUIPOS");
        insuranceDetail.setQuantity((double) 1);
        insuranceDetail.setUnitPrice(unitPriceInsurance);
        insuranceDetail.setSubtotal(unitPriceInsurance);
        insuranceDetail.setDiscount(0.0);
        insuranceDetail.setTax(unitPriceInsurance * getTaxPercentage() / 100);
        insuranceDetail.setAmountTotal(unitPriceInsurance + (unitPriceInsurance * getTaxPercentage() / 100));
        insuranceDetail.setStatus(1L);
        insuranceDetail.setCreated(LocalDateTime.now());
        invoiceDetails.add(insuranceDetail);
    }

    private TotalInvoiceModel calculateInvoiceTotals(List<InvoiceDetailEntity> invoiceDetails, Long channel) {
        Double totalSubtotal = 0.0;
        Double totalTax = 0.0;
        Double totalDiscount = 0.0;
        Double totalAmount = 0.0;

        ExchangeRateModel exchangeRateModel = this.exchangeRateService.getExchangeRate();
        Double exchangeRate = exchangeRateModel.getSalePrice().doubleValue();

        List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(999L);
        Map<String, String> parameters = new HashMap<>();
        for (ConfigParametersModel parameter : list) {
            parameters.put(parameter.getParameterName(), parameter.getParameterValue());
        }

        String percentageTax = parameters.get("PERCENTAGE_TAX");

        int roundedValue = (channel == 2) ? 4 : 2;

        for (InvoiceDetailEntity detail : invoiceDetails) {


            Double subtotal = (detail.getSubtotal() != null) ? detail.getSubtotal()
                    : detail.getQuantity() * detail.getUnitPrice();

            Double discount = (detail.getDiscount() != null) ? detail.getDiscount() : 0.00;

            // Calculo del impuesto
            Double taxableAmount = subtotal - discount;
            Double tax = (detail.getTax() != null) ? detail.getTax() : taxableAmount * getTaxPercentage() / 100;

            // Redondear el impuesto a 4 decimales
            BigDecimal taxBD = BigDecimal.valueOf(tax).setScale(roundedValue, RoundingMode.HALF_UP);
            tax = taxBD.doubleValue();

            // Calculo del total
            Double amountTotal = subtotal + tax - discount;
            BigDecimal amountTotalBD = BigDecimal.valueOf(amountTotal).setScale(roundedValue, RoundingMode.HALF_UP);
            amountTotal = amountTotalBD.doubleValue();

            // Sumar a los totales
            totalSubtotal += subtotal;
            totalTax += tax;
            totalDiscount += discount;
            totalAmount += amountTotal;

        }

        // Calcular el porcentaje de descuento
        Double discountPercentage = (totalSubtotal != 0) ? (totalDiscount / totalSubtotal) * 100 : 0.0;

        // Redondear totalSubtotal, totalDiscount y totalAmount a cuatro decimales
        BigDecimal totalTaxBD = BigDecimal.valueOf(totalTax).setScale(roundedValue, RoundingMode.HALF_UP);
        totalTax = totalTaxBD.doubleValue();

        BigDecimal totalSubtotalBD = BigDecimal.valueOf(totalSubtotal).setScale(roundedValue, RoundingMode.HALF_UP);
        totalSubtotal = totalSubtotalBD.doubleValue();

        BigDecimal totalDiscountBD = BigDecimal.valueOf(totalDiscount).setScale(roundedValue, RoundingMode.HALF_UP);
        totalDiscount = totalDiscountBD.doubleValue();

        BigDecimal totalAmountBD = BigDecimal.valueOf(totalAmount).setScale(roundedValue, RoundingMode.HALF_UP);
        totalAmount = totalAmountBD.doubleValue();

        BigDecimal discountPercentageBD = BigDecimal.valueOf(discountPercentage).setScale(2, RoundingMode.HALF_UP);
        discountPercentage = discountPercentageBD.doubleValue();

        return new TotalInvoiceModel(totalSubtotal, exchangeRate, Double.valueOf(percentageTax), totalTax,
                discountPercentage, totalDiscount, totalAmount);

    }

    private double getTaxPercentage() {
        List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(999L);
        Map<String, String> parameters = new HashMap<>();
        for (ConfigParametersModel parameter : list) {
            parameters.put(parameter.getParameterName(), parameter.getParameterValue());
        }
        return Double.parseDouble(parameters.get("PERCENTAGE_TAX"));
    }

    @Override
    public void updateDataForAddVoucher(Long id, BillingModel model) {
        BillingEntity entity = this.billingRepository.findById(id).orElse(null);
        entity.setIdReference(model.getIdReference());
        entity.setCai(model.getCai());
        entity.setIdCompany(model.getIdCompany());
        entity.setIdSystem(model.getIdSystem());
        entity.setIdVoucher(model.getIdVoucher());
        entity.setNumberDei(model.getNumberDei());
        entity.setDeadLine(model.getDeadLine());
        entity.setCustomerId(model.getCustomerId());
        entity.setIdBranchOffices(model.getIdBranchOffices());
        entity.setAgency(model.getAgency());
        entity.setStatus(model.getStatus());

        this.billingRepository.save(entity);
    }

    @Override
    public void update(Long id, BillingModel model) {

        BillingEntity entity = this.billingRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));

        // Verificar si el usuario vendedor existe en la tabla de usuarios
        List<UsersEntity> user = this.usersService.findByUserName(model.getSeller().toUpperCase());
        if (user.isEmpty()) {
            throw new BadRequestException(String.format(Constants.USER_NOT_FOUND, model.getSeller()));
        }

        // Verificar si el usuario vendedor está asignado a una sucursal
        List<UserBranchOfficesEntity> userBranchOffices = this.userBranchOfficesService
                .findByIdUser(user.get(0).getId());
        if (userBranchOffices.isEmpty()) {
            throw new BadRequestException(String.format(Constants.USER_HAS_NOT_ASSIGNED, model.getSeller()));
        }

        // Verificar si el usuario vendedor está activo en alguna sucursal
        UserBranchOfficesEntity userActive = null;
        for (UserBranchOfficesEntity validateUser : userBranchOffices) {
            if (validateUser.getStatus() == 1) {
                userActive = validateUser;
                break;
            }
        }
        if (userActive == null) {
            throw new BadRequestException(String.format(Constants.USER_NOT_ACTIVE_BRANCHE_OFFICES, model.getSeller()));
        }

        // Actualizar los campos de la entidad BillingEntity
        entity.setInvoiceNo(model.getInvoiceNo());
        entity.setInvoiceType(model.getInvoiceType());
        entity.setIdReference(model.getIdReference());
        entity.setAcctCode(model.getAcctCode());
        entity.setPrimaryIdentity(model.getPrimaryIdentity());
        entity.setDocumentNo(model.getDocumentNo());
        entity.setDiplomaticCardNo(model.getDiplomaticCardNo());
        entity.setCorrelativeOrdenExemptNo(model.getCorrelativeOrdenExemptNo());
        entity.setCorrelativeCertificateExoNo(model.getCorrelativeCertificateExoNo());
        entity.setXml(model.getXml());
        entity.setCai(model.getCai());
        entity.setCustomer(model.getCustomer());
        entity.setCustomerId(model.getCustomerId());
        entity.setCashierName(model.getCashierName());
        entity.setSeller(model.getSeller().toUpperCase());
        entity.setSellerCode(model.getSellerCode());
        entity.setIdBranchOffices(model.getIdBranchOffices());
        entity.setAgency(model.getAgency());
        entity.setWarehouse(model.getWarehouse());
        entity.setDueDate(LocalDateTime.now().plusMonths(1));
        entity.setInitialRank(model.getInitialRank());
        entity.setFinalRank(model.getFinalRank());
        entity.setIdCompany(model.getIdCompany());
        entity.setIdSystem(model.getIdSystem());
        entity.setNumberDei(model.getNumberDei());
        entity.setDeadLine(model.getDeadLine());
        entity.setIdVoucher(model.getIdVoucher());
        entity.setSubtotal(model.getSubtotal());
        entity.setExchangeRate(model.getExchangeRate());
        entity.setDiscount(model.getDiscount());
        entity.setDiscountPercentage(model.getDiscountPercentage());
        entity.setTaxPercentage(model.getTaxPercentage());
        entity.setAmountTax(model.getAmountTax());
        entity.setAmountExo(model.getAmountExo());
        entity.setAmountTotal(model.getAmountTotal());
        entity.setAuthorizingUser(model.getAuthorizingUser());
        entity.setAuthorizationDate(model.getAuthorizationDate());
        entity.setUserIssued(model.getUserIssued());
        entity.setDateOfIssue(model.getDateOfIssue());
        entity.setExonerationStatus(0L);
        entity.setStatus(model.getStatus());
        entity.setCreated(LocalDateTime.now());
        entity.setInventoryType(model.getInventoryType());
        entity.setSubWarehouse(model.getSubWarehouse());
        entity.setChannel(model.getChannel());
        entity.setExemptAmount(model.getExemptAmount());
        entity.setCustomerAddress(model.getCustomerAddress());
        entity.setCustomerRtnId(model.getCustomerRtnId());
        entity.setSellerName(user.get(0).getName());
        entity.setTotalLps(model.getTotalLps());
        entity.setTotalLpsLetters(model.getTotalLpsLetters());
        entity.setFiscalProcess(model.getFiscalProcess());
        entity.setIdInsuranceClaim(model.getIdInsuranceClaim());
        entity.setUti(model.getUti());
        this.billingRepository.save(entity);


    }

    @Override
    public void updateStatusInvoice(Long id, Long status, String authorizingUser, LocalDateTime authorizationDate,
                                    String userIssued, LocalDateTime dateOfIssue) {
        long startTime = System.currentTimeMillis();

        try {
            BillingEntity entity = this.billingRepository.findById(id).orElse(null);
            if (entity == null) {
                throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));
            }
            entity.setAuthorizingUser(authorizingUser);
            entity.setAuthorizationDate(authorizationDate);
            entity.setUserIssued(userIssued);
            entity.setDateOfIssue(dateOfIssue);
            entity.setStatus(status);

            this.billingRepository.save(entity);

        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;

            logsService.saveLog(2, id, "Error occurred while updating status invoice: " + e.getMessage(),
                    authorizingUser, duration);
            throw e;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;

            logsService.saveLog(2, id, "Error occurred while updating status invoice: " + e.getMessage(),
                    authorizingUser, duration);
            throw e;
        }
    }

    @Override
    public void updateDocumentNo(Long id, String documentNo) {
        long startTime = System.currentTimeMillis();

        try {
            BillingEntity entity = this.billingRepository.findById(id).orElse(null);
            if (entity == null) {
                throw new BadRequestException(String.format(Constants.ERROR_UPDATE_FIELDS, id));
            }
            entity.setDocumentNo(documentNo);

            this.billingRepository.save(entity);
        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;

            logsService.saveLog(3, id, "Error occurred while updating document number: " + e.getMessage(), null,
                    duration);

            throw e;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;

            logsService.saveLog(3, id, "Error occurred while updating document number: " + e.getMessage(), null,
                    duration);
            throw e;
        }
    }

    @Override
    public void updateCorporateClient(Long id, BillingModel model) {
        long startTime = System.currentTimeMillis();

        try {


            BillingEntity entity = this.billingRepository.findById(id).orElse(null);
            if (entity == null) {
                throw new BadRequestException(String.format(Constants.ERROR_UPDATE_FIELDS, id));
            }

            if (entity.getStatus() != 0) {
                throw new BadRequestException(
                        String.format("La factura no se puede exonerar, ya que no es una factura pendiente", id));
            }

            List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(999L);
            Map<String, String> parameters = new HashMap<>();
            for (ConfigParametersModel parameter : list) {
                parameters.put(parameter.getParameterName(), parameter.getParameterValue());
            }

            String invoiceType = parameters.get("INVOICE_TYPE_EXO");

            entity.setInvoiceType(invoiceType);
            entity.setCorrelativeOrdenExemptNo(model.getCorrelativeOrdenExemptNo());
            entity.setCorrelativeCertificateExoNo(model.getCorrelativeCertificateExoNo());
            entity.setExonerationStatus(1L);
            entity.setAmountTotal(entity.getAmountTotal() - entity.getAmountTax());
            entity.setAmountExo(entity.getAmountTax());
            entity.setAmountTax(0.0);

            this.billingRepository.save(entity);
        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;

            logsService.saveLog(4, id, "Error occurred while updating Corporate Client: " + e.getMessage(),
                    model.getUserIssued(), duration);
            throw e;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;

            logsService.saveLog(4, id, "Error occurred while updating Corporate Client: " + e.getMessage(),
                    model.getUserIssued(), duration);
            throw e;
        }
    }

    @Override
    public void updateSingleClient(Long id, BillingModel model) {
        long startTime = System.currentTimeMillis();

        try {
            BillingEntity entity = this.billingRepository.findById(id).orElse(null);
            if (entity == null) {
                throw new BadRequestException(String.format(Constants.ERROR_UPDATE_FIELDS, id));
            }

            if (entity.getStatus() != 0) {
                throw new BadRequestException(
                        String.format("La factura no se puede exonerar, ya que no es una factura pendiente", id));
            }

            List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(999L);
            Map<String, String> parameters = new HashMap<>();
            for (ConfigParametersModel parameter : list) {
                parameters.put(parameter.getParameterName(), parameter.getParameterValue());
            }

            String invoiceType = parameters.get("INVOICE_TYPE_EXO");

            entity.setInvoiceType(invoiceType);
            entity.setDiplomaticCardNo(model.getDiplomaticCardNo());
            entity.setExonerationStatus(1L);
            entity.setAmountTotal(entity.getAmountTotal() - entity.getAmountTax());
            entity.setAmountExo(entity.getAmountTax());
            entity.setAmountTax(0.0);

            this.billingRepository.save(entity);
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;

            logsService.saveLog(5, id, "Error occurred while updating Single Client: " + e.getMessage(),
                    model.getUserIssued(), duration);
            throw e;
        }
    }

    @Override
    public void updateStatusExoTax(Long id) {
        long startTime = System.currentTimeMillis();

        try {
            BillingEntity entity = this.billingRepository.findById(id).orElse(null);
            if (entity == null) {
                throw new BadRequestException(String.format(Constants.ERROR_UPDATE_FIELDS, id));
            }

            Long currentStatus = entity.getStatus();

            if (currentStatus == 3L) {
                entity.setStatus(5L);
            } else if (currentStatus == 4L) {
                entity.setStatus(5L);
            } else if (currentStatus == 5L) {
                entity.setStatus(10L);
            } else {
                throw new BadRequestException(String.format("Invalid status %s for update", currentStatus));
            }

            entity.setTaxPercentage(0.0);

            this.billingRepository.save(entity);
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;

            logsService.saveLog(6, id, "Error occurred while updating status invoice exonerated: " + e.getMessage(),
                    null, duration);
            throw e;
        }
    }
    @Override
    public InvoiceDetailGraphicsResponse getInvoiceDetailsGraphByDateRangeAndFilters(Optional<LocalDate> startDate,
                                                                                     Optional<LocalDate> endDate, List<String> agencies, List<String> territories, List<String> invoiceType,
                                                                                     List<Long> status) {

        Specification<BillingEntity> specInvoice = InvoiceGraphFiltersSpecifications.filterByGraph(startDate, endDate,
                agencies, territories, invoiceType, status);

        List<BillingEntity> billingEntities = billingRepository.findAll(specInvoice);

        Long unauthorizedInvoices = billingEntities.stream().filter(entity -> entity.getStatus() == 0).count();
        Long authorizedInvoices = billingEntities.stream().filter(entity -> entity.getStatus() == 1).count();
        Long issuedInvoices = billingEntities.stream().filter(entity -> entity.getStatus() == 2).count();
        Long invoicesWithTaxNumber = billingEntities.stream().filter(entity -> entity.getStatus() == 4).count();
        Long canceledInvoicesWithoutIssued = billingEntities.stream().filter(entity -> entity.getStatus() == 5).count();
        Long canceledInvoicesWithTaxNumber = billingEntities.stream().filter(entity -> entity.getStatus() == 6).count();
        Long errorInvoice = billingEntities.stream().filter(entity -> entity.getStatus() == -1).count();

        Map<String, Long> invoiceTypeCounts = billingEntities.stream()
                .collect(Collectors.groupingBy(BillingEntity::getInvoiceType, Collectors.counting()));

        Map<String, Long> sellerCounts = billingEntities.stream()
                .collect(Collectors.groupingBy(BillingEntity::getSeller, Collectors.counting()));

        InvoiceDetailGraphicsResponse response = new InvoiceDetailGraphicsResponse();
        response.setUnauthorizedInvoices(unauthorizedInvoices);
        response.setAuthorizedInvoices(authorizedInvoices);
        response.setIssuedInvoices(issuedInvoices);
        response.setInvoicesWithTaxNumber(invoicesWithTaxNumber);
        response.setCanceledInvoicesWithoutIssued(canceledInvoicesWithoutIssued);
        response.setCanceledInvoicesWithTaxNumber(canceledInvoicesWithTaxNumber);
        response.setErrorInvoice(errorInvoice);
        response.setInvoicesByType(invoiceTypeCounts);
        response.setInvoicesPerSeller(sellerCounts);

        return response;
    }

    private static final Map<Long, String> statusNames = new HashMap<>();

    static {
        statusNames.put(-1L, "errorInvoice");
        statusNames.put(0L, "unauthorizedInvoices");
        statusNames.put(1L, "authorizedInvoices");
        statusNames.put(2L, "issuedInvoices");
        statusNames.put(4L, "invoicesWithTaxNumber");
        statusNames.put(5L, "canceledInvoicesWithoutIssued");
        statusNames.put(6L, "canceledInvoicesWithTaxNumber");
    }

    @Override
    public List<InvoiceTypeAndStatus> getInvoiceDetailsByTypeAndStatusAndFilters(Optional<LocalDate> startDate,
                                                                                 Optional<LocalDate> endDate, List<String> agencies, List<String> territories, List<String> invoiceType,
                                                                                 List<Long> status) {

        Specification<BillingEntity> spec = InvoiceGraphFiltersSpecifications.filterByGraph(startDate, endDate,
                agencies, territories, invoiceType, status);

        List<BillingEntity> results = billingRepository.findAll(spec);

        Map<String, Map<String, Long>> groupedResults = results.stream()
                .collect(Collectors.groupingBy(BillingEntity::getInvoiceType, Collectors
                        .groupingBy(result -> statusNames.get(result.getStatus()), Collectors.counting())));

        return groupedResults.entrySet().stream().flatMap(entry -> entry.getValue().entrySet().stream().map(
                        statusEntry -> new InvoiceTypeAndStatus(entry.getKey(), statusEntry.getKey(), statusEntry.getValue())))
                .collect(Collectors.toList());
    }

    @Override
    public List<BranchOfficeAndStatus> getInvoiceDetailsByBranchOfficeAndStatus(Optional<LocalDate> startDate,
                                                                                Optional<LocalDate> endDate, List<String> agencies, List<String> territories, List<String> invoiceType,
                                                                                List<Long> status) {

        Specification<BillingEntity> spec = InvoiceGraphFiltersSpecifications.filterByGraph(startDate, endDate,
                agencies, territories, invoiceType, status);

        List<BillingEntity> results = billingRepository.findAll(spec);

        // Obtener los nombres de las agencias a partir de los resultados
        Map<String, List<BillingEntity>> groupedByAgency = results.stream()
                .collect(Collectors.groupingBy(BillingEntity::getAgency));

        // Agrupar los resultados por nombre de agencia y estado
        Map<String, Map<String, Long>> groupedResults = groupedByAgency.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().collect(
                        Collectors.groupingBy(result -> statusNames.get(result.getStatus()), Collectors.counting()))));

        // Convertir el Map en una lista de BranchOfficeAndStatus
        return groupedResults.entrySet().stream().flatMap(entry -> entry.getValue().entrySet().stream().map(
                        statusEntry -> new BranchOfficeAndStatus(entry.getKey(), statusEntry.getKey(), statusEntry.getValue())))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<InvoicesByNameOrRtnModel> getAllPendingByNameOrRtn(String name, String rtn) {
    	String likeName = "%" + name.toLowerCase() + "%";
    	String likeRtn = "%" + rtn.toLowerCase() + "%";
    	List<Object[]> data = new ArrayList<>();
    	if(!(name == "")) {
    		data = billingRepository.getAllPendingByName(likeName);
    	}
    	if(!(rtn == "")) {
    		data = billingRepository.getAllPendingByRtn(likeRtn);
    	}
    	List<InvoicesByNameOrRtnModel> dtoList = new ArrayList<>();
		for (Object[] result : data) {
			InvoicesByNameOrRtnModel dto = new InvoicesByNameOrRtnModel();
			dto.setId((BigDecimal) result[0]);
			dto.setInvoiceType((String) result[1]);
			dto.setStatus((BigDecimal) result[2]);
			dto.setExonerationStatus((BigDecimal) result[3]);
			dto.setCustomer((String) result[4]);
			dto.setSeller((String) result[5]);
			dto.setPrimaryIdentity((String) result[6]);
			dto.setCreated((Timestamp) result[7]);
			dtoList.add(dto);
		}
		return dtoList;
    }

    @Override
    public void delete(Long id) {
        BillingEntity entity = this.billingRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

        this.billingRepository.delete(entity);
    }

    @Override
    public String getInvoiceDetailBase64ById(Long id) {
        BillingEntity billingEntity = this.billingRepository.findById(id).orElse(null);
        if (billingEntity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

        BranchOfficesEntity branchOfficesEntity = this.branchOfficesRepository
                .findById(billingEntity.getIdBranchOffices()).orElse(null);
        if (branchOfficesEntity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));

        return InvoicePrintingDetailGenerator.generateInvoiceDetail(billingEntity, branchOfficesEntity);
    }

    @Override
    public BillingModel getInvoiceBySerialNumber(String serie) {
        InvoiceDetailEntity invoiceDetailentity = this.invoiceDetailRepository.getDetailBySerie(serie);
        if (invoiceDetailentity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD_SERIE, serie));

        BillingEntity billingEntity = this.billingRepository.findById(invoiceDetailentity.getBilling().getId())
                .orElse(null);

        if (billingEntity.getStatus() != 2)
            throw new BadRequestException(String.format(Constants.INVOICE_NOT_ISSUED));

        return billingEntity.entityToModel();
    }

    @Override
    public BillingModel getBillingByInsuranceClaim(Long idInsuranceClaim) {
        BillingEntity billingEntity = this.billingRepository.getBillingByInsuranceClaim(idInsuranceClaim);
        if (billingEntity != null)
            throw new BadRequestException(String.format(Constants.ERROR_INSURANCE_CLAIM, idInsuranceClaim));
        return null;
    }

    @Override
    public String resendTrama(ResendTramaRequest request) {
        this.processQueue.getProps();
        ReadFilesConfig readConfig = null;
        long startTimeQueue = 0;
        String trama = null;

        try {
            BillingEntity billingEntity = billingRepository.findById(request.getInvoiceId())
                    .orElseThrow(() -> new BadRequestException("Invoice not found"));

            switch (request.getTramaType()) {
                case "EMI":
                    trama = billingEntity.getTramaEmission();
                    if (trama == null || trama.isEmpty()) {
                        // Generar trama para EMI
                        trama = generateTramaForEmission(billingEntity);
                    }
                    break;
                case "ANU":
                    trama = billingEntity.getTramaCancellation();
                    if (trama == null || trama.isEmpty()) {
                        // Generar trama para ANU
                        trama = generateTramaForCancellation(billingEntity, 1); // 1 para ANULACION
                    }
                    break;
                case "ASE":
                    trama = billingEntity.getTramaCancellationIssued();
                    if (trama == null || trama.isEmpty()) {
                        // Generar trama para ASE
                        trama = generateTramaForCancellation(billingEntity, 2); // 2 para ANULACION_TOTAL
                    }
                    break;
                default:
                    throw new BadRequestException("Invalid trama type");
            }

            if (trama == null || trama.isEmpty()) {
                throw new BadRequestException("The trama is empty or not available for the given invoice.");
            }

            readConfig = new ReadFilesConfig();

            // Procesar la trama y obtener el resultado
            ProcessResult result = this.processQueue.processResendTrama(readConfig, startTimeQueue, trama);

            if (!result.isSuccess()) {
                throw new BadRequestException("Error processing trama: " + result.getMessage());
            }

        } catch (Exception e) {
            throw new BadRequestException("An error occurred while resending trama: " + e.getMessage());
        }

        return "Frame forwarded successfully: " + trama;
    }

    @Override
    public Page<BillingModel> getInvoicesOfTheDayAndStatusAndSeller(Pageable pageable, String seller) {
        Page<BillingEntity> entities = this.billingRepository.getInvoicesOfTheDayAndStatusAndSeller(pageable, seller.toUpperCase());

        return entities.map(BillingEntity::entityToModel);
    }

    // Método para generar la trama de emisión
    private String generateTramaForEmission(BillingEntity billingEntity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        StringBuilder marco = new StringBuilder("FACTURACION");
        marco.append("|EMISION");
        marco.append("|SUBSNUMB=")
                .append(billingEntity.getPrimaryIdentity() != null && !billingEntity.getPrimaryIdentity().isEmpty()
                        ? billingEntity.getPrimaryIdentity()
                        : billingEntity.getAcctCode());
        marco.append("|CUENTA_CLIENTE=").append(billingEntity.getCustomerId());
        marco.append("|CUENTA_FACTURACION=").append(billingEntity.getAcctCode());
        marco.append("|ID_PEDIDO_VENTA=").append(
                billingEntity.getUti() != null && !billingEntity.getUti().isEmpty() ? billingEntity.getUti() : 0);
        marco.append("|NUMERO_PREFACTURA=").append(billingEntity.getId());
        marco.append("|NUMERO_FACTURA=").append(billingEntity.getNumberDei());
        marco.append("|FECHAEMISION=").append(billingEntity.getDateOfIssue().format(formatter));
        marco.append("|IMEI=").append(billingEntity.getInvoiceDetails().get(0).getSerieOrBoxNumber());
        marco.append("|TOTAL_FACTURADO=").append(billingEntity.getAmountTotal());
        marco.append("|IMPUESTO=").append(billingEntity.getTaxPercentage());
        marco.append("|TIPO_FACTURA=").append(billingEntity.getInvoiceType());
        marco.append("|CAI=").append(billingEntity.getCai());
        marco.append("|USUARIO=").append(billingEntity.getUserIssued().toUpperCase());

        // Extraer valores de InvoiceNo
        String[] invoiceParts = billingEntity.getInvoiceNo().split(" ");
        String invoiceNo = invoiceParts.length > 0 ? invoiceParts[0] : "";
        String serialNum = invoiceParts.length > 1 ? invoiceParts[1] : "";

        marco.append("|SERIAL_NUM=").append(serialNum);
        marco.append("|INVOICE_NO=").append(invoiceNo);
        marco.append("|");

        return marco.toString();
    }


    // Método para generar la trama de cancelación
    private String generateTramaForCancellation(BillingEntity billingEntity, int typeCancellation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        StringBuilder marco = new StringBuilder("FACTURACION");

        if (typeCancellation == 1) {
            marco.append("|ANULACION");
        } else {
            marco.append("|ANULACION_TOTAL");
        }
        marco.append("|SUBSNUMB=")
                .append(billingEntity.getPrimaryIdentity() != null && !billingEntity.getPrimaryIdentity().isEmpty()
                        ? billingEntity.getPrimaryIdentity()
                        : billingEntity.getAcctCode());
        marco.append("|CUENTA_CLIENTE=").append(billingEntity.getCustomerId());
        marco.append("|CUENTA_FACTURACION=").append(billingEntity.getAcctCode());
        marco.append("|ID_PEDIDO_VENTA=").append(
                billingEntity.getUti() != null && !billingEntity.getUti().isEmpty() ? billingEntity.getUti() : 0);
        marco.append("|NUMERO_PREFACTURA=").append(billingEntity.getId());
        if (typeCancellation == 1) {
            marco.append("|NUMERO_FACTURA=");
        } else {
            marco.append("|NUMERO_FACTURA=").append(billingEntity.getNumberDei());
        }
        if (typeCancellation == 1) {
            marco.append("|FECHAEMISION=").append(billingEntity.getCreated().format(formatter));
        } else {
            marco.append("|FECHAEMISION=").append(billingEntity.getDateOfIssue().format(formatter));
        }
        marco.append("|IMEI=").append(billingEntity.getInvoiceDetails().get(0).getSerieOrBoxNumber());
        marco.append("|TOTAL_FACTURADO=").append(billingEntity.getAmountTotal());
        marco.append("|IMPUESTO=").append(billingEntity.getTaxPercentage());
        marco.append("|TIPO_FACTURA=").append(billingEntity.getInvoiceType());
        marco.append("|USUARIO=").append(billingEntity.getUserIssued().toUpperCase());
        if (typeCancellation == 1) {
            marco.append("|MOTIVO=").append("ANULACION PREFACTURA");
        } else {
            marco.append("|MOTIVO=998");
        }
        if (typeCancellation == 1) {
            marco.append("|CAI=");
        } else {
            marco.append("|CAI=").append(billingEntity.getCai());
        }
        marco.append("|PROGRAMA=|");

        return marco.toString();
    }
}
