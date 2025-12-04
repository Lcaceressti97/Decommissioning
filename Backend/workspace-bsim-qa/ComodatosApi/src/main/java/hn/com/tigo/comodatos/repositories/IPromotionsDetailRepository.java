package hn.com.tigo.comodatos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.comodatos.entities.PromotionsDetailEntity;

@Repository
public interface IPromotionsDetailRepository extends JpaRepository<PromotionsDetailEntity, Long>{

	@Query(value = "SELECT * FROM CMD_PROMOTIONS_DETAILS WHERE PLAN_VALUE = :PLAN_VALUE and MONTHS_PERMANENCE = :MONTHS_PERMANENCE and MODEL_CODE = :MODEL_CODE", nativeQuery = true)
	List<PromotionsDetailEntity> buscarModelos(
			@Param("PLAN_VALUE") String PLAN_VALUE,
			@Param("MONTHS_PERMANENCE") String MONTHS_PERMANENCE,
			@Param("MODEL_CODE") String MODEL_CODE);
	
	//@Query(value = "SELECT sum(prod.SUBSIDY_FUND + prod.ADDITIONAL_SUBSIDY + prod.INSTITUTIONAL_FUNDS + prod.COOPS_FUND ) as total FROM cmd_promotions pr inner join cmd_promotions_details prod on pr.id = prod.id_promotion WHERE prod.PLAN_VALUE = :PLAN_VALUE and prod.MONTHS_PERMANENCE = :MONTHS_PERMANENCE and prod.MODEL_CODE = :MODEL_CODE and pr.START_DATE <= to_date(:START_DATE, 'YYYY-MM-DD') and pr.CORPORATE = :CORPORATE and ROWNUM = 1 ", nativeQuery = true)
	@Query(value = "SELECT sum(prod.SUBSIDY_FUND + prod.ADDITIONAL_SUBSIDY + prod.INSTITUTIONAL_FUNDS + prod.COOPS_FUND ) as total FROM cmd_promotions pr inner join cmd_promotions_details prod on pr.id = prod.id_promotion WHERE prod.PLAN_VALUE = :PLAN_VALUE and prod.MONTHS_PERMANENCE = :MONTHS_PERMANENCE and prod.MODEL_CODE = :MODEL_CODE and pr.CORPORATE = :CORPORATE and to_date(:START_DATE , 'YYYY-MM-DD HH24:MI:SS') BETWEEN pr.START_DATE AND pr.END_DATE and ROWNUM = 1 ", nativeQuery = true)
	Object getDesc(
			@Param("PLAN_VALUE") String PLAN_VALUE,
			@Param("MONTHS_PERMANENCE") String MONTHS_PERMANENCE,
			@Param("MODEL_CODE") String MODEL_CODE,
			@Param("CORPORATE") String CORPORATE,
			@Param("START_DATE") String START_DATE) ;
	
//	@Query(value = "SELECT new hn.com.tigo.comodatos.responses.DescTotal(sum(prod.SUBSIDY_FUND + prod.ADDITIONAL_SUBSIDY + prod.INSTITUTIONAL_FUNDS + prod.COOPS_FUND )) FROM cmd_promotions pr inner join cmd_promotions_details prod on pr.id = prod.id_promotion WHERE prod.PLAN_VALUE = :PLAN_VALUE and prod.MONTHS_PERMANENCE = :MONTHS_PERMANENCE and prod.MODEL_CODE = :MODEL_CODE")
//	DescTotal getDesc(
//			@Param("PLAN_VALUE") String PLAN_VALUE,
//			@Param("MONTHS_PERMANENCE") String MONTHS_PERMANENCE,
//			@Param("MODEL_CODE") String MODEL_CODE);
}
 