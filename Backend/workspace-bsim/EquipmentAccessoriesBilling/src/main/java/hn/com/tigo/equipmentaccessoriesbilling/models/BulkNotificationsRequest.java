package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.Data;

@Data
public class BulkNotificationsRequest {
    
    private String idInvoices;
    private String emails;
    private String cashierName;

}
