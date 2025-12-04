package hn.com.tigo.equipmentinsurance.repositories.bsim;

import java.util.List;

import hn.com.tigo.equipmentinsurance.entities.bsim.InvExistencesEntity;
import hn.com.tigo.equipmentinsurance.entities.bsim.InvExistencesViewEntity;

public interface IExistencesDao {

	List<InvExistencesEntity> getExistencesByFilter(Long warehouseId, Long typeInventoryId);

	List<InvExistencesViewEntity> getExistencesViewByFilter(Long warehouseId, String type, String equipmentLine);

}
