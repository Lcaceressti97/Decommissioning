package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.models.TypeUserModel;

public interface ITypeUserService {

	List<TypeUserModel> getAllTypeUser();

	TypeUserModel getTypeUserById(Long id);

	void addTypeUser(TypeUserModel model);

	void updateTypeUser(Long id, TypeUserModel model);

	void deleteTypeUser(Long id);
}
