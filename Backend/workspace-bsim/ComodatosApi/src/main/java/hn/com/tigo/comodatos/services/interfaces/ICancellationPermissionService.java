package hn.com.tigo.comodatos.services.interfaces;

import hn.com.tigo.comodatos.models.CancellationPermissionModel;

public interface ICancellationPermissionService {

	CancellationPermissionModel findById(Long id);
	
	CancellationPermissionModel findByUserName(String userName);
	
	void add(CancellationPermissionModel model);
	
	void update(Long id, CancellationPermissionModel model);
	
}
