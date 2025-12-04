package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.models.InsuranceClaimModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IInsuranceClaimService {

	Page<InsuranceClaimModel> getAll(Pageable pageable);

	List<InsuranceClaimModel> getByPhone(String phone);

	InsuranceClaimModel getById(Long id);
	
	List<InsuranceClaimModel> getByActualEsn(String actualEsn);

	void add(InsuranceClaimModel model);

	void update(Long id, InsuranceClaimModel model);

	void delete(Long id);
}
