package hn.com.tigo.selfconsumption.provider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import hn.com.tigo.selfconsumption.services.offeringinfo.AdapterException_Exception;
import hn.com.tigo.selfconsumption.services.offeringinfo.CBSQueryOfferingInfoTask;
import hn.com.tigo.selfconsumption.services.offeringinfo.CBSQueryOfferingInfoTaskService;
import hn.com.tigo.selfconsumption.services.offeringinfo.TaskRequestType;
import hn.com.tigo.selfconsumption.services.offeringinfo.TaskResponseType;

public class OfferingInfoProvider {

	private String endPoint;
	private final URL url;
	CBSQueryOfferingInfoTaskService webService = null;
	CBSQueryOfferingInfoTask webServicePort = null;

	/**
	 * 
	 * @param endPoint
	 * @throws MalformedURLException
	 */
	public OfferingInfoProvider(String endPoint) throws MalformedURLException {
		this.endPoint = endPoint;
		this.url = new URL(endPoint + "?wsdl");
		webService = new CBSQueryOfferingInfoTaskService(this.url);
		webServicePort = webService.getCBSQueryOfferingInfoTaskPort();
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
