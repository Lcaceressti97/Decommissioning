package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;


import hn.com.tigo.equipmentaccessoriesbilling.entities.InsuranceClaimEntity;
import hn.com.tigo.equipmentaccessoriesbilling.services.executeorderservice.OrderResponse;

public interface IProvisioningInsuranceService {

    OrderResponse executeTask(InsuranceClaimEntity claim, boolean isCancellation);

}
