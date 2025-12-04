package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkEmissionItemResult {

    private Long idPrefecture;
    private boolean success;
    private String message;
    private String service;
    private String errorCode;
    private String invoiceNo;
    private String payUpfrontSerialNo;
}