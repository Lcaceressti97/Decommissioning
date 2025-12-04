package hn.com.tigo.inquiriesamsysnavega.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import hn.com.tigo.inquiriesamsysnavega.models.NavegaPaymentsModel;

public interface NavegaPaymentsService {

	List<NavegaPaymentsModel> getAllPaymentsNavega();

	List<NavegaPaymentsModel> getPaymentsNavegaByDateRange(Optional<LocalDate> startDate, Optional<LocalDate> endDate);

	List<NavegaPaymentsModel> getPaymentsByEbsAccount(String ebsAccount);

	void add(NavegaPaymentsModel model);

	void cancellationPaymentsNavega(Long id);
}
