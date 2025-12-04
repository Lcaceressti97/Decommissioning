package hn.com.tigo.equipmentinsurance.repositories.bsim;

import java.util.List;

import hn.com.tigo.equipmentinsurance.entities.bsim.InvWarehouseByLocationEntity;

public interface IWarehouseMaintenanceDao {

	List<InvWarehouseByLocationEntity> getAll();


}
