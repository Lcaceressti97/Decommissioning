package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.models.ControlAuthEmissionModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherResponseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IControlAuthEmissionService {

	List<ControlAuthEmissionModel> getAll();

	ControlAuthEmissionModel getById(Long id);

	List<ControlAuthEmissionModel> getByUserCreate(String userCreate);

	Page<ControlAuthEmissionModel> getByTypeApproval(Pageable pageable, Long typeApproval);

	VoucherResponseType add(ControlAuthEmissionModel model) throws Exception;

	void update(Long id, ControlAuthEmissionModel model);

	void delete(Long id);

}
