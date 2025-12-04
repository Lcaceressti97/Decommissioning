package hn.com.tigo.bsimmodule.services;

import hn.com.tigo.bsimmodule.entities.ReleaseSerialLogEntity;
import hn.com.tigo.bsimmodule.models.ReleaseSerialLogModel;
import hn.com.tigo.bsimmodule.repositories.IReleaseSerialLogRepository;
import hn.com.tigo.bsimmodule.services.interfaces.IReleaseSerialLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReleaseSerialLogServiceImpl implements IReleaseSerialLogService {

    private final IReleaseSerialLogRepository releaseSerialLogRepository;

    public ReleaseSerialLogServiceImpl(IReleaseSerialLogRepository releaseSerialLogRepository) {
        this.releaseSerialLogRepository = releaseSerialLogRepository;
    }

    @Override
    public Page<ReleaseSerialLogModel> getAllReleaseSerialLog(Pageable pageable) {
        Page<ReleaseSerialLogEntity> entityPage = this.releaseSerialLogRepository
                .getReleaseSerialLogPaginated(pageable);
        return entityPage.map(ReleaseSerialLogEntity::entityToModel);
    }

    @Override
    public List<ReleaseSerialLogModel> getLogBySerialNumber(String serialNumber) {
        List<ReleaseSerialLogEntity> entities = releaseSerialLogRepository.findBySerialNumber(serialNumber);
        return entities.stream().map(ReleaseSerialLogEntity::entityToModel).collect(Collectors.toList());
    }


}
