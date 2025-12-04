package hn.com.tigo.simcardinquiry.services.interfaces;

import hn.com.tigo.simcardinquiry.models.SimcardInquiryRequest;
import hn.com.tigo.simcardinquiry.models.SimcardInquiryResponse;

public interface ISimcardInquiryService {

	SimcardInquiryResponse processSimcardInquiry(SimcardInquiryRequest request);
	
	SimcardInquiryResponse processImsiInquiry(SimcardInquiryRequest request);


}
