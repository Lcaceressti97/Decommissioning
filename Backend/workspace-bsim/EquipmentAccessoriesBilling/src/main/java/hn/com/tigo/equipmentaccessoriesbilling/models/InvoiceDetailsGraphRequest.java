package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailsGraphRequest {

    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> idBranchOffices;
    private List<String> invoiceTypes;
    private List<Long> statuses;
}
