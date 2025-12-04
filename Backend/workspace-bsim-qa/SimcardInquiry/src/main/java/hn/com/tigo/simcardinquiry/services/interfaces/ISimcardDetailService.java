package hn.com.tigo.simcardinquiry.services.interfaces;

import java.util.List;

import hn.com.tigo.simcardinquiry.models.SimcardDetailModel;
import hn.com.tigo.simcardinquiry.models.SimcardOrderDetailModel;

public interface ISimcardDetailService {

	List<SimcardDetailModel> getSimcardDetailById(Long idOrderControl);

	SimcardDetailModel getById(Long id);

	SimcardDetailModel getBySimcard(String icc);

	SimcardOrderDetailModel findByImsiOrIcc(int type, String value) throws Exception;

	void updateStatusSimcardDetails(Long idOrderControl);
}
