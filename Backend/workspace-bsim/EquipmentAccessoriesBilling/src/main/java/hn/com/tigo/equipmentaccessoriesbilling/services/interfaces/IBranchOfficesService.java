package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hn.com.tigo.equipmentaccessoriesbilling.models.BranchOfficesModel;

public interface IBranchOfficesService {

	Page<BranchOfficesModel> getAllBranchOffices(Pageable pageable);

	List<BranchOfficesModel> getBranchOfficesReport(String seller);

	BranchOfficesModel getById(Long id);

	BranchOfficesModel findById(Long id);

	void add(BranchOfficesModel model);

	void update(Long id, BranchOfficesModel model);

	void delete(Long id);

}
