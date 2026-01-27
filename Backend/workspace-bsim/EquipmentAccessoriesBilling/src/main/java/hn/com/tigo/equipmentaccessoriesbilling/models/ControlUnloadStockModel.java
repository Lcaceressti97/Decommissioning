package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlUnloadStockModel {

    private Long id;

    private Long reference;
    private String service;
    private String url;

    private String inventoryType;
    private String itemCode;
    private String warehouseCode;
    private String subWarehouseCode;
    private String reserveKey;

    private String requestJson;
    private String responseJson;

    private String errorCode;
    private String errorMessage;
    private String stacktrace;

    private String status;
    private String userCreate;
    private Long executionTimeMs;

    private LocalDateTime created;
    private LocalDateTime updated;

    private transient Throwable exception;
}
