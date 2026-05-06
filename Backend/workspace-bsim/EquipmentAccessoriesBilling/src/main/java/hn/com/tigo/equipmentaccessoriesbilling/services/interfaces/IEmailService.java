package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;


import hn.com.tigo.equipmentaccessoriesbilling.models.EmailServiceModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.emailservice.GeneralResponse;

public interface IEmailService {

	GeneralResponse sendEmail(EmailServiceModel model);
	boolean sendBulkEmail(String email, String fileName, String zipBase64);
	
}
