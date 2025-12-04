package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.*;

@Data
@AllArgsConstructor
public class BranchOfficeAndStatus {
	private String branchOfficeName;
	private String status;
	private Long count;
}