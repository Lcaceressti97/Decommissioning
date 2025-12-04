package hn.com.tigo.bsimmodule.repositories;


import hn.com.tigo.bsimmodule.entities.ReleaseSerialDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReleaseSerialDetailRepository extends JpaRepository<ReleaseSerialDetailEntity, Long> {
}
