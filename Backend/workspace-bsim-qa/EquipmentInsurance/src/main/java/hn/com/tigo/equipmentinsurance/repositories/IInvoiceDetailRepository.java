package hn.com.tigo.equipmentinsurance.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.InvoiceDetailEntity;

@Repository
public interface IInvoiceDetailRepository extends JpaRepository<InvoiceDetailEntity, Long> {

	@Query(value = "SELECT * FROM MEA_INVOICE_DETAIL WHERE ID_PREFECTURE = :idInvoice", nativeQuery = true)
	List<InvoiceDetailEntity> getDetailByIdInvoice(Long idInvoice);

	@Query(value = "SELECT * FROM (SELECT * FROM MEA_INVOICE_DETAIL WHERE SERIE_OR_BOX_NUMBER = :serie ORDER BY CREATED DESC) WHERE ROWNUM = 1", nativeQuery = true)
	InvoiceDetailEntity getDetailBySerie(String serie);

	Optional<InvoiceDetailEntity> findByBillingIdAndModel(Long billingId, String model);

}
