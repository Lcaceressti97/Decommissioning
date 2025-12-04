package hn.com.tigo.simcardinquiry.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParametersModel {

	private List<ParameterModel> parameter;
}
