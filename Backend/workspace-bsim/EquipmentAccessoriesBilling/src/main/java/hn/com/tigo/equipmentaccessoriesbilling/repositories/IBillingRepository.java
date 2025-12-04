package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;

@Repository
public interface IBillingRepository extends JpaRepository<BillingEntity, Long> {
	
    @Query(value = "SELECT ID, INVOICE_TYPE, STATUS, EXONERATION_STATUS, CUSTOMER, SELLER, PRIMARY_IDENTITY, CREATED "
			+ "FROM MEA_BILLING WHERE STATUS = 0 AND LOWER(CUSTOMER) LIKE :name  ORDER BY CREATED DESC", nativeQuery = true)
	List<Object[]> getAllPendingByName(@Param("name") String name);
	
    @Query(value = "SELECT ID, INVOICE_TYPE, STATUS, EXONERATION_STATUS, CUSTOMER, SELLER, PRIMARY_IDENTITY, CREATED "
			+ "FROM MEA_BILLING WHERE STATUS = 0 AND LOWER(CUSTOMER_ID) LIKE :rtn ORDER BY CREATED DESC", nativeQuery = true)
	List<Object[]> getAllPendingByRtn(@Param("rtn") String rtn);
	
    @Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND TRUNC(CREATED) = TRUNC(SYSDATE) AND CHANNEL NOT IN (2, 4) ORDER BY CREATED DESC", nativeQuery = true)
    Page<BillingEntity> getInvoicesOfTheDay(Pageable pageable, Long status);

    List<BillingEntity> findByIdReference(Long idReference);

    @Query(value = "SELECT * FROM MEA_BILLING WHERE " +
            "((:type = 1 AND INVOICE_TYPE = :value AND CHANNEL NOT IN (2, 4)) " +
            "OR (:type = 2 AND ID = :value AND CHANNEL NOT IN (2, 4)) " +
            "OR (:type = 3 AND ACCTCODE = :value AND CHANNEL NOT IN (2, 4)) " +
            "OR (:type = 4 AND PRIMARY_IDENTITY = :value AND CHANNEL NOT IN (2, 4)))",
            nativeQuery = true)
    Page<BillingEntity> findByFilter(Pageable pageable, int type, String value);

    @Query(value = "SELECT * FROM MEA_BILLING WHERE CREATED >= :startDate AND CREATED <= :endDate AND CHANNEL NOT IN (2, 4) ORDER BY CREATED DESC", nativeQuery = true)
    Page<BillingEntity> getBillsByDateRange(Pageable pageable, LocalDate startDate, LocalDate endDate);

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

    @Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') AND CHANNEL NOT IN (2, 4) ORDER BY CREATED DESC", nativeQuery = true)
    Page<BillingEntity> getCreditBill(Pageable pageable, Long status);

    @Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND SELLER = :seller AND ID_BRANCH_OFFICES IN (:idBranchOffices) AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') AND CHANNEL NOT IN (2, 4) ORDER BY CREATED DESC", nativeQuery = true)
    Page<BillingEntity> getCreditInvoicesBySellerBranchOffice(Pageable pageable, Long status, String seller, List<Long> idBranchOffices);

    @Query(value = "SELECT * FROM MEA_BILLING WHERE STATUS = :status AND ID_BRANCH_OFFICES IN (:idBranchOffices) AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') AND CHANNEL NOT IN (2, 4) ORDER BY CREATED DESC", nativeQuery = true)
    Page<BillingEntity> getCreditInvoicesByBranchOffice(Pageable pageable, Long status, List<Long> idBranchOffices);

    @Query(value =
            "SELECT * " +
                    "FROM MEA_BILLING " +
                    "WHERE (:status IS NULL OR STATUS = :status) " +
                    "  AND CHANNEL NOT IN (2, 4) " +
                    "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                    "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                    "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%') " +
                    "ORDER BY CREATED DESC",
            countQuery =
                    "SELECT COUNT(*) " +
                            "FROM MEA_BILLING " +
                            "WHERE (:status IS NULL OR STATUS = :status) " +
                            "  AND CHANNEL NOT IN (2, 4) " +
                            "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                            "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                            "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%')",
            nativeQuery = true)
    Page<BillingEntity> searchInvoicesByCustomerOrCustomerId(Pageable pageable,
                                                             @Param("status") Long status,
                                                             @Param("customer") String customer,
                                                             @Param("customerId") String customerId);


    @Query(value =
            "SELECT * " +
                    "FROM MEA_BILLING " +
                    "WHERE (:status IS NULL OR STATUS = :status) " +
                    "  AND CHANNEL NOT IN (2, 4) " +
                    "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                    "  AND SELLER = :seller " +
                    "  AND ID_BRANCH_OFFICES IN (:idBranchOffices) " +
                    "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                    "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%') " +
                    "ORDER BY CREATED DESC",
            countQuery =
                    "SELECT COUNT(*) " +
                            "FROM MEA_BILLING " +
                            "WHERE (:status IS NULL OR STATUS = :status) " +
                            "  AND CHANNEL NOT IN (2, 4) " +
                            "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                            "  AND SELLER = :seller " +
                            "  AND ID_BRANCH_OFFICES IN (:idBranchOffices) " +
                            "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                            "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%')",
            nativeQuery = true)
    Page<BillingEntity> searchInvoicesByCustomerOrCustomerIdBySellerBranchOffice(Pageable pageable,
                                                                                 @Param("status") Long status,
                                                                                 @Param("seller") String seller,
                                                                                 @Param("idBranchOffices") List<Long> idBranchOffices,
                                                                                 @Param("customer") String customer,
                                                                                 @Param("customerId") String customerId);


    @Query(value =
            "SELECT * " +
                    "FROM MEA_BILLING " +
                    "WHERE (:status IS NULL OR STATUS = :status) " +
                    "  AND CHANNEL NOT IN (2, 4) " +
                    "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                    "  AND ID_BRANCH_OFFICES IN (:idBranchOffices) " +
                    "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                    "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%') " +
                    "ORDER BY CREATED DESC",
            countQuery =
                    "SELECT COUNT(*) " +
                            "FROM MEA_BILLING " +
                            "WHERE (:status IS NULL OR STATUS = :status) " +
                            "  AND CHANNEL NOT IN (2, 4) " +
                            "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                            "  AND ID_BRANCH_OFFICES IN (:idBranchOffices) " +
                            "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                            "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%')",
            nativeQuery = true)
    Page<BillingEntity> searchInvoicesByCustomerOrCustomerIdByBranchOffice(Pageable pageable,
                                                                           @Param("status") Long status,
                                                                           @Param("idBranchOffices") List<Long> idBranchOffices,
                                                                           @Param("customer") String customer,
                                                                           @Param("customerId") String customerId);

    // ====== EMISIÓN  ======
    @Query(value = "SELECT * FROM MEA_BILLING " +
            "WHERE CHANNEL NOT IN (2, 4) " +
            "  AND STATUS = 1 " +
            "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
            "ORDER BY CREATED DESC",
            nativeQuery = true)
    Page<BillingEntity> getInvoicesForEmission(Pageable pageable);


    @Query(value = "SELECT * FROM MEA_BILLING " +
            "WHERE CHANNEL NOT IN (2, 4) " +
            "  AND SELLER = :seller " +
            "  AND ID_BRANCH_OFFICES IN (:idBranchOffices) " +
            "  AND STATUS = 1 " +
            "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
            "ORDER BY CREATED DESC",
            nativeQuery = true)
    Page<BillingEntity> getInvoicesForEmissionBySellerBranchOffice(Pageable pageable,
                                                                   @Param("seller") String seller,
                                                                   @Param("idBranchOffices") List<Long> idBranchOffices);


    @Query(value = "SELECT * FROM MEA_BILLING " +
            "WHERE CHANNEL NOT IN (2, 4) " +
            "  AND ID_BRANCH_OFFICES IN (:idBranchOffices) " +
            "  AND STATUS = 1 " +
            "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
            "ORDER BY CREATED DESC",
            nativeQuery = true)
    Page<BillingEntity> getInvoicesForEmissionByBranchOffice(Pageable pageable,
                                                             @Param("idBranchOffices") List<Long> idBranchOffices);


    // ====== BÚSQUEDA PARA EMISIÓN ======
    @Query(value =
            "SELECT * " +
                    "FROM MEA_BILLING " +
                    "WHERE CHANNEL NOT IN (2, 4) " +
                    "  AND STATUS = 1 " +
                    "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                    "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                    "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%') " +
                    "ORDER BY CREATED DESC",
            countQuery =
                    "SELECT COUNT(*) " +
                            "FROM MEA_BILLING " +
                            "WHERE CHANNEL NOT IN (2, 4) " +
                            "  AND STATUS = 1 " +
                            "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                            "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                            "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%')",
            nativeQuery = true)
    Page<BillingEntity> searchEmissionByCustomerOrCustomerId(Pageable pageable,
                                                             @Param("customer") String customer,
                                                             @Param("customerId") String customerId);


    @Query(value =
            "SELECT * " +
                    "FROM MEA_BILLING " +
                    "WHERE CHANNEL NOT IN (2, 4) " +
                    "  AND SELLER = :seller " +
                    "  AND ID_BRANCH_OFFICES IN (:idBranchOffices) " +
                    "  AND STATUS = 1 " +
                    "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                    "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                    "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%') " +
                    "ORDER BY CREATED DESC",
            countQuery =
                    "SELECT COUNT(*) " +
                            "FROM MEA_BILLING " +
                            "WHERE CHANNEL NOT IN (2, 4) " +
                            "  AND SELLER = :seller " +
                            "  AND ID_BRANCH_OFFICES IN (:idBranchOffices) " +
                            "  AND STATUS = 1 " +
                            "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                            "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                            "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%')",
            nativeQuery = true)
    Page<BillingEntity> searchEmissionByCustomerOrCustomerIdBySellerBranchOffice(
            Pageable pageable,
            @Param("seller") String seller,
            @Param("idBranchOffices") List<Long> idBranchOffices,
            @Param("customer") String customer,
            @Param("customerId") String customerId);


    @Query(value =
            "SELECT * " +
                    "FROM MEA_BILLING " +
                    "WHERE CHANNEL NOT IN (2, 4) " +
                    "  AND ID_BRANCH_OFFICES IN (:idBranchOffices) " +
                    "  AND STATUS = 1 " +
                    "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                    "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                    "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%') " +
                    "ORDER BY CREATED DESC",
            countQuery =
                    "SELECT COUNT(*) " +
                            "FROM MEA_BILLING " +
                            "WHERE CHANNEL NOT IN (2, 4) " +
                            "  AND ID_BRANCH_OFFICES IN (:idBranchOffices) " +
                            "  AND STATUS = 1 " +
                            "  AND INVOICE_TYPE IN ('FS2','FS4','FC2','FC4') " +
                            "  AND (:customer IS NULL OR TRIM(:customer) = '' OR UPPER(CUSTOMER) LIKE '%' || UPPER(:customer) || '%') " +
                            "  AND (:customerId IS NULL OR TRIM(:customerId) = '' OR UPPER(CUSTOMER_ID) LIKE '%' || UPPER(:customerId) || '%')",
            nativeQuery = true)
    Page<BillingEntity> searchEmissionByCustomerOrCustomerIdByBranchOffice(
            Pageable pageable,
            @Param("idBranchOffices") List<Long> idBranchOffices,
            @Param("customer") String customer,
            @Param("customerId") String customerId);

}
