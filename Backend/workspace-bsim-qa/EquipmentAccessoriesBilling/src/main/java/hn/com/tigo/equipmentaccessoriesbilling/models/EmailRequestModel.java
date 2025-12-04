package hn.com.tigo.equipmentaccessoriesbilling.models;

import hn.com.tigo.equipmentaccessoriesbilling.services.emailservice.AttachmentsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailRequestModel {

	private String from;

	private String to;

	private String cc;

	private String subject;

	private String body;

	private AttachmentsDTO attachments;

}
