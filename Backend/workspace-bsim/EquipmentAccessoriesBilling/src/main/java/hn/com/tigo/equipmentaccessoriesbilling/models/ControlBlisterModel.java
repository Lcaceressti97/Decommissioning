package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlBlisterModel {

    private Long id;

    @NotNull(message = "MODEL cannot be null")
    @NotBlank(message = "MODEL cannot be blank")
    @Pattern(regexp = "^\\w+$", message = "Model must not contain spaces")
    private String model;

    private LocalDateTime created;

}
