package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.models.ControlCancellationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IControlCancellationService {

	List<ControlCancellationModel> getAll();

	Page<ControlCancellationModel> getByTypeCancellation(Pageable pageable, Long typeCancellation);

	ControlCancellationModel getById(Long id);

	void add(ControlCancellationModel model) throws Exception;

	void update(Long id, ControlCancellationModel model);

	void delete(Long id);
}
