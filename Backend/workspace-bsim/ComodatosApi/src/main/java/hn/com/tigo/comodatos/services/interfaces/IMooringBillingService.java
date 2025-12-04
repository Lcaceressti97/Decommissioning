package hn.com.tigo.comodatos.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hn.com.tigo.comodatos.models.MooringBillingModel;

public interface IMooringBillingService {

	Page<MooringBillingModel> getAllCmdByPagination(Pageable pageable);
	
	List<MooringBillingModel> findByFilter(int type, String value);
	
	List<MooringBillingModel> findByConsult(int type, Long value);
	
	MooringBillingModel findById(Long id);
	
	Long add(MooringBillingModel model);

	void update(Long id, MooringBillingModel model);
	
	void delete(Long id);
	
}
