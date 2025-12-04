package hn.com.tigo.selfconsumption.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import hn.com.tigo.selfconsumption.models.AutoConsumoTempModel;


public interface IAutoConsumoTempService {
	
	List<AutoConsumoTempModel> getAllAutoConsumoTemp();

	List<AutoConsumoTempModel> findAutoConsumoTempByFilter(int type, String value);

	List<AutoConsumoTempModel> getAutoConsumoTempByDateRange(Optional<LocalDate> startDate, Optional<LocalDate> endDate);
	
	AutoConsumoTempModel getAutoConsumoTempById(String id);

}
