package hn.com.tigo.inquiriesamsysnavega.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.inquiriesamsysnavega.entities.NavegaBankEntity;

@Repository
public interface INavegaBankRepository extends JpaRepository<NavegaBankEntity, Long> {

	NavegaBankEntity findBankByIdentifyingLetter(String identifyingLetter);
}
