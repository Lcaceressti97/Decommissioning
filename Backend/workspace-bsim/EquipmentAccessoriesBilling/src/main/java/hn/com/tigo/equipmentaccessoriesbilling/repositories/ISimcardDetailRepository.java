package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import hn.com.tigo.equipmentaccessoriesbilling.entities.SimcardDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ISimcardDetailRepository extends JpaRepository<SimcardDetailEntity, Long> {

    @Query(value = "SELECT * FROM SIMCARD_DETAIL WHERE (:type = 1 AND IMSI = :value) OR (:type = 2 AND ICC = :value)", nativeQuery = true)
    SimcardDetailEntity findByImsiOrIcc(int type, String value);

    @Query(value = "SELECT * FROM SIMCARD_DETAIL " +
            "WHERE UPPER(MODEL) = UPPER(:model) " +
            "AND UPPER(ICC) = UPPER(:icc)", nativeQuery = true)
    SimcardDetailEntity findByModelAndIcc(@Param("model") String model, @Param("icc") String icc);

    @Transactional
    @Modifying
    @Query(value = "UPDATE SIMCARD_DETAIL " +
            "SET BILLING_STATUS = 'EMI' " +
            "WHERE UPPER(MODEL) = UPPER(:model) " +
            "AND UPPER(ICC) = UPPER(:icc) " +
            "AND (BILLING_STATUS IS NULL OR UPPER(BILLING_STATUS) <> 'EMI')",
            nativeQuery = true)
    int updateBillingStatusToEmi(@Param("model") String model, @Param("icc") String icc);
}