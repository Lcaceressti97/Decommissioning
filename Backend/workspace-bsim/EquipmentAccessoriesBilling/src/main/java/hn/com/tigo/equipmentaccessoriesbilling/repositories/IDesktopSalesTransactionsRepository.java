package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.DesktopSalesTransactionsEntity;

@Repository
public interface IDesktopSalesTransactionsRepository extends JpaRepository<DesktopSalesTransactionsEntity, Long> {

	Optional<DesktopSalesTransactionsEntity> findByClosingDateAndSeller(LocalDateTime closingDate, Long seller);

	@Query(value = "SELECT * FROM MEA_DESKTOP_SALES_TRANSACTIONS WHERE CLOSING_DATE >= :closingDate AND CLOSING_DATE < :nextDay AND SELLER = :seller", nativeQuery = true)
	List<DesktopSalesTransactionsEntity> getByClosingDateAndSeller(@Param("closingDate") LocalDate closingDate, @Param("nextDay") LocalDate nextDay, @Param("seller") Long seller);


}
