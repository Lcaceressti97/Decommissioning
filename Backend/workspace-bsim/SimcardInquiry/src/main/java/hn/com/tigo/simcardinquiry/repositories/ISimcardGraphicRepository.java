package hn.com.tigo.simcardinquiry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.simcardinquiry.entities.SimcardGraphicEntity;

@Repository
public interface ISimcardGraphicRepository extends JpaRepository<SimcardGraphicEntity, Long> {

}
