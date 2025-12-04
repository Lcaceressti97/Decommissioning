package hn.com.tigo.comodatos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.comodatos.entities.MooringBillingEntity;

@Repository
public interface IMooringBillingRepository extends JpaRepository<MooringBillingEntity, Long>{

	@Query(value = "SELECT * FROM CMD_MOORING_BILLING WHERE (:type = 1 AND SUBSCRIBER_ID = :value) OR (:type = 2 AND CUSTOMER_ACCOUNT = :value) OR (:type = 3 AND SERVICE_ACCOUNT = :value) OR (:type = 4 AND BILLING_ACCOUNT = :value)", nativeQuery = true)
	List<MooringBillingEntity> findByFilter(int type, String value);
	
	@Query(value = "SELECT * FROM CMD_MOORING_BILLING WHERE (:type = 5 AND CORRELATIVE_CMD = :value) OR (:type = 6 AND CORRELATIVE_MOORING_CMD = :value) ", nativeQuery = true)
	List<MooringBillingEntity> findByConsult(int type, Long value);
	
	@Query(value = "SELECT * FROM CMD_MOORING_BILLING WHERE SUBSCRIBER_ID = :SUBSCRIBER_ID AND CMD_STATUS = 'A'", nativeQuery = true)
	List<MooringBillingEntity> findByCmdActive(@Param("SUBSCRIBER_ID") String subscriber);
	
	@Query(value = "SELECT * FROM CMD_MOORING_BILLING WHERE BILLING_ACCOUNT = :BILLING_ACCOUNT AND CMD_STATUS = 'A'", nativeQuery = true)
	List<MooringBillingEntity> findByCmdActiveBill(@Param("BILLING_ACCOUNT") String billingAccount);
	
	List<MooringBillingEntity> findBySubscriberId(String subscriberId);
	
	@Query(value = "SELECT * FROM (SELECT * FROM CMD_MOORING_BILLING WHERE SUBSCRIBER_ID = :SUBSCRIBER_ID ORDER BY CREATE_DATE DESC) WHERE ROWNUM = 1", nativeQuery = true)
	List<MooringBillingEntity> findByConsultSubscriberDesc(@Param("SUBSCRIBER_ID") String subscriber);
	
}
