package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hn.com.tigo.equipmentaccessoriesbilling.models.LogsModel;

public interface ILogsService {

	Page<LogsModel> getAllLogsByPagination(Pageable pageable);

	Page<LogsModel> getLogsByDateRange(Pageable pageable, Optional<LocalDate> startDate, Optional<LocalDate> endDate);

	Page<LogsModel> getLogsByTypeError(Pageable pageable,long typeError);

	void saveLog(long typeError, long reference, String message, String user, long time);

}
