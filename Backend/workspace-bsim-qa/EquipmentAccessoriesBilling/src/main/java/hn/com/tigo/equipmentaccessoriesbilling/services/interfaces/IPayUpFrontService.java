package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.ChannelEntity;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.AdapterException_Exception;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.TaskResponseType;

public interface IPayUpFrontService {

	TaskResponseType executeTask(BillingEntity billingEntity, ChannelEntity channelEntity) throws AdapterException_Exception;
}
