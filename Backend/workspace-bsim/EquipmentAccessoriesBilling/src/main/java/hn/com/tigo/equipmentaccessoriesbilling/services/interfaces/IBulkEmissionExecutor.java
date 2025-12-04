package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import hn.com.tigo.equipmentaccessoriesbilling.models.BulkEmissionContext;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkEmissionItemResult;

public interface IBulkEmissionExecutor {

    BulkEmissionItemResult emitOneIsolated(Long idPrefecture, BulkEmissionContext ctx);

}