package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BulkAuthorizeItemResult {

    private Long invoiceId;

    private boolean success;

    private String message;
}