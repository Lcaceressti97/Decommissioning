package hn.com.tigo.equipmentinsurance.services;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.models.ConfigParametersModel;
import hn.com.tigo.equipmentinsurance.models.ExchangeRateModel;
import hn.com.tigo.equipmentinsurance.provider.ExchangeRateServiceProvider;
import hn.com.tigo.equipmentinsurance.services.exchangerate.ExchangeResponseType;
import hn.com.tigo.equipmentinsurance.services.exchangerate.ExchangeTypeG;
import hn.com.tigo.equipmentinsurance.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentinsurance.services.interfaces.IExchangeRateService;

@Service
public class ExchangeRateServiceImpl implements IExchangeRateService {

	private final IConfigParametersService configParametersService;

	public ExchangeRateServiceImpl(IConfigParametersService configParametersService) {
		super();
		this.configParametersService = configParametersService;
	}

	@Override
	public ExchangeRateModel getExchangeRate() {
		
		ExchangeRateModel exchangeRateModel = new ExchangeRateModel();
		
		try {

			List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(2604L);
			HashMap<String, String> parameters = new HashMap<String, String>();
			for (ConfigParametersModel parameter : list) {
				parameters.put(parameter.getParameterName(), parameter.getParameterValue());
			}

			ExchangeRateServiceProvider service = new ExchangeRateServiceProvider(
					parameters.get("ENDPOINT_EXCHANGE_RATE"),parameters);
			
			ExchangeTypeG request = new ExchangeTypeG();
			request.setIDSource(2);
			request.setIDTarget(1);

			ExchangeResponseType response = service.callService(request);

			exchangeRateModel.setIdExchange(response.getExchange().get(0).getIDExchange());
			exchangeRateModel.setSalePrice(response.getExchange().get(0).getSalePrice());
			exchangeRateModel.setPurchasePrice(response.getExchange().get(0).getPurchasePrice());


			return exchangeRateModel;
		} catch (BadRequestException e) {
			throw e;

		} catch (Exception e) {
			System.out.println("Exception:" +  e);
			return null;
		}
	}

}
