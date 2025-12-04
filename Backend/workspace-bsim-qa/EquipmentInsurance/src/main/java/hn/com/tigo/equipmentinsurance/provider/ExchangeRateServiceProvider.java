package hn.com.tigo.equipmentinsurance.provider;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.SOAPException;

import hn.com.tigo.equipmentinsurance.services.exchangerate.ExchangeResponseType;
import hn.com.tigo.equipmentinsurance.services.exchangerate.ExchangeTypeG;

public class ExchangeRateServiceProvider extends AbstractDriverExchangeRate<ExchangeTypeG> {

	private HttpVoucher http;

	protected HashMap<String, String> parameters;

	/**
	 * 
	 * @param endPoint
	 * @throws MalformedURLException
	 * @throws SOAPException
	 */
	public ExchangeRateServiceProvider(String endPoint, final HashMap<String, String> parameters) throws SOAPException {
		super();
		this.parameters = parameters;
		final Map<String, String> httpProperties = new HashMap<String, String>();
		httpProperties.put("Content-Type", "text/xml; charset=utf-8");
		httpProperties.put("Connection", "Keep-Alive");
		http = new HttpVoucher(endPoint + "?wsdl", httpProperties);
		this.setHttpVoucher(http);
	}

	public ExchangeResponseType callService(final ExchangeTypeG request) throws Exception {
		String requestStr = this.getRequest(request, ExchangeTypeG.class, "getExchange",
				parameters.get("USER_EXCHANGE_RATE"), parameters.get("PASS_EXCHANGE_RATE"));
		// requestStr = requestStr.replace("http://schemas.xmlsoap.org/soap/envelope/",
		// "http://www.w3.org/2003/05/soap-envelope");
		System.out.println("requestStr:" + requestStr);

		String response = http.invoke(requestStr, StandardCharsets.UTF_8, null);

		if ("-1".equals(response)) {
			throw new Exception("Error call service exchange rate");
		}
		response = response.replace("http://www.w3.org/2003/05/soap-envelope",
				"http://schemas.xmlsoap.org/soap/envelope/");
		return this.getResult2(response, ExchangeResponseType.class);
	}

}
