package hn.com.tigo.selfconsumption.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import hn.com.tigo.selfconsumption.models.OfferingInfoModel;
import hn.com.tigo.selfconsumption.models.ParametersAutoconsumoModel;
import hn.com.tigo.selfconsumption.provider.OfferingInfoProvider;
import hn.com.tigo.selfconsumption.services.interfaces.IOfferingInfoService;
import hn.com.tigo.selfconsumption.services.interfaces.IParametersAutoconsumoService;
import hn.com.tigo.selfconsumption.services.offeringinfo.AdapterException_Exception;
import hn.com.tigo.selfconsumption.services.offeringinfo.ParameterArray;
import hn.com.tigo.selfconsumption.services.offeringinfo.ParameterType;
import hn.com.tigo.selfconsumption.services.offeringinfo.TaskRequestType;
import hn.com.tigo.selfconsumption.services.offeringinfo.TaskResponseType;

@Service
public class OfferingInfoServiceImpl implements IOfferingInfoService {

	private final IParametersAutoconsumoService parametersAutoconsumoService;

	public OfferingInfoServiceImpl(IParametersAutoconsumoService parametersAutoconsumoService) {
		super();
		this.parametersAutoconsumoService = parametersAutoconsumoService;
	}

	@Override
	public TaskResponseType executeTask(String offeringId) throws AdapterException_Exception {
		try {
			List<ParametersAutoconsumoModel> list = this.parametersAutoconsumoService.getByIdApplication(1000L);
			Map<String, String> parameters = new HashMap<>();
			for (ParametersAutoconsumoModel parameter : list) {
				parameters.put(parameter.getName(), parameter.getValue());
			}

			OfferingInfoProvider offeringInfoProvider = new OfferingInfoProvider(
					parameters.get("OFFERING_INFO_PROVIDER"));
			String tariffFlag = parameters.get("TARIFF_FLAG");

			TaskRequestType taskRequestType = new TaskRequestType();
			ParameterArray parameterArray = new ParameterArray();
			ParameterType parameterType = new ParameterType();
			OfferingInfoModel offeringInfoModel = new OfferingInfoModel();

			offeringInfoModel.setOfferingID(offeringId);
			offeringInfoModel.setTariffFlag(tariffFlag);

			Gson gson = new Gson();
			String json = gson.toJson(offeringInfoModel);

			parameterType.setName("JSON");
			parameterType.setValue(json);
			parameterArray.getParameter().add(parameterType);
			taskRequestType.setParameters(parameterArray);

			TaskResponseType taskResponseType = offeringInfoProvider.executeTask(taskRequestType);

			return taskResponseType;
		} catch (AdapterException_Exception e) {
			System.err.println("BadRequestException: " + e.getMessage());

			throw new BadRequestException(e.getMessage());

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

}
