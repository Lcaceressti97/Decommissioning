package hn.com.tigo.selfconsumption.services.interfaces;

import hn.com.tigo.selfconsumption.services.offeringinfo.AdapterException_Exception;
import hn.com.tigo.selfconsumption.services.offeringinfo.TaskResponseType;

public interface IOfferingInfoService {

	TaskResponseType executeTask(String offeringId) throws AdapterException_Exception;

}
