package hn.com.tigo.bsimmodule.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseSerialDetailModel {

    private Long id;

    private Long releaseSerialLogId;

    private String inventoryType;

    private String itemCode;

    private String warehouseCode;

    private String subWarehouseCode;

    private String serialNumber;

    private String status;

    private String resultMessage;

    private LocalDateTime createdAt;
}
