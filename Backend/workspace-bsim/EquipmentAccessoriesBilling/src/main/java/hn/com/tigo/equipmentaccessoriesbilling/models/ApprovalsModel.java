package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalsModel {

	private Long id;

	private Long idApproval;

	private String approvedUser;

	private Long status;

	private LocalDateTime created;

}
