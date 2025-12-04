package hn.com.tigo.simcardinquiry.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponseSimcard {

	private String uti;
	private String status;
	private String code;
	private String message;	

}
