package hn.com.tigo.equipmentinsurance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.InvoiceTypeEntity;

@Repository
public interface IInvoiceTypeRepository extends JpaRepository<InvoiceTypeEntity, Long> {

}
