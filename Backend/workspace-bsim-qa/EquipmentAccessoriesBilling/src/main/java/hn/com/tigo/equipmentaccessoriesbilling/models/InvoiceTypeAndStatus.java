package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceTypeAndStatus {
    private String invoiceType;
    private String status;
    private Long count;
}
