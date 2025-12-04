package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hn.com.tigo.equipmentaccessoriesbilling.models.ControlUserPermissionsModel;

public interface IControlUserPermissionsService {

	Page<ControlUserPermissionsModel> getAll(Pageable pageable);

	ControlUserPermissionsModel getByUserName(String userName);

	List<ControlUserPermissionsModel> getIssuingUsers();

	ControlUserPermissionsModel getById(Long id);

	void add(ControlUserPermissionsModel model);

	void update(Long id, ControlUserPermissionsModel model);

	void delete(Long id);
}
