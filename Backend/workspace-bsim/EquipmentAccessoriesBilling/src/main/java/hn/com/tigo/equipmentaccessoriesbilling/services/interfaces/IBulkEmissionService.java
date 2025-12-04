package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import hn.com.tigo.equipmentaccessoriesbilling.models.BillingModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkEmissionBatchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBulkEmissionService {

    Page<BillingModel> getBulkEmission(Pageable pageable, String seller);

    Page<BillingModel> searchByCustomerOrCustomerId(Pageable pageable, String seller, String customer, String customerId);
    
    BulkEmissionBatchResult emitBulk(List<Long> idsPreInvoices,
                                     String userCreate,
                                     String description,
                                     Long idBranchOffices,
                                     String paymentCode);
}
