package hn.com.tigo.comodatos.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringInfoModel {

	private String subscriber;
	private Long correlativeCmd;
	private Long correlativeMooringCmd;
	private String cmdStatus;
	private LocalDateTime createDate;
	private int monthsOfPermanence;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime dueDate;
	private LocalDateTime estimatedDate;
	private Double cost;
	private Double discount;
	private int pendingMonths;
	private String mooring;
	private List<String> subscribers;
	
}
