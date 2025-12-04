package hn.com.tigo.bsimmodule.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseSerialBsimRequest {

    private String releaseType;
    private String inventoryType;
    private String itemCode;
    private String warehouseCode;
    private String subWarehouseCode;
    private List<SerialNumberList> serialNumberList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SerialNumberList {
        private String serialNumber;

    }

}
