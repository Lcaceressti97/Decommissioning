package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import hn.com.tigo.equipmentaccessoriesbilling.models.BillingModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkAuthorizeRequest;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkAuthorizeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBulkAuthorizeService {

    Page<BillingModel> getBulkAuthorize(Pageable pageable, String seller);

    Page<BillingModel> searchByCustomerOrCustomerId(Pageable pageable, String seller, String customer, String customerId);

    BulkAuthorizeResponse authorizeInvoices(BulkAuthorizeRequest request);

}
