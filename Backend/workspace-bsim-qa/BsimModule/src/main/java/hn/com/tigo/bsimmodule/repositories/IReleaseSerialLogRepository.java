package hn.com.tigo.bsimmodule.repositories;


import hn.com.tigo.bsimmodule.entities.ReleaseSerialLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReleaseSerialLogRepository extends JpaRepository<ReleaseSerialLogEntity, Long> {

    @Query(value = "SELECT * FROM BSIM_RELEASE_SERIAL_LOG ORDER BY ID DESC", nativeQuery = true)
    Page<ReleaseSerialLogEntity> getReleaseSerialLogPaginated(Pageable pageable);

    @Query(value = "SELECT rsl.* FROM BSIM_RELEASE_SERIAL_LOG rsl JOIN BSIM_RELEASE_SERIAL_DETAIL rsd ON rsl.ID = rsd.RELEASE_SERIA_LOG_ID WHERE rsd.SERIAL_NUMBER = :serialNumber", nativeQuery = true)
    List<ReleaseSerialLogEntity> findBySerialNumber(String serialNumber);

}
