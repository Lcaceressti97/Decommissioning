package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.util.List;

import lombok.Data;

@Data
public class BulkAuthorizeRequest {

    private List<Long> invoiceIds;

    private String user;

    private String description;

}
