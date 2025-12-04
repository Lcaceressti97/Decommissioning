package hn.com.tigo.equipmentinsurance.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.SafeControlEquipmentEntity;
import hn.com.tigo.equipmentinsurance.exceptions.BadRequestException;
import hn.com.tigo.equipmentinsurance.models.SafeControlEquipmentModel;
import hn.com.tigo.equipmentinsurance.repositories.ISafeControlEquipmentRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.ISafeControlEquipmentService;

@Service
public class SafeControlEquipmentServiceImpl implements ISafeControlEquipmentService {

	private final ISafeControlEquipmentRepository safeControlEquipmentRepository;

	public SafeControlEquipmentServiceImpl(ISafeControlEquipmentRepository safeControlEquipmentRepository) {
		this.safeControlEquipmentRepository = safeControlEquipmentRepository;
	}

	@Override
	public List<SafeControlEquipmentModel> getAll() {
		List<SafeControlEquipmentEntity> entities = this.safeControlEquipmentRepository.findAll();
		return entities.stream().map(SafeControlEquipmentEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public SafeControlEquipmentModel getById(Long id) {
		SafeControlEquipmentEntity entity = this.safeControlEquipmentRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format("Error get,Record with id %s is not valid", id));
		return entity.entityToModel();
	}

	@Override
	public List<SafeControlEquipmentModel> getByPhone(String phone) {
		List<SafeControlEquipmentEntity> entities;
		entities = this.safeControlEquipmentRepository.getByPhone(phone);
		return entities.stream().map(SafeControlEquipmentEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public void add(SafeControlEquipmentModel model) {
		SafeControlEquipmentEntity entity = new SafeControlEquipmentEntity();
		entity.setId(-1L);
		entity.setSubscriberId(model.getSubscriberId());
		entity.setEsn(model.getEsn());
		entity.setCustomerAccount(model.getCustomerAccount());
		entity.setServiceAccount(model.getServiceAccount());
		entity.setBillingAccount(model.getBillingAccount());
		entity.setPhone(model.getPhone());
		entity.setPhoneModel(model.getPhoneModel());
		entity.setOriginPhone(model.getOriginPhone());
		entity.setInventoryType(model.getInventoryType());
		entity.setOriginType(model.getOriginType());
		entity.setDateStartService(model.getDateStartService());
		entity.setDateInit(model.getDateInit());
		entity.setDateEnd(model.getDateEnd());
		entity.setDateInclusion(model.getDateInclusion());
		entity.setMonthlyFee(model.getMonthlyFee());
		entity.setCurrentPeriod(model.getCurrentPeriod());
		entity.setInsuranceStatus(model.getInsuranceStatus());
		entity.setUserAs400(model.getUserAs400());
		entity.setDateTransaction(model.getDateTransaction());
		entity.setOperationProgram(model.getOperationProgram());
		entity.setPeriod2(model.getPeriod2());
		entity.setMonthlyFee2(model.getMonthlyFee2());
		entity.setPeriod3(model.getPeriod3());
		entity.setMonthlyFee3(model.getMonthlyFee3());
		this.safeControlEquipmentRepository.save(entity);
	}

	@Override
	public void update(Long id, SafeControlEquipmentModel model) {
		SafeControlEquipmentEntity entity = this.safeControlEquipmentRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format("Error update, Record with id %s is not valid", id));
		entity.setSubscriberId(model.getSubscriberId());
		entity.setEsn(model.getEsn());
		entity.setCustomerAccount(model.getCustomerAccount());
		entity.setServiceAccount(model.getServiceAccount());
		entity.setBillingAccount(model.getBillingAccount());
		entity.setPhone(model.getPhone());
		entity.setPhoneModel(model.getPhoneModel());
		entity.setOriginPhone(model.getOriginPhone());
		entity.setInventoryType(model.getInventoryType());
		entity.setOriginType(model.getOriginType());
		entity.setDateStartService(model.getDateStartService());
		entity.setDateInit(model.getDateInit());
		entity.setDateEnd(model.getDateEnd());
		entity.setDateInclusion(model.getDateInclusion());
		entity.setMonthlyFee(model.getMonthlyFee());
		entity.setCurrentPeriod(model.getCurrentPeriod());
		entity.setInsuranceStatus(model.getInsuranceStatus());
		entity.setUserAs400(model.getUserAs400());
		entity.setDateTransaction(model.getDateTransaction());
		entity.setOperationProgram(model.getOperationProgram());
		entity.setPeriod2(model.getPeriod2());
		entity.setMonthlyFee2(model.getMonthlyFee2());
		entity.setPeriod3(model.getPeriod3());
		entity.setMonthlyFee3(model.getMonthlyFee3());
		this.safeControlEquipmentRepository.save(entity);
	}

	@Override
	public void delete(Long id) {
		SafeControlEquipmentEntity entity = this.safeControlEquipmentRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format("Record with id %s is not valid", id));

		this.safeControlEquipmentRepository.delete(entity);
	}

}
