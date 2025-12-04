package hn.com.tigo.equipmentinsurance.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceTypeModel {

	private Long id;

	private String type;

	private String description;

	private Long status;

	private LocalDateTime created;

}
