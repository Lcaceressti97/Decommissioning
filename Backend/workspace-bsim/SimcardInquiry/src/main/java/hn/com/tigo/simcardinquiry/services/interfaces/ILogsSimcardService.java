package hn.com.tigo.simcardinquiry.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hn.com.tigo.simcardinquiry.models.LogsSimcardModel;

public interface ILogsSimcardService {

	Page<LogsSimcardModel> getAllLogsByPagination(Pageable pageable);

	List<LogsSimcardModel> getLogsByDateRange(Optional<LocalDate> startDate, Optional<LocalDate> endDate);

	List<LogsSimcardModel> getLogsByTypeError(long typeError);

	void saveLog(long typeError, long reference, String message);
}
