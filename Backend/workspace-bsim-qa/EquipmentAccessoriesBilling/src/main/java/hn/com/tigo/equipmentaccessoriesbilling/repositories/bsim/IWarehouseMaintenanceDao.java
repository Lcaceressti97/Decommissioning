package hn.com.tigo.equipmentaccessoriesbilling.repositories.bsim;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.entities.bsim.InvWarehouseBsimEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.bsim.InvWarehouseByLocationEntity;

public interface IWarehouseMaintenanceDao {

	List<InvWarehouseByLocationEntity> getAll();
	
	List<InvWarehouseBsimEntity> getInvWarehouseBsimByUser(String userName);

	List<InvWarehouseByLocationEntity> findByCode(String code);

	List<InvWarehouseByLocationEntity> getWarehouseRepot(String seller);

	String getWarehouseNameByCode(String code);

}
