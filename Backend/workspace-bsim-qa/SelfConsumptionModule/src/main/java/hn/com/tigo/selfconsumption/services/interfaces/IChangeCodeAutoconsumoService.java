package hn.com.tigo.selfconsumption.services.interfaces;

import java.util.List;

import hn.com.tigo.selfconsumption.models.ChangeCodeAutoconsumoModel;

public interface IChangeCodeAutoconsumoService {

	List<ChangeCodeAutoconsumoModel> getAllChangeCodeAutoconsumo();

	ChangeCodeAutoconsumoModel getChangeCodeAutoconsumoById(Long id);

	Long addChangeCodeAutoconsumo(ChangeCodeAutoconsumoModel model);

	void updateChangeCodeAutoconsumo(Long id, ChangeCodeAutoconsumoModel model);

	void deleteChangeCodeAutoconsumo(Long id);
}
