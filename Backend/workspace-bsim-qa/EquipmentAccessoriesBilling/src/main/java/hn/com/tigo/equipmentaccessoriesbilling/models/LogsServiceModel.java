package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsServiceModel {

    private Long id;

    private String request;

    private String response;

    private Long reference;

    private String service;

    private String url;

    private String userCreate;

    private Long executionTime;

    private LocalDateTime created;

}
