package hn.com.tigo.inquiriesamsysnavega.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.inquiriesamsysnavega.entities.NavegaPaymentsEntity;

@Repository
public interface NavegaPaymentsRepository extends JpaRepository<NavegaPaymentsEntity, Long> {

	@Query(value = "SELECT * FROM CN_NAVEGA_PAYMENTS WHERE PAYMENT_DATE >= :startDate AND PAYMENT_DATE < :endDate ORDER BY PAYMENT_DATE DESC", nativeQuery = true)
	List<NavegaPaymentsEntity> getByDateRange(LocalDate startDate, LocalDate endDate);

	List<NavegaPaymentsEntity> findByEbsAccountAndTransactionSts(String ebsAccount, String transactionSts);
}
