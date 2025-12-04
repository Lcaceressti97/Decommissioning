package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class BulkAuthorizeResponse {
    private List<BulkAuthorizeItemResult> results = new ArrayList<>();

    private int totalRequested;

    private int totalAuthorized;

    private int totalFailed;
}
