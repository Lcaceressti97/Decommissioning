package hn.com.tigo.selfconsumption.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCodeAutoconsumoModel {

	private Long id;

	private Long offeringId;

	private String chargeCode;

	private String itemName;

	private String userName;

	private Long status;

	private LocalDateTime createDate;

}
