package hn.com.tigo.simcardinquiry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.simcardinquiry.entities.SimcardTypeEntity;

@Repository
public interface ISimcardTypeRepository extends JpaRepository<SimcardTypeEntity, Long> {

}
