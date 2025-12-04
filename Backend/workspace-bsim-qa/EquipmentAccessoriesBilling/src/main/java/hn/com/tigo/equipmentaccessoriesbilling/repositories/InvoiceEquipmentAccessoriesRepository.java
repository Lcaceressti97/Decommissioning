package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.entities.InvoiceEquipmentAccessoriesEntity;

@Repository
public interface InvoiceEquipmentAccessoriesRepository extends JpaRepository<InvoiceEquipmentAccessoriesEntity, Long>,
		JpaSpecificationExecutor<InvoiceEquipmentAccessoriesEntity> {

	@Query(value = "SELECT * FROM MEA_HISTORICAL_INVOICE WHERE (:type = 1 AND SUBSCRIBER = :value) OR (:type = 2 AND CUSTCODE = :value) OR (:type = 3 AND BILLING_ACCOUNT = :value) OR (:type = 4 AND INVOICE_NO = :value)", nativeQuery = true)
	List<InvoiceEquipmentAccessoriesEntity> findBy(int type, String value);

	List<InvoiceEquipmentAccessoriesEntity> findAll(Specification<InvoiceEquipmentAccessoriesEntity> spec);

	@Query(value = "SELECT * FROM MEA_HISTORICAL_INVOICE WHERE INVOICE_STATUS > 0", nativeQuery = true)
	List<InvoiceEquipmentAccessoriesEntity> findByExonerationStatus();

    @Query(value = "SELECT i.* FROM MEA_HISTORICAL_INVOICE i JOIN MEA_APPROVALS a ON i.INVOICE_STATUS = a.ID_APPROVAL WHERE a.APPROVED_USER = :approvedUser", nativeQuery = true)
    List<InvoiceEquipmentAccessoriesEntity> findInvoicesByUserAndPermissions(String approvedUser);

}
