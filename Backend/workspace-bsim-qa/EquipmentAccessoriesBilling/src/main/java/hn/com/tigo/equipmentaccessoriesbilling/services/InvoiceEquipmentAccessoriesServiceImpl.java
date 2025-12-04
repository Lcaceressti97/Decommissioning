package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.InvoiceEquipmentAccessoriesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.exceptions.BadRequestException;
import hn.com.tigo.equipmentaccessoriesbilling.models.InvoiceEquipmentAccessoriesModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.InvoiceEquipmentAccessoriesRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.InvoiceEquipmentAccessoriesService;

@Service
public class InvoiceEquipmentAccessoriesServiceImpl implements InvoiceEquipmentAccessoriesService {

	private final InvoiceEquipmentAccessoriesRepository invoiceEquipmentAccessoriesRepository;

	public InvoiceEquipmentAccessoriesServiceImpl(
			InvoiceEquipmentAccessoriesRepository invoiceEquipmentAccessoriesRepository) {
		this.invoiceEquipmentAccessoriesRepository = invoiceEquipmentAccessoriesRepository;
	}

	@Override
	public List<InvoiceEquipmentAccessoriesModel> getAll() {
		List<InvoiceEquipmentAccessoriesEntity> entities = this.invoiceEquipmentAccessoriesRepository
				.findAll(Sort.by(Sort.Direction.DESC, "created"));
		return entities.stream().map(InvoiceEquipmentAccessoriesEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<InvoiceEquipmentAccessoriesModel> findBy(int type, String value) {
		List<InvoiceEquipmentAccessoriesEntity> entities;
		entities = this.invoiceEquipmentAccessoriesRepository.findBy(type, value);
		return entities.stream().map(InvoiceEquipmentAccessoriesEntity::entityToModel).collect(Collectors.toList());
	}

	/*
	 * public List<InvoiceEquipmentAccessoriesModel> filterInvoices(Long
	 * invoiceStatus, String warehouse, String agency, String createdBy) {
	 * Specification<InvoiceEquipmentAccessoriesEntity> spec =
	 * InvoiceSpecifications.filterByParameters(invoiceStatus, warehouse, agency,
	 * createdBy); List<InvoiceEquipmentAccessoriesEntity> filteredEntities =
	 * invoiceEquipmentAccessoriesRepository.findAll(spec);
	 * 
	 * return filteredEntities.stream().map(InvoiceEquipmentAccessoriesEntity::
	 * entityToModel) .collect(Collectors.toList()); }
	 */
	@Override
	public List<InvoiceEquipmentAccessoriesModel> getExoneratedInvoices() {
		List<InvoiceEquipmentAccessoriesEntity> exoneratedEntities = invoiceEquipmentAccessoriesRepository
				.findByExonerationStatus();
		return exoneratedEntities.stream().map(InvoiceEquipmentAccessoriesEntity::entityToModel)
				.collect(Collectors.toList());
	}

	@Override
	public List<InvoiceEquipmentAccessoriesModel> getInvoicesByUserAndPermissions(String approvedUser) {
		List<InvoiceEquipmentAccessoriesEntity> entities = this.invoiceEquipmentAccessoriesRepository
				.findInvoicesByUserAndPermissions(approvedUser);
		return entities.stream().map(InvoiceEquipmentAccessoriesEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public InvoiceEquipmentAccessoriesModel getById(Long id) {
		InvoiceEquipmentAccessoriesEntity entity = this.invoiceEquipmentAccessoriesRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format("Error get,Record with id %s is not valid", id));
		return entity.entityToModel();
	}

	@Override
	public void add(InvoiceEquipmentAccessoriesModel model) {
		InvoiceEquipmentAccessoriesEntity entity = new InvoiceEquipmentAccessoriesEntity();
		entity.setId(-1L);
		entity.setInvoiceNo(model.getInvoiceNo());
		entity.setInvoiceType(model.getInvoiceType());
		entity.setInvoiceStatus(model.getInvoiceStatus());
		entity.setBillingAccount(model.getBillingAccount());
		entity.setSubscriber(model.getSubscriber());
		entity.setCustcode(model.getCustcode());
		entity.setEmailaddr(model.getEmailaddr());
		entity.setCompany(model.getCompany());
		entity.setCai(model.getCai());
		entity.setCustomerName(model.getCustomerName());
		entity.setCustomerType(model.getCustomerType());
		entity.setDocumentNo(model.getDocumentNo());
		entity.setDiplomaticCardNo(model.getDiplomaticCardNo());
		entity.setCorrelativeOrdenExemptNo(model.getCorrelativeOrdenExemptNo());
		entity.setCorrelativeCertificateExoNo(model.getCorrelativeCertificateExoNo());
		entity.setXml(model.getXml());
		entity.setPath(model.getPath());
		entity.setAddress(model.getAddress());
		entity.setWarehouse(model.getWarehouse());
		entity.setAgency(model.getAgency());
		entity.setTransactionUser(model.getTransactionUser());
		entity.setChargeLocal(model.getChargeLocal());
		entity.setChargeUsd(model.getChargeUsd());
		entity.setExchangeRate(model.getExchangeRate());
		entity.setTax(model.getTax());
		entity.setDiscount(model.getDiscount());
		entity.setCreated(model.getCreated());
		entity.setCreatedBy(model.getCreatedBy());
		this.invoiceEquipmentAccessoriesRepository.save(entity);
	}

	@Override
	public void update(Long id, InvoiceEquipmentAccessoriesModel model) {
		InvoiceEquipmentAccessoriesEntity entity = this.invoiceEquipmentAccessoriesRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format("Error update, Record with id %s is not valid", id));

		entity.setInvoiceNo(model.getInvoiceNo());
		entity.setInvoiceType(model.getInvoiceType());
		entity.setInvoiceStatus(model.getInvoiceStatus());
		entity.setBillingAccount(model.getBillingAccount());
		entity.setSubscriber(model.getSubscriber());
		entity.setCustcode(model.getCustcode());
		entity.setEmailaddr(model.getEmailaddr());
		entity.setCompany(model.getCompany());
		entity.setCai(model.getCai());
		entity.setCustomerName(model.getCustomerName());
		entity.setCustomerType(model.getCustomerType());
		entity.setDocumentNo(model.getDocumentNo());
		entity.setDiplomaticCardNo(model.getDiplomaticCardNo());
		entity.setCorrelativeOrdenExemptNo(model.getCorrelativeOrdenExemptNo());
		entity.setCorrelativeCertificateExoNo(model.getCorrelativeCertificateExoNo());
		entity.setXml(model.getXml());
		entity.setPath(model.getPath());
		entity.setAddress(model.getAddress());
		entity.setWarehouse(model.getWarehouse());
		entity.setAgency(model.getAgency());
		entity.setTransactionUser(model.getTransactionUser());
		entity.setChargeLocal(model.getChargeLocal());
		entity.setChargeUsd(model.getChargeUsd());
		entity.setExchangeRate(model.getExchangeRate());
		entity.setTax(model.getTax());
		entity.setDiscount(model.getDiscount());
		entity.setCreated(model.getCreated());
		entity.setCreatedBy(model.getCreatedBy());
		this.invoiceEquipmentAccessoriesRepository.save(entity);
	}

	@Override
	public void updateDocumentNo(Long id, String documentNo) {
		InvoiceEquipmentAccessoriesEntity entity = this.invoiceEquipmentAccessoriesRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new BadRequestException(String.format("Error updateFields, Record with id %s is not valid", id));
		}
		entity.setDocumentNo(documentNo);

		this.invoiceEquipmentAccessoriesRepository.save(entity);
	}

	@Override
	public void updateCorporateClient(Long id, String correlativeOrdenExemptNo, String correlativeCertificateExoNo) {
		InvoiceEquipmentAccessoriesEntity entity = this.invoiceEquipmentAccessoriesRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new BadRequestException(String.format("Error updateFields, Record with id %s is not valid", id));
		}
		entity.setCorrelativeOrdenExemptNo(correlativeOrdenExemptNo);
		entity.setCorrelativeCertificateExoNo(correlativeCertificateExoNo);
		entity.setExonerationStatus(2L);
		// entity.setTax("0");

		this.invoiceEquipmentAccessoriesRepository.save(entity);
	}

	@Override
	public void updateSingleClient(Long id, String diplomaticCardNo) {
		InvoiceEquipmentAccessoriesEntity entity = this.invoiceEquipmentAccessoriesRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new BadRequestException(String.format("Error updateFields, Record with id %s is not valid", id));
		}
		entity.setDiplomaticCardNo(diplomaticCardNo);
		entity.setExonerationStatus(2L);
		// entity.setTax("0");

		this.invoiceEquipmentAccessoriesRepository.save(entity);
	}

	@Override
	public void updateStatusExoTax(Long id) {
		InvoiceEquipmentAccessoriesEntity entity = this.invoiceEquipmentAccessoriesRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new BadRequestException(String.format("Error updateFields, Record with id %s is not valid", id));
		}

		Long currentStatus = entity.getInvoiceStatus();

		if (currentStatus == 3L) {
			entity.setInvoiceStatus(5L);
		} else if (currentStatus == 4L) {
			entity.setInvoiceStatus(5L);
		} else if (currentStatus == 5L) {
			entity.setInvoiceStatus(10L);
		} else {
			throw new BadRequestException(String.format("Invalid status %s for update", currentStatus));
		}

		entity.setTax("0");

		this.invoiceEquipmentAccessoriesRepository.save(entity);
	}

	@Override
	public void delete(Long id) {
		InvoiceEquipmentAccessoriesEntity entity = this.invoiceEquipmentAccessoriesRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format("Record with id %s is not valid", id));

		this.invoiceEquipmentAccessoriesRepository.delete(entity);
	}

}
