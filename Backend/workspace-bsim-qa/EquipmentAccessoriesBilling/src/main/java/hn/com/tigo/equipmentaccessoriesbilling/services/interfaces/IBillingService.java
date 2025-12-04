package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBillingService {

	Page<BillingModel> getAll(Pageable pageable,Long status);

	Page<BillingModel> getInvoicesAuthorizeIssue(Pageable pageable, Long status, String seller);

	Page<BillingModel> getInvoicesCancel(Pageable pageable, Long status, String seller);

	Page<BillingModel> findByFilter(Pageable pageable,int type, String value);

	Page<BillingModel> getBillsByDateRange(Pageable pageable, Optional<LocalDate> startDate, Optional<LocalDate> endDate);

	Page<BillingModel> filterInvoices(Pageable pageable,List<Long> status, List<String> warehouses, List<String> agencies,
			List<String> territories, Optional<LocalDate> startDate, Optional<LocalDate> endDate, String seller);

	BillingModel getBillsByIdAuthorizeIssue(Long id);

	BillingModel getBillsByIdInvoicesCancel(Long id);

	BillingModel getById(Long id);

	BillingModel findById(Long id);

	BillingModel findByIdReference(Long idReference);

	InvoiceDetailGraphicsResponse getInvoiceDetailsGraphByDateRangeAndFilters(Optional<LocalDate> startDate,
			Optional<LocalDate> endDate, List<String> agencies, List<String> territories, List<String> invoiceType,
			List<Long> status);

	List<InvoiceTypeAndStatus> getInvoiceDetailsByTypeAndStatusAndFilters(Optional<LocalDate> startDate,
			Optional<LocalDate> endDate, List<String> agencies, List<String> territories, List<String> invoiceType,
			List<Long> status);

	List<BranchOfficeAndStatus> getInvoiceDetailsByBranchOfficeAndStatus(Optional<LocalDate> startDate,
			Optional<LocalDate> endDate, List<String> agencies, List<String> territories, List<String> invoiceType,
			List<Long> status);

	Long add(BillingModel model);
	
	Long addInvoiceInsuranceClaim(BillingModel model);

	void update(Long id, BillingModel model);

	void updateDataForAddVoucher(Long id, BillingModel model);

	void updateStatusInvoice(Long id, Long status, String authorizingUser, LocalDateTime authorizationDate,
			String userIssued, LocalDateTime dateOfIssue);

	void updateDocumentNo(Long id, String documentNo);

	void updateCorporateClient(Long id, BillingModel model);

	void updateSingleClient(Long id, BillingModel model);

	void updateStatusExoTax(Long id);

	void delete(Long id);

	String getInvoiceDetailBase64ById(Long id);

	BillingModel getInvoiceBySerialNumber(String serieOrBoxNumber);
	
	BillingModel getBillingByInsuranceClaim(Long idInsuranceClaim);

	String resendTrama(ResendTramaRequest request);

	Page<BillingModel> getInvoicesOfTheDayAndStatusAndSeller(Pageable pageable,String seller);

}
