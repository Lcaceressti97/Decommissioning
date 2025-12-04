package hn.com.tigo.comodatos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.comodatos.entities.PromotionsDetailEntity;
import hn.com.tigo.comodatos.entities.PromotionsEntity;
import hn.com.tigo.comodatos.models.PromotionsModel;
import hn.com.tigo.comodatos.repositories.IPromotionsDetailRepository;
import hn.com.tigo.comodatos.repositories.IPromotionsRepository;
import hn.com.tigo.comodatos.services.interfaces.IPromotionsService;

@Service
public class PromotionsServiceImpl implements IPromotionsService {

	IPromotionsRepository promotionsRepository;

	 @Autowired
	IPromotionsDetailRepository promotionsDetailRepository;

	public PromotionsServiceImpl(IPromotionsRepository promotionsRepository,
			IPromotionsDetailRepository promotionsDetailRepository) {
		super();
		this.promotionsRepository = promotionsRepository;
		this.promotionsDetailRepository = promotionsDetailRepository;
	}

	@Override
	public List<PromotionsModel> getAllPromotions() {
		List<PromotionsEntity> entities = this.promotionsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return entities.stream().map(PromotionsEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<PromotionsModel> getPromotionsByModelCode(String modelCode) {
		List<PromotionsEntity> entities = this.promotionsRepository.getPromotionsByModelCode(modelCode);
		return entities.stream().map(PromotionsEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public PromotionsModel getPromotionsById(Long id) {
		PromotionsEntity entity = this.promotionsRepository.findById(id).orElse(null);

		if (entity == null)
			throw new BadRequestException(String.format("No records found for id %s", id));

		return entity.entityToModel();
	}

	@Override
	public void addPromotions(PromotionsModel promotionsModel) {
		// Crear la entidad de la promoción
		PromotionsEntity promotionsEntity = new PromotionsEntity();
		promotionsEntity.setId(-1L);
		promotionsEntity.setPromotionCode(promotionsModel.getPromotionCode());
		promotionsEntity.setModelCode(promotionsModel.getModelCode());
		promotionsEntity.setDescription(promotionsModel.getDescription());
		promotionsEntity.setCorporate(promotionsModel.getCorporate());
		promotionsEntity.setPermanentValidity(promotionsModel.getPermanentValidity());
		promotionsEntity.setStartDate(LocalDateTime.now());
		promotionsEntity.setEndDate(promotionsModel.getEndDate());

		// Guardar la entidad de la promoción
		PromotionsEntity savedPromotionsEntity = this.promotionsRepository.save(promotionsEntity);

		// Crear las entidades de los detalles de la promoción
		List<PromotionsDetailEntity> promotionsDetailModels = promotionsModel.getPromotionsDetail();
		List<PromotionsDetailEntity> promotionsDetailEntities = new ArrayList<>();
		for (PromotionsDetailEntity promotionsDetailModel : promotionsDetailModels) {
			PromotionsDetailEntity promotionsDetailEntity = new PromotionsDetailEntity();
			promotionsDetailEntity.setId(-1L);
			promotionsDetailEntity.setDetail(savedPromotionsEntity);
			promotionsDetailEntity.setPromotionCode(promotionsDetailModel.getPromotionCode());
			promotionsDetailEntity.setModelCode(promotionsDetailModel.getModelCode());
			promotionsDetailEntity.setPlanValue(promotionsDetailModel.getPlanValue());
			promotionsDetailEntity.setMonthsPermanence(promotionsDetailModel.getMonthsPermanence());
			promotionsDetailEntity.setSubsidyFund(promotionsDetailModel.getSubsidyFund());
			promotionsDetailEntity.setAdditionalSubsidy(promotionsDetailModel.getAdditionalSubsidy());
			promotionsDetailEntity.setInstitutionalFunds(promotionsDetailModel.getInstitutionalFunds());
			promotionsDetailEntity.setCoopsFund(promotionsDetailModel.getCoopsFund());
			promotionsDetailEntity.setStatus(promotionsDetailModel.getStatus());
			promotionsDetailEntities.add(promotionsDetailEntity);
		}

		// Guardar las entidades de los detalles de la promoción
		this.promotionsDetailRepository.saveAll(promotionsDetailEntities);
	}

	@Override
	public void updatePromotions(Long id, PromotionsModel promotionsModel) {
		// Buscar la entidad de la promoción
		PromotionsEntity promotionsEntity = this.promotionsRepository.findById(id)
				.orElseThrow(() -> new BadRequestException(String.format("Record with id %s is not valid", id)));

		// Actualizar los campos de la promoción
		promotionsEntity.setPromotionCode(promotionsModel.getPromotionCode());
		promotionsEntity.setModelCode(promotionsModel.getModelCode());
		promotionsEntity.setDescription(promotionsModel.getDescription());
		promotionsEntity.setCorporate(promotionsModel.getCorporate());
		promotionsEntity.setPermanentValidity(promotionsModel.getPermanentValidity());
		// promotionsEntity.setStartDate(promotionsModel.getStartDate());
		promotionsEntity.setEndDate(promotionsModel.getEndDate());

		// Guardar la entidad de la promoción actualizada
		this.promotionsRepository.save(promotionsEntity);

		// Actualizar los detalles de la promoción
		List<PromotionsDetailEntity> promotionsDetailModels = promotionsModel.getPromotionsDetail();
		List<PromotionsDetailEntity> existingPromotionsDetailEntities = promotionsEntity.getPromotionsDetail();

		// Eliminar los detalles que ya no están en el modelo
		List<PromotionsDetailEntity> promotionsDetailEntitiesToDelete = existingPromotionsDetailEntities.stream()
				.filter(detail -> !promotionsDetailModels.stream()
						.anyMatch(detailModel -> detailModel.getId().equals(detail.getId())))
				.collect(Collectors.toList());
		this.promotionsDetailRepository.deleteAll(promotionsDetailEntitiesToDelete);

		// Actualizar o crear los detalles de la promoción
		for (PromotionsDetailEntity promotionsDetailModel : promotionsDetailModels) {
			PromotionsDetailEntity promotionsDetailEntity = existingPromotionsDetailEntities.stream()
					.filter(detail -> detail.getId().equals(promotionsDetailModel.getId())).findFirst()
					.orElseGet(() -> {
						PromotionsDetailEntity newPromotionsDetailEntity = new PromotionsDetailEntity();
						newPromotionsDetailEntity.setDetail(promotionsEntity);
						return newPromotionsDetailEntity;
					});

			promotionsDetailEntity.setPromotionCode(promotionsDetailModel.getPromotionCode());
			promotionsDetailEntity.setModelCode(promotionsDetailModel.getModelCode());
			promotionsDetailEntity.setPlanValue(promotionsDetailModel.getPlanValue());
			promotionsDetailEntity.setMonthsPermanence(promotionsDetailModel.getMonthsPermanence());
			promotionsDetailEntity.setSubsidyFund(promotionsDetailModel.getSubsidyFund());
			promotionsDetailEntity.setAdditionalSubsidy(promotionsDetailModel.getAdditionalSubsidy());
			promotionsDetailEntity.setInstitutionalFunds(promotionsDetailModel.getInstitutionalFunds());
			promotionsDetailEntity.setCoopsFund(promotionsDetailModel.getCoopsFund());
			promotionsDetailEntity.setStatus(promotionsDetailModel.getStatus());

			this.promotionsDetailRepository.save(promotionsDetailEntity);
		}
	}

	@Override
	public void deletePromotions(Long id) {
		PromotionsEntity entity = this.promotionsRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format("Record with id %s is not valid", id));

		this.promotionsRepository.delete(entity);

	}

	public List<PromotionsDetailEntity> buscarModelos(String precioPromo, String mesesPermanencia, String modelCode) {

		return this.promotionsDetailRepository.buscarModelos(precioPromo, mesesPermanencia, modelCode);
	}
	
	public Object getDesc(String precioPromo, String mesesPermanencia, String modelCode, String corporate, String startDate) {

		return this.promotionsDetailRepository.getDesc(precioPromo, mesesPermanencia, modelCode, corporate, startDate);
	}
}
