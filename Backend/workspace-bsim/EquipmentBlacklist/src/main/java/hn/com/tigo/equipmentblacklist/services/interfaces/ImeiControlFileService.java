package hn.com.tigo.equipmentblacklist.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentblacklist.models.ImeiControlFileModel;

public interface ImeiControlFileService {

	List<ImeiControlFileModel> getAll();

	ImeiControlFileModel getById(Long id);

	List<ImeiControlFileModel> getByPhone(String phone);

	List<ImeiControlFileModel> findByPhoneOrImeiWithType(int type, String value);

	ImeiControlFileModel findByPhoneAndStatus(String phone, Long status);

	void add(ImeiControlFileModel model);

	void update(Long id, ImeiControlFileModel model);

	void delete(Long id);

}
