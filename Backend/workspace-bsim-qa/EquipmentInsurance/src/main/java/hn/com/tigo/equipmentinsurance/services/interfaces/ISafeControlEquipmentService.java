package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.models.SafeControlEquipmentModel;

public interface ISafeControlEquipmentService {

	List<SafeControlEquipmentModel> getAll();

	SafeControlEquipmentModel getById(Long id);

	List<SafeControlEquipmentModel> getByPhone(String phone);

	void add(SafeControlEquipmentModel model);

	void update(Long id, SafeControlEquipmentModel model);

	void delete(Long id);

}
