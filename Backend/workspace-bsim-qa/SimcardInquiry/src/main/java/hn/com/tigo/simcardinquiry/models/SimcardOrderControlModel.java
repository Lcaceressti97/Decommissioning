package hn.com.tigo.simcardinquiry.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardOrderControlModel {

	private Long id;

	@NotNull(message = "ID Supplier is required")
	private Long idSupplier;

	@NotBlank(message = "Supplier name is required")
	private String supplierName;

	private Long noOrder;

	@NotBlank(message = "User name is required")
	private String userName;

	@NotBlank(message = "Customer name is required")
	private String customerName;

	private String initialImsi;

	private String initialIccd;

	@NotNull(message = "Order Quantity is required")
	private Long orderQuantity;

	private String fileName;

	private String orderFile;
	
	private String orderDetailFile;

	private LocalDateTime createdDate;

	private String customer;

	@NotNull(message = "HLR is required")
	private String hlr;

	private String batch;

	@NotNull(message = "Key is required")
	private String key;

	@NotNull(message = "Type is required")
	private String type;

	@NotNull(message = "Art is required")
	private String art;

	@NotNull(message = "Graphic is required")
	private String graphic;

	@NotNull(message = "Model is required")
	private String model;

	@NotNull(message = "Version Size is required")
	private String versionSize;

	private String status;
	
	private String email;

}
