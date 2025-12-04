package hn.com.tigo.equipmentaccessoriesbilling.repositories.bsim;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.entities.bsim.InvAllExistencesViewEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.bsim.InvExistencesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.bsim.InvExistencesViewEntity;

public interface IExistencesDao {

	List<InvExistencesEntity> getExistencesByFilter(Long warehouseId, Long typeInventoryId);
	List<InvExistencesViewEntity> getExistencesViewByFilter(Long warehouseId, String type, String equipmentLine);
	List<InvAllExistencesViewEntity> getAllExistencesViewByFilter(Long warehouseId, String type, String equipmentLine);

}
