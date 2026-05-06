package hn.com.tigo.comodatos.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import hn.com.tigo.comodatos.models.PromotionsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CMD_PROMOTIONS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CMD_PROMOTIONS")
    @SequenceGenerator(name = "SQ_CMD_PROMOTIONS", sequenceName = "SQ_CMD_PROMOTIONS", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROMOTION_CODE", length = 50)
    private String promotionCode;

    @Column(name = "MODEL_CODE", length = 50)
    private String modelCode;

    @Column(name = "DESCRIPTION", length = 60)
    private String description;

    @Column(name = "CORPORATE", length = 50)
    private String corporate;

    @Column(name = "PERMANENT_VALIDITY", length = 50)
    private String permanentValidity;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "FINANCIADO")
    private Integer financiado;

    @Column(name = "GROSS")
    private Integer gross;

    @OneToMany(mappedBy = "detail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<PromotionsDetailEntity> promotionsDetail;

    public PromotionsModel entityToModel() {
        PromotionsModel model = new PromotionsModel();
        model.setId(this.id);
        model.setPromotionCode(this.promotionCode);
        model.setModelCode(this.modelCode);
        model.setDescription(this.description);
        model.setCorporate(this.corporate);
        model.setPermanentValidity(this.permanentValidity);
        model.setStartDate(this.startDate);
        model.setEndDate(this.endDate);
        model.setFinanciado(this.financiado);
        model.setGross(this.gross);
        model.setPromotionsDetail(this.promotionsDetail);
        return model;
    }
}