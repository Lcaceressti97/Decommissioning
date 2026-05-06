package hn.com.tigo.comodatos.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import hn.com.tigo.comodatos.entities.PromotionsDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionsModel {

    private Long id;

    @NotNull(message = "Promotion Code is required")
    private String promotionCode;

    @NotNull(message = "Model Code is required")
    private String modelCode;

    private String description;

    private String corporate;

    private String permanentValidity;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer financiado;

    private Integer gross;

    private List<PromotionsDetailEntity> promotionsDetail;
}