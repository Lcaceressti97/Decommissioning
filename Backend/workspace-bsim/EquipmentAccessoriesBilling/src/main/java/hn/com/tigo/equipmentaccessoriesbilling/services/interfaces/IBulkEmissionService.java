package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import hn.com.tigo.equipmentaccessoriesbilling.models.BillingModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkEmissionBatchResult;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkNotificationsRequest;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkNotificationsResponse;
import hn.com.tigo.equipmentaccessoriesbilling.models.InvoicesByNameOrRtnModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface IBulkEmissionService {

    Page<BillingModel> getBulkEmission(Pageable pageable, String seller);

    Page<BillingModel> getEmitedInvoices(Pageable pageable, String seller);

    Page<BillingModel> searchByCustomerOrCustomerId(Pageable pageable, String seller, String customer,
            String customerId);

    Page<BillingModel> searchEmitedByCustomerOrCustomerIdPaged(Pageable pageable, String seller, String customer,
            String customerId);

    List<InvoicesByNameOrRtnModel> getAllEmitedByNameOrRtn(String name, String rtn);

    BulkNotificationsResponse sendBulkNotifications(BulkNotificationsRequest req) throws IOException;

    BulkEmissionBatchResult emitBulk(List<Long> idsPreInvoices,
            String userCreate,
            String description,
            Long idBranchOffices,
            String paymentCode);
}
