package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import hn.com.tigo.equipmentaccessoriesbilling.models.DesktopSalesTransactionsModel;

public interface IDesktopSalesTransactionsService {

	Page<DesktopSalesTransactionsModel> getAllByPagination(Pageable pageable);

	DesktopSalesTransactionsModel getById(Long id);

	List<DesktopSalesTransactionsModel> findByClosingDateAndSeller(Optional<LocalDate> closingDate, Long seller);

	void add(DesktopSalesTransactionsModel model);

	void update(Long id, DesktopSalesTransactionsModel model);

	ResponseEntity<Object> updateChargeAmount(Long id, Double chargeAmount, LocalDateTime closingDate, Long seller);

	void delete(Long id);

}
