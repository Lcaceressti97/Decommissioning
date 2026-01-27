package hn.com.tigo.equipmentaccessoriesbilling.entities;

import hn.com.tigo.equipmentaccessoriesbilling.models.ControlUnloadStockModel;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "MEA_CONTROL_UNLOAD_STOCK")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlUnloadStockEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CONTROL_UNLOAD_STOCK")
    @SequenceGenerator(name = "SQ_CONTROL_UNLOAD_STOCK", sequenceName = "SQ_CONTROL_UNLOAD_STOCK", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "REFERENCE")
    private Long reference;

    @Column(name = "SERVICE", length = 200, nullable = false)
    private String service;

    @Column(name = "URL", length = 500)
    private String url;

    @Column(name = "INVENTORY_TYPE", length = 50)
    private String inventoryType;

    @Column(name = "ITEM_CODE", length = 50)
    private String itemCode;

    @Column(name = "WAREHOUSE_CODE", length = 50)
    private String warehouseCode;

    @Column(name = "SUB_WAREHOUSE_CODE", length = 50)
    private String subWarehouseCode;

    @Column(name = "RESERVE_KEY", length = 100)
    private String reserveKey;

    @Lob
    @Column(name = "REQUEST")
    private String request;

    @Lob
    @Column(name = "RESPONSE")
    private String response;

    @Column(name = "ERROR_CODE", length = 50)
    private String errorCode;

    @Column(name = "ERROR_MESSAGE", length = 2000)
    private String errorMessage;

    @Lob
    @Column(name = "STACKTRACE")
    private String stacktrace;

    @Column(name = "STATUS", length = 20, nullable = false)
    private String status;

    @Column(name = "USER_CREATE", length = 50)
    private String userCreate;

    @Column(name = "EXECUTION_TIME_MS")
    private Long executionTimeMs;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "UPDATED")
    private LocalDateTime updated;

    public ControlUnloadStockModel entityToModel() {
        ControlUnloadStockModel model = new ControlUnloadStockModel();
        model.setId(this.getId());
        model.setReference(this.getReference());
        model.setService(this.getService());
        model.setUrl(this.getUrl());
        model.setInventoryType(this.getInventoryType());
        model.setItemCode(this.getItemCode());
        model.setWarehouseCode(this.getWarehouseCode());
        model.setSubWarehouseCode(this.getSubWarehouseCode());
        model.setReserveKey(this.getReserveKey());
        model.setRequestJson(this.getRequest());
        model.setResponseJson(this.getResponse());
        model.setErrorCode(this.getErrorCode());
        model.setErrorMessage(this.getErrorMessage());
        model.setStacktrace(this.getStacktrace());
        model.setStatus(this.getStatus());
        model.setUserCreate(this.getUserCreate());
        model.setExecutionTimeMs(this.getExecutionTimeMs());
        model.setCreated(this.getCreated());
        model.setUpdated(this.getUpdated());

        return model;
    }
}
