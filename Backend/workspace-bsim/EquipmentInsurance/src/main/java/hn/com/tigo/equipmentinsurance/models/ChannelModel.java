package hn.com.tigo.equipmentinsurance.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelModel {

	private Long id;

	@NotNull(message = "NAME cannot be null")
	@NotBlank(message = "NAME cannot be blank")
	private String name;

	@NotNull(message = "DESCRIPTION cannot be null")
	@NotBlank(message = "DESCRIPTION cannot be blank")
	private String description;

	private Long payUpFrontNumber;

	private Long nonFiscalNote;

	private Long reserveSerialNumber;

	private Long releaseSerialNumber;

	private Long inventoryDownload;

	private Long generateTrama;

	private Long status;

	private LocalDateTime created;

}
