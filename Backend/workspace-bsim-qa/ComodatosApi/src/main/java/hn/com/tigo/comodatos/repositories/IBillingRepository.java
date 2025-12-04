package hn.com.tigo.comodatos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.comodatos.entities.BillingEntity;

@Repository
public interface IBillingRepository extends JpaRepository<BillingEntity, Long> {

	BillingEntity getBillingByNumberDei(String numberDei);

}
