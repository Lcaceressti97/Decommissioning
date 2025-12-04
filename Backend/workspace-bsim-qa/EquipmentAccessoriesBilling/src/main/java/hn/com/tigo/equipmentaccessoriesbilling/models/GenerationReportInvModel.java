package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerationReportInvModel {

	private Long id;

	private String fileName;

	private String description;

	private String reportInv;

	private Long status;

	private LocalDateTime created;

}
