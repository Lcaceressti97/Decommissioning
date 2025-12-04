package hn.com.tigo.comodatos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.comodatos.entities.MooringBillingEntity;
import hn.com.tigo.comodatos.entities.MooringEntity;

@Repository
public interface MooringRepository extends JpaRepository<MooringEntity, Long>{

	List<MooringEntity> findByIdMooringBilling(Long idMooringBilling);
	
	List<MooringEntity> findBySubscriberId(String subscriber);
	
	@Query(value = "SELECT * FROM cmd_mooring WHERE ID_MOORING_BILLING = :idMoorringBilling AND MOORING_STATUS = :status", nativeQuery = true)
	List<MooringEntity> getMooringByIdMooringBillinfAndStatus(Long idMoorringBilling, int status);
	
	@Query(value = "SELECT * FROM CMD_MOORING WHERE SUBSCRIBER_ID = :SUBSCRIBER_ID AND MOORING_STATUS = 1", nativeQuery = true)
	List<MooringEntity> findByCmdMorringActive(@Param("SUBSCRIBER_ID") String subscriber);
	
	@Query(value = "SELECT * FROM (SELECT * FROM CMD_MOORING WHERE SUBSCRIBER_ID = :SUBSCRIBER_ID ORDER BY CREATED DESC) WHERE ROWNUM = 1", nativeQuery = true)
	List<MooringEntity> findByConsultSubscriberDesc(@Param("SUBSCRIBER_ID") String subscriber);
	
}
