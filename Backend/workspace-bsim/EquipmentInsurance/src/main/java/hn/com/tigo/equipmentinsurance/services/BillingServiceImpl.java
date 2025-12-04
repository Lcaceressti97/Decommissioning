package hn.com.tigo.equipmentinsurance.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.BadRequestException;

import hn.com.tigo.equipmentinsurance.models.*;
import hn.com.tigo.equipmentinsurance.provider.CustomerInfoProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.BillingEntity;
import hn.com.tigo.equipmentinsurance.entities.BillingServicesEntity;
import hn.com.tigo.equipmentinsurance.entities.BranchOfficesEntity;
import hn.com.tigo.equipmentinsurance.entities.ChannelEntity;
import hn.com.tigo.equipmentinsurance.entities.InvoiceDetailEntity;
import hn.com.tigo.equipmentinsurance.entities.UserBranchOfficesEntity;
import hn.com.tigo.equipmentinsurance.entities.UsersEntity;
import hn.com.tigo.equipmentinsurance.repositories.IBillingRepository;
import hn.com.tigo.equipmentinsurance.repositories.IBillingServicesRepository;
import hn.com.tigo.equipmentinsurance.repositories.IBranchOfficesRepository;
import hn.com.tigo.equipmentinsurance.repositories.IChannelRepository;
import hn.com.tigo.equipmentinsurance.repositories.IInsuranceRatesRepository;
import hn.com.tigo.equipmentinsurance.repositories.IInvoiceDetailRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IBillingService;
import hn.com.tigo.equipmentinsurance.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentinsurance.services.interfaces.IExchangeRateService;
import hn.com.tigo.equipmentinsurance.services.interfaces.IUserBranchOfficesService;
import hn.com.tigo.equipmentinsurance.services.interfaces.IUsersService;
import hn.com.tigo.equipmentinsurance.utils.Constants;

@Service
public class BillingServiceImpl implements IBillingService {

	private final IBillingRepository billingRepository;
	private final IInvoiceDetailRepository invoiceDetailRepository;
	private final IConfigParametersService configParametersService;
	private final IUsersService usersService;
	private final IUserBranchOfficesService userBranchOfficesService;
	private final IBranchOfficesRepository branchOfficesRepository;
	private final AuthenticationBsimService authenticationService;
	private final ReserveSerialBsimService reserveSeriesService;
	private final IInsuranceRatesRepository insuranceRatesRepository;
	private final IChannelRepository channelRepository;
	private final IExchangeRateService exchangeRateService;
	private final IBillingServicesRepository billingServicesRepository;
	private final CustomerInfoProvider customerInfoProvider;

	public BillingServiceImpl(IBillingRepository billingRepository, IInvoiceDetailRepository invoiceDetailRepository,
							  IConfigParametersService configParametersService, IUsersService usersService,
							  IUserBranchOfficesService userBranchOfficesService, IBranchOfficesRepository branchOfficesRepository,
							  AuthenticationBsimService authenticationService, ReserveSerialBsimService reserveSeriesService,
							  IInsuranceRatesRepository insuranceRatesRepository, IChannelRepository channelRepository,
							  IExchangeRateService exchangeRateService, IBillingServicesRepository billingServicesRepository, CustomerInfoProvider customerInfoProvider) {
		super();
		this.billingRepository = billingRepository;
		this.invoiceDetailRepository = invoiceDetailRepository;
		this.configParametersService = configParametersService;
		this.usersService = usersService;
		this.userBranchOfficesService = userBranchOfficesService;
		this.branchOfficesRepository = branchOfficesRepository;
		this.authenticationService = authenticationService;
		this.reserveSeriesService = reserveSeriesService;
		this.insuranceRatesRepository = insuranceRatesRepository;
		this.channelRepository = channelRepository;
		this.exchangeRateService = exchangeRateService;
		this.billingServicesRepository = billingServicesRepository;
		this.customerInfoProvider = customerInfoProvider;
	}

	@Override
	public BillingModel getById(Long id) {
		BillingEntity entity = this.billingRepository.findById(id).orElse(null);

		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		return entity.entityToModel();
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
		
		/*
		if (channelEntity.getReserveSerialNumber() == 1) {
			// Reservar los números de serie
			for (InvoiceDetailEntity detail : model.getInvoiceDetails()) {
				if (detail.getSerieOrBoxNumber() != null && !detail.getSerieOrBoxNumber().isEmpty()) {
					// Obtener el token de autenticación
					AuthenticationBsimResponse accessToken = authenticationService.getAccessToken();

					List<InvoiceDetailEntity> detailList = Arrays.asList(detail);
					reserveSeriesService.reserveSeries(accessToken.getAccess_token(), model.getInventoryType(),
							detail.getModel(), model.getWarehouse(), model.getSubWarehouse(), detailList); 
					reserveSeriesService.unloadReservedStock(accessToken.getAccess_token(), model.getInventoryType(),
						detail.getModel(), model.getWarehouse(), model.getSubWarehouse(), detail.getReserveKey(), detailList);
				}
			}
		} */

		List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(999L);
		Map<String, String> parameters = new HashMap<>();
		for (ConfigParametersModel parameter : list) {
			parameters.put(parameter.getParameterName(), parameter.getParameterValue());
		}

		String deductibleModel = parameters.get("DEDUCTIBLE_MODEL");

		ConfigParametersModel configModel = this.configParametersService.getByName("CBS_QUERY_CUSTOMER_INFO_TASK_URL");
		CustomerInfoModel customerInfo = this.customerInfoProvider.getCustomerInfoByAcctCode(model.getAcctCode(), configModel.getParameterValue());
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
		entity.setCustomer(model.getCustomer());
		entity.setCustomerId(model.getCustomerId());
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
		entity.setCustomerAddress(customerInfo.getCustomerAddress());
		entity.setCustomerRtnId(customerInfo.getCustomerRtn());
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
			Double subtotal;
			if (detailModel.getSerieOrBoxNumber() != null && !detailModel.getSerieOrBoxNumber().isEmpty()) {
				subtotal = 0.00;
			} else {
				subtotal = (detailModel.getSubtotal() != null) ? detailModel.getSubtotal()
						: detailModel.getQuantity() * detailModel.getUnitPrice();
			}
			BigDecimal roundedSubTotal = BigDecimal.valueOf(subtotal).setScale(2, RoundingMode.HALF_UP);
			detailEntity.setSubtotal(roundedSubTotal.doubleValue());

			Double discount = (detailModel.getDiscount() != null) ? detailModel.getDiscount() : 0.00;
			detailEntity.setDiscount(discount);

			// Calculo del impuesto
			Double tax = 0.00;

			// Verificar si el modelo deducible coincide con el modelo del detalle
			if (deductibleModel.equals(detailModel.getModel())) {
				// Si coincide, el impuesto es 0
				tax = 0.00;
			} else {
				// Si no coincide, calcular el impuesto normalmente
				if (subtotal > 0) {
					Double taxableAmount = subtotal - discount;
					tax = (detailModel.getTax() != null) ? detailModel.getTax()
							: taxableAmount * getTaxPercentage() / 100;
				}
			}

			// Redondear el impuesto a 2 decimales
			BigDecimal roundedTax = BigDecimal.valueOf(tax).setScale(2, RoundingMode.HALF_UP);
			detailEntity.setTax(roundedTax.doubleValue());

			// Calculo del total
			Double amountTotal = (detailModel.getAmountTotal() != null) ? detailModel.getAmountTotal()
					: subtotal + tax - discount;

			// Redondear a 4 decimales
			BigDecimal roundedAmountTotal = BigDecimal.valueOf(amountTotal).setScale(2, RoundingMode.HALF_UP);
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
		TotalInvoiceModel totals = calculateInvoiceTotals(invoiceDetails);

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
		boolean modelFound = false;

		for (InvoiceDetailEntity detail : invoiceDetails) {
			model = detail.getModel();
			unitPrice = detail.getUnitPrice();

			// Verificar si el modelo y el precio son válidos
			if (model != null && unitPrice != null) {
				modelFound = true;
				break;
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

	private TotalInvoiceModel calculateInvoiceTotals(List<InvoiceDetailEntity> invoiceDetails) {
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
		String deductibleModel = parameters.get("DEDUCTIBLE_MODEL");

		for (InvoiceDetailEntity detail : invoiceDetails) {

			Double subtotal = (detail.getSubtotal() != null) ? detail.getSubtotal()
					: detail.getQuantity() * detail.getUnitPrice();

			Double discount = (detail.getDiscount() != null) ? detail.getDiscount() : 0.00;

			// Calculo del impuesto
			Double taxableAmount = subtotal - discount;
			Double tax;
			// Verificar si el detalle es un deducible
			if (deductibleModel.equals(detail.getModel())) {
				// Si es deducible, no calcular el impuesto
				tax = 0.0;
			} else {
				tax = (detail.getTax() != null) ? detail.getTax() : taxableAmount * getTaxPercentage() / 100;
				// Redondear el impuesto a 4 decimales
				BigDecimal taxBD = BigDecimal.valueOf(tax).setScale(2, RoundingMode.HALF_UP);
				tax = taxBD.doubleValue();
			}

			// Calculo del total
			Double amountTotal = subtotal + tax - discount;
			BigDecimal amountTotalBD = BigDecimal.valueOf(amountTotal).setScale(2, RoundingMode.HALF_UP);
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
		BigDecimal totalTaxBD = BigDecimal.valueOf(totalTax).setScale(2, RoundingMode.HALF_UP);
		totalTax = totalTaxBD.doubleValue();

		BigDecimal totalSubtotalBD = BigDecimal.valueOf(totalSubtotal).setScale(2, RoundingMode.HALF_UP);
		totalSubtotal = totalSubtotalBD.doubleValue();

		BigDecimal totalDiscountBD = BigDecimal.valueOf(totalDiscount).setScale(2, RoundingMode.HALF_UP);
		totalDiscount = totalDiscountBD.doubleValue();

		BigDecimal totalAmountBD = BigDecimal.valueOf(totalAmount).setScale(2, RoundingMode.HALF_UP);
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

}
