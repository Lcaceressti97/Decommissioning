package hn.com.tigo.bsimmodule.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hn.com.tigo.bsimmodule.models.ReleaseSerialDetailModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "BSIM_RELEASE_SERIAL_DETAIL")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseSerialDetailEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_RELEASE_SERIAL_DETAIL")
    @SequenceGenerator(name = "SQ_RELEASE_SERIAL_DETAIL", sequenceName = "SQ_RELEASE_SERIAL_DETAIL", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RELEASE_SERIA_LOG_ID", nullable = false)
    @JsonIgnore
    private ReleaseSerialLogEntity releaseSerialLog;

    @Column(name = "INVENTORY_TYPE")
    private String inventoryType;

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "WAREHOUSE_CODE")
    private String warehouseCode;

    @Column(name = "SUB_WAREHOUSE_CODE")
    private String subWarehouseCode;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "RESULT_MESSAGE")
    private String resultMessage;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    public ReleaseSerialDetailModel entityToModel() {
        ReleaseSerialDetailModel model = new ReleaseSerialDetailModel();
        model.setId(this.id);
        model.setInventoryType(this.inventoryType);
        model.setItemCode(this.itemCode);
        model.setWarehouseCode(this.warehouseCode);
        model.setSubWarehouseCode(this.subWarehouseCode);
        model.setSerialNumber(this.serialNumber);
        model.setStatus(this.status);
        model.setResultMessage(this.resultMessage);
        model.setCreatedAt(this.createdAt);
        return model;
    }
}