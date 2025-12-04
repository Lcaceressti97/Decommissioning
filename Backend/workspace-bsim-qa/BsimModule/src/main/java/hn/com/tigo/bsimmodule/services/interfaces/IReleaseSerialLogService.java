package hn.com.tigo.bsimmodule.services.interfaces;

import hn.com.tigo.bsimmodule.models.ReleaseSerialLogModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReleaseSerialLogService {

    Page<ReleaseSerialLogModel> getAllReleaseSerialLog(Pageable pageable);

    List<ReleaseSerialLogModel> getLogBySerialNumber(String serialNumber);

}
