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
public class SimcardCustomerModel {

	private Long id;

    @NotNull(message = "Code is required")
    private Long code;

    @NotNull(message = "HRL is required")
    private String hlr;

    @NotBlank(message = "Client name is required")
    private String clientName;

	@NotBlank(message = "Description HRL is required")
    private String descriptionHrl;

    private Long status;

    private LocalDateTime createdDate;
}
