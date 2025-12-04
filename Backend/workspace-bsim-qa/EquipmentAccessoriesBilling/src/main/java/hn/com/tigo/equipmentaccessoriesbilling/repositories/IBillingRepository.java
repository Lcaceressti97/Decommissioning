package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;

@Repository
public interface IBillingRepository extends JpaRepository<BillingEntity, Long> {

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND TRUNC(CREATED) = TRUNC(SYSDATE) AND CHANNEL NOT IN (2, 4) ORDER BY CREATED DESC", nativeQuery = true)
	Page<BillingEntity> getInvoicesOfTheDay(Pageable pageable,Long status);

	List<BillingEntity> findByIdReference(Long idReference);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE " +
			"((:type = 1 AND INVOICE_TYPE = :value AND CHANNEL NOT IN (2, 4)) " +
			"OR (:type = 2 AND ID = :value AND CHANNEL NOT IN (2, 4)) " +
			"OR (:type = 3 AND ACCTCODE = :value AND CHANNEL NOT IN (2, 4)) " +
			"OR (:type = 4 AND PRIMARY_IDENTITY = :value AND CHANNEL NOT IN (2, 4)))",
			nativeQuery = true)
	Page<BillingEntity> findByFilter(Pageable pageable,int type, String value);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE CREATED >= :startDate AND CREATED <= :endDate AND CHANNEL NOT IN (2, 4) ORDER BY CREATED DESC", nativeQuery = true)
	Page<BillingEntity> getBillsByDateRange(Pageable pageable,LocalDate startDate, LocalDate endDate);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE ID = :id AND STATUS IN (0, 1) ORDER BY CREATED DESC", nativeQuery = true)
	BillingEntity getBillsByIdAuthorizeIssue(Long id);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE ID = :id AND STATUS IN (0,1,2,4) ORDER BY CREATED DESC", nativeQuery = true)
	BillingEntity getBillsByIdInvoicesCancel(Long id);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND CHANNEL NOT IN (2, 4) ORDER BY CREATED DESC", nativeQuery = true)
	Page<BillingEntity> getInvoicesAuthorizeIssue(Pageable pageable, Long status);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND SELLER = :seller AND ID_BRANCH_OFFICES IN (:idBranchOffices) AND CHANNEL NOT IN (2, 4) ORDER BY CREATED DESC", nativeQuery = true)
	Page<BillingEntity> getInvoicesAuthorizeIssueBySellerBranchOffice(Pageable pageable, Long status, String seller,
			List<Long> idBranchOffices);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND ID_BRANCH_OFFICES IN (:idBranchOffices) AND CHANNEL NOT IN (2, 4) ORDER BY CREATED DESC", nativeQuery = true)
	Page<BillingEntity> getInvoicesAuthorizeIssueByBranchOffice(Pageable pageable, Long status, List<Long> idBranchOffices);

	@Query("SELECT b FROM BillingEntity b WHERE b.status = :status AND b.channel NOT IN (2, 4) ORDER BY b.created DESC")
	Page<BillingEntity> getInvoicesCancel(Pageable pageable, Long status);

	@Query("SELECT b FROM BillingEntity b WHERE b.status = :status AND b.seller = :seller AND b.idBranchOffices IN (:idBranchOffices) AND b.channel NOT IN (2, 4) ORDER BY b.created DESC")
	Page<BillingEntity> getInvoicesCancelBySellerBranchOffice(Pageable pageable, Long status, String seller,
															  List<Long> idBranchOffices);

	@Query("SELECT b FROM BillingEntity b WHERE b.status = :status AND b.idBranchOffices IN (:idBranchOffices) AND b.channel NOT IN (2, 4) ORDER BY b.created DESC")
	Page<BillingEntity> getInvoicesCancelByBranchOffice(Pageable pageable, Long status, List<Long> idBranchOffices);

	//@Query(value = "SELECT * FROM MEA_BILLING", nativeQuery = true)
	List<BillingEntity> findAll(Specification<BillingEntity> spec);

	Page<BillingEntity> findAll(Specification<BillingEntity> spec, Pageable pageable);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE ID_INSURANCE_CLAIM = :idInsuranceClaim AND STATUS IN (0, 1, 2)", nativeQuery = true)
	BillingEntity getBillingByInsuranceClaim(Long idInsuranceClaim);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND TRUNC(CREATED) = TRUNC(SYSDATE) ORDER BY CREATED DESC", nativeQuery = true)
	List<BillingEntity> getInvoicesOfTheDayAndStatus(Long status);
	
    @Query("SELECT b FROM BillingEntity b WHERE b.status = :status AND b.invoiceType IN ('FS1', 'FS3', 'FC1', 'FC3')")
    Page<BillingEntity> getInvoicesAuthorizeIssueByCashInvoices(Pageable pageable, Long status);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = 0 AND SELLER = :seller AND TRUNC(CREATED) = TRUNC(SYSDATE) ORDER BY CREATED DESC", nativeQuery = true)
	Page<BillingEntity> getInvoicesOfTheDayAndStatusAndSeller(Pageable pageable, String seller);

}
