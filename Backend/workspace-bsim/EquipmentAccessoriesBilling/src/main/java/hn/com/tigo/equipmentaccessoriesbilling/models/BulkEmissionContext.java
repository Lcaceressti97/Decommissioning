package hn.com.tigo.equipmentaccessoriesbilling.models;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlUserPermissionsEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.TypeUserEntity;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class BulkEmissionContext {
    String userUpper;
    String description;
    Long idBranchOffices;
    String paymentCode;

    ControlUserPermissionsEntity userPerm;
    TypeUserEntity typeUser;

    Object params1002;
    Map<String, String> params1001;
}