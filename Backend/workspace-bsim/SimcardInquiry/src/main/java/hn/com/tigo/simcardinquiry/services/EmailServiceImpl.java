package hn.com.tigo.simcardinquiry.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.SimcardOrderControlEntity;
import hn.com.tigo.simcardinquiry.entities.SimcardSuppliersEntity;
import hn.com.tigo.simcardinquiry.models.ConfigParameterModel;
import hn.com.tigo.simcardinquiry.models.EmailServiceModel;
import hn.com.tigo.simcardinquiry.repositories.ISimcardOrderControlRepository;
import hn.com.tigo.simcardinquiry.repositories.ISimcardSuppliersRepository;
import hn.com.tigo.simcardinquiry.services.emailservice.AttachmentDto;
import hn.com.tigo.simcardinquiry.services.emailservice.AttachmentsDTO;
import hn.com.tigo.simcardinquiry.services.emailservice.GeneralResponse;
import hn.com.tigo.simcardinquiry.services.emailservice.SentDTO;
import hn.com.tigo.simcardinquiry.services.emailservice.ToDto;
import hn.com.tigo.simcardinquiry.services.interfaces.IConfigParameterService;
import hn.com.tigo.simcardinquiry.services.interfaces.IEmailService;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.utils.Constants;
import hn.com.tigo.simcardinquiry.utils.EmailServiceProvider;

@Service
public class EmailServiceImpl implements IEmailService {

	private final ISimcardOrderControlRepository simcardOrderControlRepository;
	private final ISimcardSuppliersRepository simcardSuppliersRepository;
	private final ILogsSimcardService logsService;
	private final IConfigParameterService configParametersService;

	public EmailServiceImpl(ISimcardOrderControlRepository simcardOrderControlRepository,
			ISimcardSuppliersRepository simcardSuppliersRepository, ILogsSimcardService logsService,
			IConfigParameterService configParametersService) {
		super();
		this.simcardOrderControlRepository = simcardOrderControlRepository;
		this.simcardSuppliersRepository = simcardSuppliersRepository;
		this.logsService = logsService;
		this.configParametersService = configParametersService;

	}

	@Override
	public GeneralResponse sendEmail(EmailServiceModel model) {
		try {
			SimcardOrderControlEntity orderControlEntity = this.simcardOrderControlRepository.findById(model.getId())
					.orElse(null);
			if (orderControlEntity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, model.getId()));

			SimcardSuppliersEntity suppliersEntity = this.simcardSuppliersRepository
					.findById(orderControlEntity.getIdSupplier()).orElse(null);
			if (suppliersEntity == null)
				throw new BadRequestException(
						String.format(Constants.ERROR_NOT_FOUND_RECORD_SUPPLIER, orderControlEntity.getIdSupplier()));

			List<ConfigParameterModel> list = this.configParametersService.getByIdApplication(1000L);
			Map<String, String> parameters = new HashMap<>();
			for (ConfigParameterModel parameter : list) {
				parameters.put(parameter.getParameterName(), parameter.getParameterValue());
			}

			System.out.println(suppliersEntity.getEmail());
			EmailServiceProvider serviceEmail = new EmailServiceProvider(parameters.get("EMAIL_SERVICE_PROVIDER"));
			SentDTO sendTo = new SentDTO();
			for (String mail : suppliersEntity.getEmail().split(";")) {
				ToDto email = new ToDto();
				email.setTo(mail);
				sendTo.getSend().add(email);
			}

			AttachmentsDTO attachmentsDTO = new AttachmentsDTO();
			AttachmentDto attachmentDto = new AttachmentDto();
			attachmentDto.setAttachContent(orderControlEntity.getOrderFile());
			attachmentDto.setAttachName(orderControlEntity.getFileName() + ".INP");
			attachmentDto.setMimeType("text/plain");
			attachmentsDTO.getAttachments().add(attachmentDto);

			GeneralResponse response = serviceEmail.sendMessage(
					"Notificacion de Pedido No: " + orderControlEntity.getNoOrder(), sendTo, " ",
					suppliersEntity.getSubject(), suppliersEntity.getTextEmail(), attachmentsDTO);

		    if (orderControlEntity.getStatus() == null || orderControlEntity.getStatus().isEmpty()) {
	            orderControlEntity.setStatus("E");
	            this.simcardOrderControlRepository.save(orderControlEntity);
	        }
		    
			return response;
		} catch (BadRequestException e) {
			logsService.saveLog(3, model.getId(), Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(3, model.getId(), Constants.EXCEPTION + e.getMessage());
			return null;
		}
	}

}
