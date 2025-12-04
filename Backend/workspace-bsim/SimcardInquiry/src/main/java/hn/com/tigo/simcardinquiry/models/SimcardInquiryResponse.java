package hn.com.tigo.simcardinquiry.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardInquiryResponse {

    private GeneralResponseSimcard generalResponse;
    private DetailsModel details;
}
