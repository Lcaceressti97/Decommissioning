package hn.com.tigo.bsimmodule.models;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class GeneralResponse {

    private Long code;
    private String description;
    private Object data;
    private List<GeneralError> errors = new ArrayList<>();

}
