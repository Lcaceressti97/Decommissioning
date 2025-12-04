package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.ChannelEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ControlAuthEmissionModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.executeorderservice.OrderResponse;

public interface INonFiscalNoteService {

	OrderResponse executeTask(BillingEntity billingEntity, ControlAuthEmissionModel model, ChannelEntity channelEntity);
}
