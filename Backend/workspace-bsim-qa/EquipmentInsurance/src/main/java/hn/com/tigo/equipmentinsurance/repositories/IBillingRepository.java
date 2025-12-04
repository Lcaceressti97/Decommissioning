package hn.com.tigo.equipmentinsurance.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.BillingEntity;

@Repository
public interface IBillingRepository extends JpaRepository<BillingEntity, Long> {

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND TRUNC(CREATED) = TRUNC(SYSDATE) ORDER BY CREATED DESC", nativeQuery = true)
	List<BillingEntity> getInvoicesOfTheDay(Long status);

	List<BillingEntity> findByIdReference(Long idReference);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE (TRUNC(CREATED) = TRUNC(SYSDATE) AND (:type = 1 AND INVOICE_TYPE = :value) OR (:type = 2 AND ID = :value) OR (:type = 3 AND ACCTCODE = :value) OR (:type = 4 AND PRIMARY_IDENTITY = :value))", nativeQuery = true)
	List<BillingEntity> findByFilter(int type, String value);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE CREATED >= :startDate AND CREATED <= :endDate ORDER BY CREATED DESC", nativeQuery = true)
	List<BillingEntity> getBillsByDateRange(LocalDate startDate, LocalDate endDate);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE ID = :id AND STATUS IN (0, 1) ORDER BY CREATED DESC", nativeQuery = true)
	BillingEntity getBillsByIdAuthorizeIssue(Long id);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE ID = :id AND STATUS IN (0,1,2,4) ORDER BY CREATED DESC", nativeQuery = true)
	BillingEntity getBillsByIdInvoicesCancel(Long id);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status ORDER BY CREATED DESC", nativeQuery = true)
	Page<BillingEntity> getInvoicesAuthorizeIssue(Pageable pageable, Long status);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND SELLER = :seller AND ID_BRANCH_OFFICES IN (:idBranchOffices) ORDER BY CREATED DESC", nativeQuery = true)
	Page<BillingEntity> getInvoicesAuthorizeIssueBySellerBranchOffice(Pageable pageable, Long status, String seller,
			List<Long> idBranchOffices);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND ID_BRANCH_OFFICES IN (:idBranchOffices) ORDER BY CREATED DESC", nativeQuery = true)
	Page<BillingEntity> getInvoicesAuthorizeIssueByBranchOffice(Pageable pageable, Long status,
			List<Long> idBranchOffices);

	@Query("SELECT b FROM BillingEntity b WHERE b.status = :status ORDER BY b.created DESC")
	Page<BillingEntity> getInvoicesCancel(Pageable pageable, Long status);

	@Query("SELECT b FROM BillingEntity b WHERE b.status = :status AND b.seller = :seller AND b.idBranchOffices IN (:idBranchOffices) ORDER BY b.created DESC")
	Page<BillingEntity> getInvoicesCancelBySellerBranchOffice(Pageable pageable, Long status, String seller,
			List<Long> idBranchOffices);

	@Query("SELECT b FROM BillingEntity b WHERE b.status = :status AND b.idBranchOffices IN (:idBranchOffices) ORDER BY b.created DESC")
	Page<BillingEntity> getInvoicesCancelByBranchOffice(Pageable pageable, Long status, List<Long> idBranchOffices);

	List<BillingEntity> findAll(Specification<BillingEntity> spec);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE ID_INSURANCE_CLAIM = :idInsuranceClaim AND STATUS IN (0, 1, 2)", nativeQuery = true)
	BillingEntity getBillingByInsuranceClaim(Long idInsuranceClaim);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND TRUNC(CREATED) = TRUNC(SYSDATE) ORDER BY CREATED DESC", nativeQuery = true)
	List<BillingEntity> getInvoicesOfTheDayAndStatus(Long status);

	// Método para obtener facturas al contado
	@Query("SELECT b FROM BillingEntity b WHERE b.status = :status AND b.invoiceType IN ('FS1', 'FS3', 'FC1', 'FC3', 'SHP')")
	Page<BillingEntity> getInvoicesAuthorizeIssueByCashInvoices(Pageable pageable, Long status);

}
