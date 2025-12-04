package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.Data;

import java.util.List;

@Data
public class BulkEmitRequest {

    private List<Long> idsPrefectures;
    private String userCreate;
    private String description;
    private Long idBranchOffices;
    private String paymentCode;
}