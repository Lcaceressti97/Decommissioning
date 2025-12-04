package hn.com.tigo.bsimmodule.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hn.com.tigo.bsimmodule.models.ReleaseSerialLogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "BSIM_RELEASE_SERIAL_LOG")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseSerialLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_RELEASE_SERIAL_LOG")
    @SequenceGenerator(name = "SQ_RELEASE_SERIAL_LOG", sequenceName = "SQ_RELEASE_SERIAL_LOG", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "RELEASE_TYPE")
    private String releaseType;

    @Column(name = "TOTAL_SERIES")
    private Integer totalSeries;

    @Column(name = "SUCCESSFUL_RELEASES")
    private Integer successfulReleases = 0;

    @Column(name = "FAILED_RELEASES")
    private Integer failedReleases = 0;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "releaseSerialLog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<ReleaseSerialDetailEntity> details = new ArrayList<>();

    public ReleaseSerialLogModel entityToModel() {
        ReleaseSerialLogModel model = new ReleaseSerialLogModel();
        model.setId(id);
        model.setReleaseType(releaseType);
        model.setTotalSeries(totalSeries);
        model.setSuccessfulReleases(successfulReleases);
        model.setFailedReleases(failedReleases);
        model.setFileName(fileName);
        model.setStatus(status);
        model.setCreatedAt(createdAt);
        model.setDetails(details);
        return model;
    }
}
