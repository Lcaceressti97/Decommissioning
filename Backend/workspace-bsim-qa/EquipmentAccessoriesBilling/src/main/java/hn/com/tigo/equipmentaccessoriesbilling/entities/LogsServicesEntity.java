package hn.com.tigo.equipmentaccessoriesbilling.entities;

import hn.com.tigo.equipmentaccessoriesbilling.models.LogsServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "MEA_LOGS_SERVICES")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogsServicesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_MEA_LOGS_SERVICES")
    @SequenceGenerator(name = "SQ_MEA_LOGS_SERVICES", sequenceName = "SQ_MEA_LOGS_SERVICES", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "REQUEST")
    private String request;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "RESPONSE")
    private String response;

    @Column(name = "REFERENCE")
    private Long reference;

    @Column(name = "SERVICE", length = 200)
    private String service;

    @Column(name = "URL", length = 200)
    private String url;

    @Column(name = "USER_CREATE", length = 50)
    private String userCreate;

    @Column(name = "EXECUTION_TIME")
    private Long executionTime;

    @Column(name = "CREATED", nullable = false)
    private LocalDateTime created;


    public LogsServiceModel entityToModel() {
        LogsServiceModel model = new LogsServiceModel();
        model.setId(this.getId());
        model.setRequest(this.getRequest());
        model.setResponse(this.getResponse());
        model.setReference(this.getReference());
        model.setService(this.getService());
        model.setUrl(this.getUrl());
        model.setUserCreate(this.getUserCreate());
        model.setExecutionTime(this.getExecutionTime());
        model.setCreated(this.getCreated());
        return model;
    }
}
