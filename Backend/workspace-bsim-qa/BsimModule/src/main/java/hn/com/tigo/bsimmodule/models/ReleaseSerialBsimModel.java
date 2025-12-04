package hn.com.tigo.bsimmodule.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseSerialBsimModel {

    private String result_message;
    private String result_code;
    private int count;
    private List<ReservationResult> reservation_result_list;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReservationResult {
        private String serial_number;
        private String reservation_result;
    }
}
