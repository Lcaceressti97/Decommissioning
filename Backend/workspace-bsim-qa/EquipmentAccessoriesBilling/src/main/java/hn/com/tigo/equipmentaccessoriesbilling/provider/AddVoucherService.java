package hn.com.tigo.equipmentaccessoriesbilling.provider;

import java.net.MalformedURLException;
import java.util.Map;

import javax.xml.soap.SOAPException;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherType;

@Service
public class AddVoucherService extends AbstractDriver<VoucherType>{
	
	
	public AddVoucherService() throws MalformedURLException, SOAPException {
		super();

		
	}
	
	public String getRequest(VoucherType voucherType, Map<String, String> parameters) {
		
		String request = null;
		String buildRequest;
		try {
			buildRequest = this.getRequest(voucherType, VoucherType.class, "addVoucher",parameters.get("USER_VOUCHER_SERVICE"),parameters.get("PASS_VOUCHER_SERVICE"));
			

			String updateRequest = buildRequest.replace("http://schemas.xmlsoap.org/soap/envelope/", "http://www.w3.org/2003/05/soap-envelope");
			
			
			
			String requestFinal = updateRequest.replace("SOAP-ENV", "env");
			
			String changeMain = requestFinal.replace("<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">", "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\" xmlns=\"http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema\">");
			
			String requestEnd = changeMain.replace("<addVoucher xmlns=\"http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema\">", "<addVoucher>");
			
			request = requestEnd;
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("REQUEST: " +  request);

		return request;
	}

}
