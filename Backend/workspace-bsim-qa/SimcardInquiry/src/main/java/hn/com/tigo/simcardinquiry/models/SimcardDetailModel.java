package hn.com.tigo.simcardinquiry.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardDetailModel {

    private Long id;

    private Long idOrderControl;

    @NotBlank(message = "ICC is required")
    @Size(max = 100, message = "ICC must be at most 100 characters")
    private String icc;

    @Size(max = 100, message = "IMSI must be at most 100 characters")
    private String imsi;

    @Size(max = 100, message = "IMSIB must be at most 100 characters")
    private String imsib;

    @Size(max = 100, message = "KI must be at most 100 characters")
    private String ki;

    @Size(max = 100, message = "PIN1 must be at most 100 characters")
    private String pin1;

    @Size(max = 100, message = "PUK1 must be at most 100 characters")
    private String puk1;

    @Size(max = 100, message = "PIN2 must be at most 100 characters")
    private String pin2;

    @Size(max = 100, message = "PUK2 must be at most 100 characters")
    private String puk2;

    @Size(max = 100, message = "ADM1 must be at most 100 characters")
    private String adm1;

    @Size(max = 100, message = "ADM2 must be at most 100 characters")
    private String adm2;

    @Size(max = 100, message = "ADM3 must be at most 100 characters")
    private String adm3;

    @Size(max = 100, message = "ACC must be at most 100 characters")
    private String acc;

    private String status;

    private String orderEbs;

    private String customerEbs;

    private String billingStatus;

    private String model;

    private String warehouse;
}
