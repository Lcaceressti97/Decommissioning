package hn.com.tigo.simcardinquiry.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import hn.com.tigo.simcardinquiry.services.emailservice.AttachmentsDTO;
import hn.com.tigo.simcardinquiry.services.emailservice.EmailService;
import hn.com.tigo.simcardinquiry.services.emailservice.EmailService_Service;
import hn.com.tigo.simcardinquiry.services.emailservice.GeneralResponse;
import hn.com.tigo.simcardinquiry.services.emailservice.SentDTO;

public class EmailServiceProvider {

	private String endPoint;
	private final URL url;
	EmailService_Service webService = null;
	EmailService webServicePort = null;

	/**
	 * 
	 * @param endPoint
	 * @throws MalformedURLException
	 */
	public EmailServiceProvider(String endPoint) throws MalformedURLException {
		this.endPoint = endPoint;
		this.url = new URL(endPoint + "?wsdl");
		webService = new EmailService_Service(this.url);
		webServicePort = webService.getEmailServicePort();
		Map<String, Object> ctx = ((BindingProvider) webServicePort).getRequestContext();
		ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);
	}

	public GeneralResponse sendMessage(final String from, final SentDTO sendTo, final String cc, final String subject,
			final String body, final AttachmentsDTO attachments) {
		return webServicePort.sendMessage(from, sendTo, cc, subject, body, attachments);
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @return the endPoint
	 */
	public String getEndPoint() {
		return endPoint;
	}

	/**
	 * @param endPoint the endPoint to set
	 */
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
}
