package hn.com.tigo.jteller.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.jteller.entities.InvoiceDetailEntity;

@Repository
public interface IInvoiceDetailRepository extends JpaRepository<InvoiceDetailEntity,Long> {

	List<InvoiceDetailEntity> getInvoiceDetailByIdPrefecture(Long idPrefecture);
	
}
