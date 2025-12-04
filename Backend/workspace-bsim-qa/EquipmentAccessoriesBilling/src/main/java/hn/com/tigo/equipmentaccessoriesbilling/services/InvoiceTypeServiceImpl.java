package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.InvoiceTypeEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.InvoiceTypeModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IInvoiceTypeRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IInvoiceTypeService;

@Service
public class InvoiceTypeServiceImpl implements IInvoiceTypeService {

	private final IInvoiceTypeRepository invoiceTypeRepository;

	public InvoiceTypeServiceImpl(IInvoiceTypeRepository invoiceTypeRepository) {
		super();
		this.invoiceTypeRepository = invoiceTypeRepository;
	}

	@Override
	public List<InvoiceTypeModel> getAllInvoiceType() {
		List<InvoiceTypeEntity> entities = this.invoiceTypeRepository.findAll();
		return entities.stream().map(InvoiceTypeEntity::entityToModel).collect(Collectors.toList());

	}

}
