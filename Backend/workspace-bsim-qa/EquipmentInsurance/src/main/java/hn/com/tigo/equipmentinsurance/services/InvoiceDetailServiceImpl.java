package hn.com.tigo.equipmentinsurance.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.BillingEntity;
import hn.com.tigo.equipmentinsurance.entities.InvoiceDetailEntity;
import hn.com.tigo.equipmentinsurance.models.InvoiceDetailModel;
import hn.com.tigo.equipmentinsurance.repositories.IBillingRepository;
import hn.com.tigo.equipmentinsurance.repositories.IInvoiceDetailRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IInvoiceDetailService;
import hn.com.tigo.equipmentinsurance.utils.Constants;

@Service
public class InvoiceDetailServiceImpl implements IInvoiceDetailService {

	private final IInvoiceDetailRepository invoiceDetailRepository;
	private IBillingRepository billingRepository;

	public InvoiceDetailServiceImpl(IInvoiceDetailRepository invoiceDetailRepository,
			IBillingRepository billingRepository) {
		super();
		this.invoiceDetailRepository = invoiceDetailRepository;
		this.billingRepository = billingRepository;
	}

	@Override
	public List<InvoiceDetailEntity> getDetailByIdInvoiceEntity(Long idInvoice) {
		List<InvoiceDetailEntity> entities = this.invoiceDetailRepository.getDetailByIdInvoice(idInvoice);
		return entities;

	}

	@Override
	public void add(InvoiceDetailModel model) {

		try {
			BillingEntity billingEntity = this.billingRepository.findById(model.getIdPrefecture()).orElse(null);
			if (billingEntity == null) {
				throw new BadRequestException(
						String.format(Constants.ERROR_INVOICE_NOT_EXISTS, model.getIdPrefecture().toString()));
			}

			InvoiceDetailEntity entity = new InvoiceDetailEntity();
			entity.setId(-1L);
			entity.setModel(model.getModel());
			entity.setDescription(model.getDescription());
			entity.setQuantity(model.getQuantity());
			entity.setUnitPrice(model.getUnitPrice());
			entity.setAmountTotal(model.getAmountTotal());
			entity.setSerieOrBoxNumber(model.getSerieOrBoxNumber());
			entity.setStatus(1L);
			entity.setCreated(LocalDateTime.now());
			this.invoiceDetailRepository.save(entity);

			System.out.println("ID generado: " + entity.getId());
		} catch (BadRequestException e) {

			throw e;
		} catch (Exception e) {

			throw e;
		}

	}

}
