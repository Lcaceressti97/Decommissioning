package hn.com.tigo.equipmentinsurance.provider;

import java.util.Map;

import javax.ws.rs.core.Response;
import javax.xml.soap.SOAPException;

import org.json.JSONObject;

import hn.com.tigo.core.exception.HttpClientException;
import hn.com.tigo.core.http.Buildable;
import hn.com.tigo.core.http.PoolingBuilder;
import hn.com.tigo.core.http.PostMethod;
import hn.com.tigo.core.invoice.utils.InvoiceUtils;

public class HttpVoucher extends PostMethod<String> {

	/** Attribute that determine buildable. */
	private static Buildable buildable = PoolingBuilder.getInstance();

	/**
	 * Instantiates a new HTTP implementation for CBS services.
	 *
	 * @param url        the end point CBS
	 * @param properties the HTTP header properties
	 * @throws SOAPException the SOAP exception
	 */
	public HttpVoucher(final String url, final Map<String, String> properties) throws SOAPException {
		super(url, url, buildable, properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hn.com.tigo.josm.common.http.ServiceInvoker#getResponse(java.lang.String,
	 * int, java.lang.String)
	 */
	@Override
	public String getResponse(final String response, final int responseCode, final String responseMessage,
			final InvoiceUtils utils) throws HttpClientException {

		System.out.println("response code" + responseCode);

		if (responseCode == Response.Status.OK.getStatusCode()) {
			return response;
		} else if (response != null && !response.isEmpty()) {
			JSONObject responseGetObj;
			try {
				responseGetObj = utils.getJsonResponse(response);
				final JSONObject envResponse = responseGetObj.getJSONObject("S:Envelope");
				final JSONObject soapResponse = envResponse.getJSONObject("S:Body");
				final JSONObject vResponse = soapResponse.getJSONObject("ns1:Fault");
				final JSONObject internalResponse = vResponse.getJSONObject("ns1:Reason");
				return internalResponse.get("ns1:Text").toString();
			} catch (Exception e) {
				System.out.println(e.getMessage() + "Aqui1");
				throw new HttpClientException(responseMessage);
			}
		}

		throw new HttpClientException(responseMessage);
	}

}
