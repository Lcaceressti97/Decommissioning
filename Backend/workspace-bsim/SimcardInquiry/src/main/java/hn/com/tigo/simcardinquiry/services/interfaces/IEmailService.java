package hn.com.tigo.simcardinquiry.services.interfaces;

import hn.com.tigo.simcardinquiry.models.EmailServiceModel;
import hn.com.tigo.simcardinquiry.services.emailservice.GeneralResponse;

public interface IEmailService {

	GeneralResponse sendEmail(EmailServiceModel model);

}
