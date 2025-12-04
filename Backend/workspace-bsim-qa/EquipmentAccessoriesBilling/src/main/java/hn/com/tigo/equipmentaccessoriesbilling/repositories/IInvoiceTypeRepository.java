package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.InvoiceTypeEntity;

@Repository
public interface IInvoiceTypeRepository extends JpaRepository<InvoiceTypeEntity, Long> {

}
