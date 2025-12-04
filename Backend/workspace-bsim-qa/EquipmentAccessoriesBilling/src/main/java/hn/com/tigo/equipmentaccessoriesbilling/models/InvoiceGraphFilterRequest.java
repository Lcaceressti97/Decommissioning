package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceGraphFilterRequest {

	private Optional<LocalDate> startDate = Optional.empty();
	private Optional<LocalDate> endDate = Optional.empty();
	private List<String> agencies;
	private List<String> territories;
	private List<String> invoiceType;
	private List<Long> status;

}
