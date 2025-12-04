package hn.com.tigo.simcardinquiry.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.SimcardDetailEntity;
import hn.com.tigo.simcardinquiry.entities.SimcardModelEntity;
import hn.com.tigo.simcardinquiry.entities.SimcardOrderControlEntity;
import hn.com.tigo.simcardinquiry.models.ConfigParameterModel;
import hn.com.tigo.simcardinquiry.models.DetailsModel;
import hn.com.tigo.simcardinquiry.models.GeneralResponseSimcard;
import hn.com.tigo.simcardinquiry.models.InvoiceDataModel;
import hn.com.tigo.simcardinquiry.models.ParameterModel;
import hn.com.tigo.simcardinquiry.models.ParametersModel;
import hn.com.tigo.simcardinquiry.models.ShieldingDataModel;
import hn.com.tigo.simcardinquiry.models.SimcardInquiryRequest;
import hn.com.tigo.simcardinquiry.models.SimcardInquiryResponse;
import hn.com.tigo.simcardinquiry.repositories.ISimcardDetailRepository;
import hn.com.tigo.simcardinquiry.repositories.ISimcardModelRepository;
import hn.com.tigo.simcardinquiry.repositories.ISimcardOrderControlRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.IConfigParameterService;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardInquiryService;
import hn.com.tigo.simcardinquiry.utils.Constants;
import hn.com.tigo.simcardinquiry.utils.EncryptDecrypt;

@Service
public class SimcardInquiryServiceImpl implements ISimcardInquiryService {

	private final ISimcardDetailRepository simcardDetailRepository;
	private final ISimcardOrderControlRepository simcardOrderControlRepository;
	private final ISimcardModelRepository simcardModelRepository;
	private final IConfigParameterService configParametersService;
	private final ILogsSimcardService logsService;

	public SimcardInquiryServiceImpl(ISimcardDetailRepository simcardDetailRepository,
			ISimcardOrderControlRepository simcardOrderControlRepository,
			 ISimcardModelRepository simcardModelRepository,
			IConfigParameterService configParametersService, ILogsSimcardService logsService) {
		super();
		this.simcardDetailRepository = simcardDetailRepository;
		this.simcardOrderControlRepository = simcardOrderControlRepository;
		this.simcardModelRepository = simcardModelRepository;
		this.configParametersService = configParametersService;
		this.logsService = logsService;
	}

	@Override
	public SimcardInquiryResponse processSimcardInquiry(SimcardInquiryRequest request) {
		try {

			SimcardDetailEntity simcardDetail = this.simcardDetailRepository.findByImsiOrIcc(2, request.getSimcard());
			if (simcardDetail == null) {
				return createErrorResponse("02", Constants.ERROR_NOT_FOUND_RECORD_SIMCARD);
			}

			SimcardOrderControlEntity simcardOrderControlEntity = this.simcardOrderControlRepository
					.findById(simcardDetail.getIdOrderControl()).orElse(null);
			if (simcardOrderControlEntity == null) {
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD_ORDER_CONTROL,
						simcardDetail.getIdOrderControl()));
			}

			SimcardModelEntity simcardModelEntity = this.simcardModelRepository
					.findById(Long.parseLong(simcardOrderControlEntity.getModel())).orElse(null);
			if (simcardModelEntity == null) {
				throw new BadRequestException(
						String.format(Constants.ERROR_NOT_FOUND_RECORD_MODEL, simcardOrderControlEntity.getModel()));
			}

			List<ConfigParameterModel> list = this.configParametersService.getByIdApplication(1000L);
			Map<String, String> parametersModel = new HashMap<>();
			for (ConfigParameterModel parameter : list) {
				parametersModel.put(parameter.getParameterName(), parameter.getParameterValue());
			}

			String ki =  EncryptDecrypt.decript(simcardDetail.getKi());
			String pin = EncryptDecrypt.decript(simcardDetail.getPin1());
			String puk1 = EncryptDecrypt.decript(simcardDetail.getPuk1());
			String puk2 = EncryptDecrypt.decript(simcardDetail.getPuk2());
			String pin2 = EncryptDecrypt.decript(simcardDetail.getPin2());

			SimcardInquiryResponse response = new SimcardInquiryResponse();

			// GeneralResponse
			GeneralResponseSimcard generalResponse = new GeneralResponseSimcard();
			generalResponse.setUti(UUID.randomUUID().toString());
			String code = Constants.CODE;
			String message = Constants.MESSAGGE;

			if (simcardDetail.getBillingStatus() != null && !"EMI".equals(simcardDetail.getBillingStatus())) {
				code = Constants.CODE_EMI_BILLING;
				message = Constants.ERROR_STATUS_EMI_BILLING;
			} else if (simcardDetail.getBillingStatus() == null) {
				code = Constants.CODE_NOT_RECORD_BILLING;
				message = Constants.ERROR_NOT_FOUND_RECORD_BILLING;
			}

			generalResponse.setStatus(Constants.STATUS);
			generalResponse.setCode(code);
			generalResponse.setMessage(message);
			response.setGeneralResponse(generalResponse);

			// Details
			DetailsModel details = new DetailsModel();
			details.setImsi(simcardDetail.getImsi());
			details.setEstado(" ");
			details.setKi(ki);
			details.setPin(pin);
			details.setPuk1(puk1);
			details.setPuk2(puk2);
			details.setIcc(simcardDetail.getIcc());

			InvoiceDataModel datosFactura = new InvoiceDataModel();
			datosFactura.setTipo("");
			datosFactura.setLetra("");
			datosFactura.setNumero("");
			datosFactura.setEstadoFactura("EMI".equals(simcardDetail.getBillingStatus()) ? "EMI" : "NF");
			datosFactura.setAnexoFacturado("");
			datosFactura.setFechaFactura("");
			datosFactura.setModelo(simcardDetail.getModel() != null ? simcardDetail.getModel() : "");
			details.setDatosFactura(datosFactura);

			// datosBlindaje
			ShieldingDataModel datosBlindaje = new ShieldingDataModel();
			datosBlindaje.setFechaEfectivo(null);
			datosBlindaje.setEstadoBlindaje(null);
			details.setDatosBlindaje(datosBlindaje);

			// parameters
			ParametersModel parameters = new ParametersModel();
			List<ParameterModel> parameterList = new ArrayList<>();

			ParameterModel parameterPin2 = new ParameterModel();
			parameterPin2.setName(Constants.PARAMETER_PIN_2);
			parameterPin2.setValue(pin2);
			parameterList.add(parameterPin2);

			ParameterModel parameterModel = new ParameterModel();
			parameterModel.setName(Constants.PARAMETER_MODEL);
			parameterModel.setValue(simcardModelEntity.getSimModel());
			parameterList.add(parameterModel);

			ParameterModel parameterDescriptionModel = new ParameterModel();
			parameterDescriptionModel.setName(Constants.PARAMETER__DESCRIPTION_MODEL);
			parameterDescriptionModel.setValue(simcardModelEntity.getModelDescription());
			parameterList.add(parameterDescriptionModel);

			ParameterModel parameterWarehouse = new ParameterModel();
			parameterWarehouse.setName(Constants.PARAMETER_WAREHOUSE);
			parameterWarehouse.setValue(simcardDetail.getWarehouse());
			parameterList.add(parameterWarehouse);

			ParameterModel parameterInventoryType = new ParameterModel();
			parameterInventoryType.setName(Constants.PARAMETER_INVENTORY_TYPE);
			parameterInventoryType.setValue(parametersModel.get("INVENTORY_TYPE"));
			parameterList.add(parameterInventoryType);

			parameters.setParameter(parameterList);
			details.setParameters(parameters);

			response.setDetails(details);

			return response;
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			return createErrorResponse(Constants.CODE_ERROR, e.getMessage());
		}
	}

	@Override
	public SimcardInquiryResponse processImsiInquiry(SimcardInquiryRequest request) {
		try {

			SimcardDetailEntity simcardDetailImsi = this.simcardDetailRepository.findByImsiOrIcc(1, request.getImsi());
			if (simcardDetailImsi == null) {
				return createErrorResponse("02", Constants.ERROR_NOT_FOUND_RECORD_IMSI);
			}

			SimcardDetailEntity simcardDetail = this.simcardDetailRepository.findByImsiOrIcc(2,
					simcardDetailImsi.getIcc());
			if (simcardDetail == null) {
				return createErrorResponse("02", Constants.ERROR_NOT_FOUND_RECORD_SIMCARD);
			}

			SimcardOrderControlEntity simcardOrderControlEntity = this.simcardOrderControlRepository
					.findById(simcardDetail.getIdOrderControl()).orElse(null);
			if (simcardOrderControlEntity == null) {
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD_ORDER_CONTROL,
						simcardDetail.getIdOrderControl()));
			}

			SimcardModelEntity simcardModelEntity = this.simcardModelRepository
					.findById(Long.parseLong(simcardOrderControlEntity.getModel())).orElse(null);
			if (simcardModelEntity == null) {
				throw new BadRequestException(
						String.format(Constants.ERROR_NOT_FOUND_RECORD_MODEL, simcardOrderControlEntity.getModel()));
			}

			List<ConfigParameterModel> list = this.configParametersService.getByIdApplication(1000L);
			Map<String, String> parametersModel = new HashMap<>();
			for (ConfigParameterModel parameter : list) {
				parametersModel.put(parameter.getParameterName(), parameter.getParameterValue());
			}

			String ki =  EncryptDecrypt.decript(simcardDetail.getKi());
			String pin = EncryptDecrypt.decript(simcardDetail.getPin1());
			String puk1 = EncryptDecrypt.decript(simcardDetail.getPuk1());
			String puk2 = EncryptDecrypt.decript(simcardDetail.getPuk2());
			String pin2 = EncryptDecrypt.decript(simcardDetail.getPin2());

			SimcardInquiryResponse response = new SimcardInquiryResponse();

			// GeneralResponse
			GeneralResponseSimcard generalResponse = new GeneralResponseSimcard();
			generalResponse.setUti(UUID.randomUUID().toString());
			String code = Constants.CODE;
			String message = Constants.MESSAGGE;

			if (simcardDetail.getBillingStatus() != null && !"EMI".equals(simcardDetail.getBillingStatus())) {
				code = Constants.CODE_EMI_BILLING;
				message = Constants.ERROR_STATUS_EMI_BILLING;
			} else if (simcardDetail.getBillingStatus() == null) {
				code = Constants.CODE_NOT_RECORD_BILLING;
				message = Constants.ERROR_NOT_FOUND_RECORD_BILLING;
			}

			generalResponse.setStatus(Constants.STATUS);
			generalResponse.setCode(code);
			generalResponse.setMessage(message);
			response.setGeneralResponse(generalResponse);

			// Details
			DetailsModel details = new DetailsModel();
			details.setImsi(simcardDetail.getImsi());
			details.setEstado(" ");
			details.setKi(ki);
			details.setPin(pin);
			details.setPuk1(puk1);
			details.setPuk2(puk2);
			details.setIcc(simcardDetail.getIcc());

			// datosFactura
			InvoiceDataModel datosFactura = new InvoiceDataModel();
			datosFactura.setTipo("");
			datosFactura.setLetra("");
			datosFactura.setNumero("");
			datosFactura.setEstadoFactura("EMI".equals(simcardDetail.getBillingStatus()) ? "EMI" : "NF");
			datosFactura.setAnexoFacturado("");
			datosFactura.setFechaFactura("");
			datosFactura.setModelo(simcardDetail.getModel() != null ? simcardDetail.getModel() : "");
			details.setDatosFactura(datosFactura);

			// datosBlindaje
			ShieldingDataModel datosBlindaje = new ShieldingDataModel();
			datosBlindaje.setFechaEfectivo(null);
			datosBlindaje.setEstadoBlindaje(null);
			details.setDatosBlindaje(datosBlindaje);

			// parameters
			ParametersModel parameters = new ParametersModel();
			List<ParameterModel> parameterList = new ArrayList<>();

			ParameterModel parameterPin2 = new ParameterModel();
			parameterPin2.setName(Constants.PARAMETER_PIN_2);
			parameterPin2.setValue(pin2);
			parameterList.add(parameterPin2);

			ParameterModel parameterModel = new ParameterModel();
			parameterModel.setName(Constants.PARAMETER_MODEL);
			parameterModel.setValue(simcardModelEntity.getSimModel());
			parameterList.add(parameterModel);

			ParameterModel parameterDescriptionModel = new ParameterModel();
			parameterDescriptionModel.setName(Constants.PARAMETER__DESCRIPTION_MODEL);
			parameterDescriptionModel.setValue(simcardModelEntity.getModelDescription());
			parameterList.add(parameterDescriptionModel);

			ParameterModel parameterWarehouse = new ParameterModel();
			parameterWarehouse.setName(Constants.PARAMETER_WAREHOUSE);
			parameterWarehouse.setValue(simcardDetail.getWarehouse());
			parameterList.add(parameterWarehouse);

			ParameterModel parameterInventoryType = new ParameterModel();
			parameterInventoryType.setName(Constants.PARAMETER_INVENTORY_TYPE);
			parameterInventoryType.setValue(parametersModel.get("INVENTORY_TYPE"));
			parameterList.add(parameterInventoryType);

			parameters.setParameter(parameterList);
			details.setParameters(parameters);

			response.setDetails(details);

			return response;
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			return createErrorResponse(Constants.CODE_ERROR, e.getMessage());
		}
	}

	private SimcardInquiryResponse createErrorResponse(String code, String message) {
		SimcardInquiryResponse response = new SimcardInquiryResponse();
		GeneralResponseSimcard generalResponse = new GeneralResponseSimcard();
		generalResponse.setUti(UUID.randomUUID().toString());
		generalResponse.setStatus(Constants.STATUS);
		generalResponse.setCode(code);
		generalResponse.setMessage(message);
		response.setGeneralResponse(generalResponse);
		response.setDetails(new DetailsModel());
		return response;
	}

}
