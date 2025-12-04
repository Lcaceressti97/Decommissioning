package hn.com.tigo.equipmentinsurance.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.InvoiceTypeEntity;
import hn.com.tigo.equipmentinsurance.models.InvoiceTypeModel;
import hn.com.tigo.equipmentinsurance.repositories.IInvoiceTypeRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IInvoiceTypeService;

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