package hn.com.tigo.simcardinquiry.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardEncryptDecryptResponse {

    private String imsib;

    private String ki;

    private String pin1;

    private String puk1;

    private String pin2;

    private String puk2;

    private String adm2;

    private String adm3;

    private String acc;
}
