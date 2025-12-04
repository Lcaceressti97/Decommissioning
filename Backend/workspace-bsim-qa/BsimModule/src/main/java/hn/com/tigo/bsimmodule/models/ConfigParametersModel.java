package hn.com.tigo.bsimmodule.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigParametersModel {

    private Long id;

    private Long idApplication;

    private String parameterType;

    private String parameterName;

    private String parameterDescription;

    private String parameterValue;

    private Long stateCode;

    private LocalDateTime created;

}
