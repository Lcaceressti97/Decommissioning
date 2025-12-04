package hn.com.tigo.equipmentinsurance.models.bsim;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvWarehouseByLocationModel {

	private Long id;

	private BigDecimal version;

	private String code;

	private String name;

	private String address;

	private String phone;

	private String zone;

	private String businessUnit;

	private String responsible;

	private String status;

	private String warehouseTypeId;

	private String immediateBoss;

}
