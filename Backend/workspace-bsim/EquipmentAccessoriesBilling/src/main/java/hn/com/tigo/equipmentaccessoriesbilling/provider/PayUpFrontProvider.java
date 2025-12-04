package hn.com.tigo.equipmentaccessoriesbilling.provider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.AdapterException_Exception;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.CBS3ArPayUpFrontTask;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.CBS3ArPayUpFrontTaskService;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.TaskRequestType;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.TaskResponseType;


public class PayUpFrontProvider {

	private String endPoint;
	private final URL url;
	CBS3ArPayUpFrontTaskService webService = null;
	CBS3ArPayUpFrontTask webServicePort = null;
	
	/**
	 * 
	 * @param endPoint
	 * @throws MalformedURLException
	 */
	public PayUpFrontProvider(String endPoint) throws MalformedURLException {
		this.endPoint = endPoint;
		this.url = new URL(endPoint + "?wsdl");
		webService = new CBS3ArPayUpFrontTaskService(this.url);
		webServicePort = webService.getCBS3ArPayUpFrontTaskPort();
		Map<String, Object> ctx = ((BindingProvider) webServicePort).getRequestContext();
		ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);
	}

	public TaskResponseType executeTask(TaskRequestType arg0) throws AdapterException_Exception {
		return webServicePort.executeTask(arg0);
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
