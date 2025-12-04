package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkEmissionBatchResult {

    private int totalRequested;
    private int totalSuccess;
    private int totalFailed;
    private List<BulkEmissionItemResult> items;
}