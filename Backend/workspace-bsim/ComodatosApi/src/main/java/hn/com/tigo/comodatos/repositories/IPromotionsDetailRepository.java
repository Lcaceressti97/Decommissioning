package hn.com.tigo.comodatos.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.comodatos.entities.PromotionsDetailEntity;

@Repository
public interface IPromotionsDetailRepository extends JpaRepository<PromotionsDetailEntity, Long> {

    @Query(value = "SELECT * " +
            "FROM CMD_PROMOTIONS_DETAILS " +
            "WHERE PLAN_VALUE = :PLAN_VALUE " +
            "AND MONTHS_PERMANENCE = :MONTHS_PERMANENCE " +
            "AND MODEL_CODE = :MODEL_CODE",
            nativeQuery = true)
    List<PromotionsDetailEntity> searchModels(
            @Param("PLAN_VALUE") String planValue,
            @Param("MONTHS_PERMANENCE") String monthsPermanence,
            @Param("MODEL_CODE") String modelCode
    );

    @Query(value = "SELECT NVL(SUM( " +
            "NVL(prod.SUBSIDY_FUND, 0) + " +
            "NVL(prod.ADDITIONAL_SUBSIDY, 0) + " +
            "NVL(prod.INSTITUTIONAL_FUNDS, 0) + " +
            "NVL(prod.COOPS_FUND, 0) " +
            "), 0) AS total " +
            "FROM CMD_PROMOTIONS pr " +
            "INNER JOIN CMD_PROMOTIONS_DETAILS prod ON pr.ID = prod.ID_PROMOTION " +
            "WHERE prod.PLAN_VALUE = :PLAN_VALUE " +
            "AND prod.MONTHS_PERMANENCE = :MONTHS_PERMANENCE " +
            "AND prod.MODEL_CODE = :MODEL_CODE " +
            "AND pr.CORPORATE = :CORPORATE " +
            "AND pr.FINANCIADO = :FINANCIADO " +
            "AND pr.GROSS = :GROSS " +
            "AND TO_DATE(:START_DATE, 'YYYY-MM-DD HH24:MI:SS') BETWEEN pr.START_DATE AND pr.END_DATE " +
            "AND prod.STATUS = 1 " +
            "AND ROWNUM = 1",
            nativeQuery = true)
    BigDecimal getDesc(
            @Param("PLAN_VALUE") String planValue,
            @Param("MONTHS_PERMANENCE") String monthsPermanence,
            @Param("MODEL_CODE") String modelCode,
            @Param("CORPORATE") String corporate,
            @Param("START_DATE") String startDate,
            @Param("FINANCIADO") Integer financiado,
            @Param("GROSS") Integer gross
    );
}