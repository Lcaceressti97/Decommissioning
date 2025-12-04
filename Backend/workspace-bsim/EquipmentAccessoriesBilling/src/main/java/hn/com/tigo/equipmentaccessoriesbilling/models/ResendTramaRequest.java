package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResendTramaRequest {

    private Long invoiceId;
    private String tramaType; // EMI, ANU, ASE
}
