package hn.com.tigo.simcardinquiry.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.simcardinquiry.entities.BillingEntity;

@Repository
public interface IBillingRepository extends JpaRepository<BillingEntity, Long> {

	@Query(value = "SELECT * FROM MEA_BILLING WHERE TRUNC(CREATED) = TRUNC(SYSDATE) ORDER BY CREATED DESC", nativeQuery = true)
	List<BillingEntity> getInvoicesOfTheDay();

	List<BillingEntity> findByIdReference(Long idReference);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE (TRUNC(CREATED) = TRUNC(SYSDATE) AND (:type = 1 AND INVOICE_TYPE = :value) OR (:type = 2 AND ID = :value) OR (:type = 3 AND ACCTCODE = :value) OR (:type = 4 AND PRIMARY_IDENTITY = :value))", nativeQuery = true)
	List<BillingEntity> findByFilter(int type, String value);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE CREATED >= :startDate AND CREATED < :endDate ORDER BY CREATED DESC", nativeQuery = true)
	List<BillingEntity> getBillsByDateRange(LocalDate startDate, LocalDate endDate);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE ID = :id AND STATUS IN (0, 1) ORDER BY CREATED DESC", nativeQuery = true)
	BillingEntity getBillsByIdAuthorizeIssue(Long id);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE ID = :id AND STATUS IN (0,1,2,4) ORDER BY CREATED DESC", nativeQuery = true)
	BillingEntity getBillsByIdInvoicesCancel(Long id);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS IN (0, 1) ORDER BY CREATED DESC", nativeQuery = true)
	Page<BillingEntity> getInvoicesAuthorizeIssue(Pageable pageable);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS IN (0,1,2,4) ORDER BY CREATED DESC", nativeQuery = true)
	Page<BillingEntity> getInvoicesCancel(Pageable pageable);

	@Query(value = "SELECT * FROM MEA_BILLING WHERE (:startDate IS NULL OR CREATED BETWEEN :startDate AND :endDate) "
			+ "AND (:idBranchOffices IS NULL OR ID_BRANCH_OFFICES = :idBranchOffices) "
			+ "AND (:invoiceType IS NULL OR INVOICE_TYPE = :invoiceType) "
			+ "AND (:status IS NULL OR STATUS = :status)", nativeQuery = true)
	List<BillingEntity> getInvoiceDetailsGraphByFilters(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate, @Param("idBranchOffices") Long idBranchOffices,
			@Param("invoiceType") String invoiceType, @Param("status") Long status);

	@Query(value = "SELECT INVOICE_TYPE, STATUS, COUNT(*) FROM MEA_BILLING "
			+ "WHERE (:startDate IS NULL OR CREATED BETWEEN :startDate AND :endDate) "
			+ "AND (:idBranchOffices IS NULL OR ID_BRANCH_OFFICES = :idBranchOffices) "
			+ "AND (:invoiceType IS NULL OR INVOICE_TYPE = :invoiceType) "
			+ "AND (:status IS NULL OR STATUS = :status) GROUP BY INVOICE_TYPE, STATUS", nativeQuery = true)
	List<Object[]> getInvoiceDetailsByTypeAndStatusAndFilters(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate, @Param("idBranchOffices") Long idBranchOffices,
			@Param("invoiceType") String invoiceType, @Param("status") Long status);

	@Query(value = "SELECT bo.NAME AS BRANCH_OFFICE_NAME, bo.ID AS BRANCH_OFFICE_ID, b.STATUS, COUNT(*) "
			+ "FROM MEA_BILLING b LEFT JOIN MEA_BRANCH_OFFICES bo ON b.ID_BRANCH_OFFICES = bo.ID "
			+ "WHERE (:startDate IS NULL OR b.CREATED BETWEEN :startDate AND :endDate) "
			+ "AND (:idBranchOffices IS NULL OR b.ID_BRANCH_OFFICES = :idBranchOffices) "
			+ "AND (:invoiceType IS NULL OR b.INVOICE_TYPE = :invoiceType) "
			+ "AND (:status IS NULL OR b.STATUS = :status) GROUP BY bo.NAME, bo.ID, b.STATUS", nativeQuery = true)
	List<Object[]> getInvoiceDetailsByBranchOfficeAndStatusAndFilters(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate, @Param("idBranchOffices") Long idBranchOffices,
			@Param("invoiceType") String invoiceType, @Param("status") Long status);

	List<BillingEntity> findAll(Specification<BillingEntity> spec);
}
