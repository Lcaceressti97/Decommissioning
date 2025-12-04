package hn.com.tigo.equipmentinsurance.services.interfaces;

import hn.com.tigo.equipmentinsurance.entities.InsuranceClaimEntity;
import hn.com.tigo.equipmentinsurance.services.executeorder.OrderResponse;

public interface IProvisioningInsuranceService {

    OrderResponse executeTask(InsuranceClaimEntity claim, boolean isCancellation);

}
