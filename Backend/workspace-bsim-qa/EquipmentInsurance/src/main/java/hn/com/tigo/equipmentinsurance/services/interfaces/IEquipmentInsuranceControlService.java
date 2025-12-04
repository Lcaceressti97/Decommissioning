package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.models.CalculateOutstandingFeesReponse;
import hn.com.tigo.equipmentinsurance.models.CalculateOutstandingFeesRequest;
import hn.com.tigo.equipmentinsurance.models.EquipmentInsuranceControlModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEquipmentInsuranceControlService {

	Page<EquipmentInsuranceControlModel> getAll(Pageable pageable);

	List<EquipmentInsuranceControlModel> getByPhoneNumber(String phoneNumber);

	EquipmentInsuranceControlModel getById(Long id);

	List<EquipmentInsuranceControlModel> getByEsn(String esn);

	CalculateOutstandingFeesReponse calculateOutstandingFees(CalculateOutstandingFeesRequest request);

	void add(EquipmentInsuranceControlModel model) throws Exception;

	void update(Long id, EquipmentInsuranceControlModel model);

	void delete(Long id);

}
