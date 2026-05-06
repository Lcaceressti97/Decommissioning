package hn.com.tigo.comodatos.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hn.com.tigo.comodatos.entities.PromotionsDetailEntity;
import hn.com.tigo.comodatos.entities.PromotionsEntity;
import hn.com.tigo.comodatos.models.PromotionsModel;
import hn.com.tigo.comodatos.repositories.IPromotionsDetailRepository;
import hn.com.tigo.comodatos.repositories.IPromotionsRepository;
import hn.com.tigo.comodatos.services.interfaces.IPromotionsService;

@Service
@Transactional
public class PromotionsServiceImpl implements IPromotionsService {

    private final IPromotionsRepository promotionsRepository;
    private final IPromotionsDetailRepository promotionsDetailRepository;

    public PromotionsServiceImpl(IPromotionsRepository promotionsRepository,
                                 IPromotionsDetailRepository promotionsDetailRepository) {
        this.promotionsRepository = promotionsRepository;
        this.promotionsDetailRepository = promotionsDetailRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionsModel> getAllPromotions() {
        return promotionsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(PromotionsEntity::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionsModel> getPromotionsByModelCode(String modelCode) {
        return promotionsRepository.getPromotionsByModelCode(modelCode)
                .stream()
                .map(PromotionsEntity::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PromotionsModel getPromotionsById(Long id) {
        PromotionsEntity entity = promotionsRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("No records found for id %s", id)));

        return entity.entityToModel();
    }

    @Override
    public void addPromotions(PromotionsModel promotionsModel) {
        PromotionsEntity promotionsEntity = new PromotionsEntity();
        promotionsEntity.setId(-1L);
        promotionsEntity.setPromotionCode(promotionsModel.getPromotionCode());
        promotionsEntity.setModelCode(promotionsModel.getModelCode());
        promotionsEntity.setDescription(promotionsModel.getDescription());
        promotionsEntity.setCorporate(promotionsModel.getCorporate());
        promotionsEntity.setPermanentValidity(promotionsModel.getPermanentValidity());
        promotionsEntity.setStartDate(LocalDateTime.now());
        promotionsEntity.setEndDate(promotionsModel.getEndDate());
        promotionsEntity.setFinanciado(promotionsModel.getFinanciado());
        promotionsEntity.setGross(promotionsModel.getGross());

        PromotionsEntity savedPromotion = promotionsRepository.save(promotionsEntity);

        List<PromotionsDetailEntity> detailEntities = new ArrayList<>();
        for (PromotionsDetailEntity detailModel : promotionsModel.getPromotionsDetail()) {
            PromotionsDetailEntity detailEntity = new PromotionsDetailEntity();
            detailEntity.setId(-1L);
            detailEntity.setDetail(savedPromotion);
            detailEntity.setPromotionCode(detailModel.getPromotionCode());
            detailEntity.setModelCode(detailModel.getModelCode());
            detailEntity.setPlanValue(detailModel.getPlanValue());
            detailEntity.setMonthsPermanence(detailModel.getMonthsPermanence());
            detailEntity.setSubsidyFund(detailModel.getSubsidyFund());
            detailEntity.setAdditionalSubsidy(detailModel.getAdditionalSubsidy());
            detailEntity.setInstitutionalFunds(detailModel.getInstitutionalFunds());
            detailEntity.setCoopsFund(detailModel.getCoopsFund());
            detailEntity.setStatus(detailModel.getStatus());
            detailEntities.add(detailEntity);
        }

        promotionsDetailRepository.saveAll(detailEntities);
    }

    @Override
    public void updatePromotions(Long id, PromotionsModel promotionsModel) {
        PromotionsEntity promotionsEntity = promotionsRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("Record with id %s is not valid", id)));

        promotionsEntity.setPromotionCode(promotionsModel.getPromotionCode());
        promotionsEntity.setModelCode(promotionsModel.getModelCode());
        promotionsEntity.setDescription(promotionsModel.getDescription());
        promotionsEntity.setCorporate(promotionsModel.getCorporate());
        promotionsEntity.setPermanentValidity(promotionsModel.getPermanentValidity());
        promotionsEntity.setEndDate(promotionsModel.getEndDate());
        promotionsEntity.setFinanciado(promotionsModel.getFinanciado());
        promotionsEntity.setGross(promotionsModel.getGross());

        promotionsRepository.save(promotionsEntity);

        List<PromotionsDetailEntity> requestDetails = promotionsModel.getPromotionsDetail();
        List<PromotionsDetailEntity> currentDetails = promotionsEntity.getPromotionsDetail();

        List<PromotionsDetailEntity> detailsToDelete = currentDetails.stream()
                .filter(current -> requestDetails.stream()
                        .noneMatch(request -> request.getId() != null && request.getId().equals(current.getId())))
                .collect(Collectors.toList());

        promotionsDetailRepository.deleteAll(detailsToDelete);

        for (PromotionsDetailEntity detailModel : requestDetails) {
            PromotionsDetailEntity detailEntity = currentDetails.stream()
                    .filter(current -> current.getId().equals(detailModel.getId()))
                    .findFirst()
                    .orElseGet(() -> {
                        PromotionsDetailEntity newDetail = new PromotionsDetailEntity();
                        newDetail.setDetail(promotionsEntity);
                        return newDetail;
                    });

            detailEntity.setPromotionCode(detailModel.getPromotionCode());
            detailEntity.setModelCode(detailModel.getModelCode());
            detailEntity.setPlanValue(detailModel.getPlanValue());
            detailEntity.setMonthsPermanence(detailModel.getMonthsPermanence());
            detailEntity.setSubsidyFund(detailModel.getSubsidyFund());
            detailEntity.setAdditionalSubsidy(detailModel.getAdditionalSubsidy());
            detailEntity.setInstitutionalFunds(detailModel.getInstitutionalFunds());
            detailEntity.setCoopsFund(detailModel.getCoopsFund());
            detailEntity.setStatus(detailModel.getStatus());

            promotionsDetailRepository.save(detailEntity);
        }
    }

    @Override
    public void deletePromotions(Long id) {
        PromotionsEntity entity = promotionsRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("Record with id %s is not valid", id)));

        promotionsRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionsDetailEntity> searchModels(String precioPromo, String mesesPermanencia, String modelCode) {
        return promotionsDetailRepository.searchModels(precioPromo, mesesPermanencia, modelCode);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getDesc(String precioPromo,
                              String mesesPermanencia,
                              String modelCode,
                              String corporate,
                              String startDate,
                              Integer financiado,
                              Integer gross) {
        return promotionsDetailRepository.getDesc(
                precioPromo,
                mesesPermanencia,
                modelCode,
                corporate,
                startDate,
                financiado,
                gross
        );
    }
}