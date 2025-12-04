package hn.com.tigo.comodatos.services.interfaces;

import java.util.List;

import hn.com.tigo.comodatos.entities.PromotionsDetailEntity;
import hn.com.tigo.comodatos.models.PromotionsModel;

public interface IPromotionsService {

	List<PromotionsModel> getAllPromotions();

	List<PromotionsModel> getPromotionsByModelCode(String modelCode);

	PromotionsModel getPromotionsById(Long id);

	void addPromotions(PromotionsModel model);

	void updatePromotions(Long id, PromotionsModel model);

	void deletePromotions(Long id);

	List<PromotionsDetailEntity> buscarModelos(String precioPromo, String mesesPermanencia, String modelCode);
	
	Object getDesc(String precioPromo, String mesesPermanencia, String modelCode, String corporate, String startDate);
}
