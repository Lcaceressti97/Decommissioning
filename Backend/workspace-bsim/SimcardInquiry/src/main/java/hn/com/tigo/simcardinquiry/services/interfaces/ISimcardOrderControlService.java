package hn.com.tigo.simcardinquiry.services.interfaces;

import java.util.List;

import hn.com.tigo.simcardinquiry.models.GeneralResponse;
import hn.com.tigo.simcardinquiry.models.OrderFilesModel;
import hn.com.tigo.simcardinquiry.models.SimcardOrderControlModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISimcardOrderControlService {

	Page<SimcardOrderControlModel> getSimcardOrderControlPaginated(Pageable pageable);
	
	List<SimcardOrderControlModel> getOrderControlByIdSupplier(Long idSupplier);
	
	List<SimcardOrderControlModel> getOrderControlByInitialImsi(String initialImsi);
	
	List<SimcardOrderControlModel> getOrderControlByInitialIccd(String initialIccd);
		
	SimcardOrderControlModel getOrderByIdPadre(Long idSimcardPadre);
	
	GeneralResponse processSimcardFile(Long id, byte[] fileContent);
	
	void add(SimcardOrderControlModel model);
	
	void updateStatus(Long id);
		
	OrderFilesModel getOrderFilesById(Long id);

}
