package hn.com.tigo.equipmentinsurance.provider;

import hn.com.tigo.equipmentinsurance.services.executeorder.ExecuteOrderService;
import hn.com.tigo.equipmentinsurance.services.executeorder.Order;
import hn.com.tigo.equipmentinsurance.services.executeorder.OrderResponse;
import hn.com.tigo.equipmentinsurance.services.executeorder.SimpleOrderRequest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;

public class NonFiscalNoteProvider {
    private String endPoint;
    private final URL url;
    ExecuteOrderService webService = null;
    Order webServicePort = null;

    /**
     * @param endPoint
     * @throws MalformedURLException
     */
    public NonFiscalNoteProvider(String endPoint) throws MalformedURLException {
        this.endPoint = endPoint;
        this.url = new URL(endPoint + "?wsdl");
        webService = new ExecuteOrderService(this.url);
        webServicePort = webService.getExecuteOrderPort();
        Map<String, Object> ctx = ((BindingProvider) webServicePort).getRequestContext();
        ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);
    }

    public OrderResponse executeTask(SimpleOrderRequest simpleOrderRequest) {
        return webServicePort.purchaseProduct(simpleOrderRequest);
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

