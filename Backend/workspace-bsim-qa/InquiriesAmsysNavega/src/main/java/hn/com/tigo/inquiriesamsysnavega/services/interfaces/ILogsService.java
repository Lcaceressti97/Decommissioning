package hn.com.tigo.inquiriesamsysnavega.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hn.com.tigo.inquiriesamsysnavega.models.LogsModel;

public interface ILogsService {

	Page<LogsModel> getAllLogsByPagination(Pageable pageable);

	List<LogsModel> getLogsByDateRange(Optional<LocalDate> startDate, Optional<LocalDate> endDate);

	List<LogsModel> getLogsByTypeError(long typeError);

	void saveLog(long typeError, long reference, String message);
}
