package hn.com.tigo.simcardinquiry.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.simcardinquiry.entities.SimcardSuppliersEntity;

@Repository
public interface ISimcardSuppliersRepository extends JpaRepository<SimcardSuppliersEntity, Long> {

	@Query("SELECT id, supplierNo, supplierName, address, phone, postalMail, emailSupplier, email, subject, textEmail, initialIccd,key, status, createdDate FROM SimcardSuppliersEntity ORDER BY createdDate DESC")
	List<Object[]> findAllFieldsExceptBaseFile();
	
	@Query("SELECT id, supplierNo, supplierName, address, phone, postalMail, emailSupplier, email, subject, textEmail, initialIccd,key, status, createdDate FROM SimcardSuppliersEntity WHERE ID = :id")
	Object[] findByIdFieldsExceptBaseFile(Long id);
}
