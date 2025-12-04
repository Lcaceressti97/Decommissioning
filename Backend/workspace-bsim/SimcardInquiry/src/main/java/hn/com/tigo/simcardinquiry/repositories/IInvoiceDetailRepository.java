package hn.com.tigo.simcardinquiry.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.simcardinquiry.entities.InvoiceDetailEntity;

@Repository
public interface IInvoiceDetailRepository extends JpaRepository<InvoiceDetailEntity, Long> {

	@Query(value = "SELECT * FROM MEA_INVOICE_DETAIL WHERE ID_PREFECTURE = :idInvoice", nativeQuery = true)
	List<InvoiceDetailEntity> getDetailByIdInvoice(Long idInvoice);

	@Query(value = "SELECT * FROM (SELECT * FROM MEA_INVOICE_DETAIL WHERE SERIE_OR_BOX_NUMBER = :serie ORDER BY ID DESC) WHERE ROWNUM = 1", nativeQuery = true)
	InvoiceDetailEntity getDetailBySerie(String serie);

}
