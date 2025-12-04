package hn.com.tigo.comodatos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.comodatos.entities.PromotionsDetailEntity;
import hn.com.tigo.comodatos.entities.PromotionsEntity;

@Repository
public interface IPromotionsRepository extends JpaRepository<PromotionsEntity, Long> {
	
	
	//@Query(value = "SELECT sum(prod.SUBSIDY_FUND + prod.ADDITIONAL_SUBSIDY + prod.INSTITUTIONAL_FUNDS + prod.COOPS_FUND ) as total FROM cmd_promotions pr inner join cmd_promotions_details prod on pr.id = prod.id_promotion where pr.id = 11 and prod.PLAN_VALUE = #planValue and prod.MONTHS_PERMANENCE = #meses and prod.MODEL_CODE =#modelcode and (TO_DATE('19-09-2024', 'DD-MM-YYYY') <= SYSDATE or pr.PERMANENT_VALIDITY = 'Y')", nativeQuery = true)
	@Query(value = "SELECT promotion_code FROM CMD_PROMOTIONS", nativeQuery = true)
	List<PromotionsEntity> buscarPromociones(@Param("VIGENCIA") String VIGENCIA);
	
	//@Query(value = "SELECT p.* FROM persona p JOIN direccion d ON p.direccion_id = d.id WHERE d.ciudad = :ciudad", nativeQuery = true)
	//	List<Persona> buscarPersonasPorCiudad(@Param("ciudad") String ciudad);
	
	List<PromotionsEntity> getPromotionsByModelCode (String modelCode);

}
