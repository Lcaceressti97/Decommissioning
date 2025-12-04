package hn.com.tigo.equipmentaccessoriesbilling.services;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ExemptInvoiceApprovalsEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ExemptInvoiceApprovalsModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IExemptInvoiceApprovalsRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IExemptInvoiceApprovalsService;

@Service
public class ExemptInvoiceApprovalsServiceImpl implements IExemptInvoiceApprovalsService {

	private final IExemptInvoiceApprovalsRepository exemptInvoiceApprovalsRepository;

	public ExemptInvoiceApprovalsServiceImpl(IExemptInvoiceApprovalsRepository exemptInvoiceApprovalsRepository) {
		this.exemptInvoiceApprovalsRepository = exemptInvoiceApprovalsRepository;
	}

	@Override
	public void add(ExemptInvoiceApprovalsModel model) {
		ExemptInvoiceApprovalsEntity entity = new ExemptInvoiceApprovalsEntity();
		entity.setId(-1L);
		entity.setIdInvoice(model.getIdInvoice());
		entity.setCommentApproval(model.getCommentApproval());
		entity.setUserApproved(model.getUserApproved());
		entity.setApprovalDate(model.getApprovalDate());
		entity.setStatus(model.getStatus());
		this.exemptInvoiceApprovalsRepository.save(entity);
	}

}
