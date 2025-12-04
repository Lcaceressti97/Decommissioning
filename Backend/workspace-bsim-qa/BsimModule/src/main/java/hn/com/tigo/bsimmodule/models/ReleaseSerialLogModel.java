package hn.com.tigo.bsimmodule.models;

import hn.com.tigo.bsimmodule.entities.ReleaseSerialDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseSerialLogModel {

    private Long id;

    private String releaseType;

    private Integer totalSeries;

    private Integer successfulReleases;

    private Integer failedReleases;

    private String fileName;

    private String status;

    private LocalDateTime createdAt;

    private List<ReleaseSerialDetailEntity> details;
}
