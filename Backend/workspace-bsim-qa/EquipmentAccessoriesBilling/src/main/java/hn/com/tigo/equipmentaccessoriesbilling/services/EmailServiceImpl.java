package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.BranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.EmailServiceModel;
import hn.com.tigo.equipmentaccessoriesbilling.provider.EmailServiceProvider;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IBillingRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IBranchOfficesRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.emailservice.AttachmentDto;
import hn.com.tigo.equipmentaccessoriesbilling.services.emailservice.AttachmentsDTO;
import hn.com.tigo.equipmentaccessoriesbilling.services.emailservice.GeneralResponse;
import hn.com.tigo.equipmentaccessoriesbilling.services.emailservice.SentDTO;
import hn.com.tigo.equipmentaccessoriesbilling.services.emailservice.ToDto;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IEmailService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;
import hn.com.tigo.equipmentaccessoriesbilling.utils.InvoicePdfGenerator;

@Service
public class EmailServiceImpl implements IEmailService {

	private final IConfigParametersService configParametersService;
	private final IBillingRepository billingRepository;
	private final IBranchOfficesRepository branchOfficesRepository;

	public EmailServiceImpl(IConfigParametersService configParametersService, IBillingRepository billingRepository,
			IBranchOfficesRepository branchOfficesRepository) {
		super();
		this.configParametersService = configParametersService;
		this.billingRepository = billingRepository;
		this.branchOfficesRepository = branchOfficesRepository;

	}

	@Override
	public GeneralResponse sendEmail(EmailServiceModel model) {

		try {

			BillingEntity billingEntity = this.billingRepository.findById(model.getIdPrefecture()).orElse(null);
			if (billingEntity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, model.getIdPrefecture()));

			BranchOfficesEntity branchOfficesEntity = this.branchOfficesRepository
					.findById(billingEntity.getIdBranchOffices()).orElse(null);
			if (branchOfficesEntity == null)
				throw new BadRequestException(
						String.format(Constants.ERROR_NOT_FINDING_AN_ID, model.getIdPrefecture()));

			List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(2601L);
			Map<String, String> parameters = new HashMap<>();
			for (ConfigParametersModel parameter : list) {
				parameters.put(parameter.getParameterName(), parameter.getParameterValue());
			}

			EmailServiceProvider serviceEmail = new EmailServiceProvider(parameters.get("EMAIL_SERVICE_PROVIDER"));
			String from = parameters.get("FROM");
			String subject = parameters.get("SUBJECT");
			String body = parameters.get("BODY");

			SentDTO sendTo = new SentDTO();
			for (String mail : model.getEmail().split(";")) {
				ToDto email = new ToDto();
				email.setTo(mail);
				sendTo.getSend().add(email);

			}

			byte[] pdfBytes = InvoicePdfGenerator.generateInvoicePdf(billingEntity, branchOfficesEntity, model.getCashierName());

			AttachmentsDTO attachmentsDTO = new AttachmentsDTO();
			AttachmentDto attachmentDto = new AttachmentDto();
			attachmentDto.setAttachContent(Base64.getEncoder().encodeToString(pdfBytes));
			attachmentDto.setAttachName("Detalle de Factura " + billingEntity.getId() + ".pdf");
			attachmentDto.setMimeType("application/pdf");
			attachmentsDTO.getAttachments().add(attachmentDto);

			return serviceEmail.sendMessage(from + billingEntity.getId(), sendTo, "", subject,
					body + billingEntity.getId(), attachmentsDTO);
		} catch (BadRequestException e) {
			throw e;

		} catch (Exception e) {
			return null;
		}
	}

}
