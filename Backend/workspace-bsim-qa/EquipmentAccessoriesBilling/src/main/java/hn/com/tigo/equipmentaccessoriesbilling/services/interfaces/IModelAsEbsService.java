package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import hn.com.tigo.equipmentaccessoriesbilling.models.ModelAsEbsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IModelAsEbsService {

	Page<ModelAsEbsModel> getAllModelAsEbs(Pageable pageable);

	List<ModelAsEbsModel> getModelAsEbsByCodEbs(String codEbs);

	ModelAsEbsModel getModelAsEbsById(Long id);

	ModelAsEbsModel findByCodMod(String codMod);

	void addModelAsEbs(ModelAsEbsModel model);

	void updateModelAsEbs(Long id, ModelAsEbsModel model);

	void deleteModelAsEbs(Long id);

}
