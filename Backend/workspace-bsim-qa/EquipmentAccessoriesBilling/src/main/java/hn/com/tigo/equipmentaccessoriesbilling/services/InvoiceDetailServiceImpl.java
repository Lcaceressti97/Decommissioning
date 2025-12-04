package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.InvoiceDetailEntity;
import hn.com.tigo.equipmentaccessoriesbilling.exceptions.BadRequestException;
import hn.com.tigo.equipmentaccessoriesbilling.models.InvoiceDetailModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IBillingRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IInvoiceDetailRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IInvoiceDetailService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class InvoiceDetailServiceImpl implements IInvoiceDetailService {

	private final IInvoiceDetailRepository invoiceDetailRepository;
	private IBillingRepository billingRepository;
	private final ILogsService logsService;

	public InvoiceDetailServiceImpl(IInvoiceDetailRepository invoiceDetailRepository,
			IBillingRepository billingRepository, ILogsService logsService) {
		super();
		this.invoiceDetailRepository = invoiceDetailRepository;
		this.billingRepository = billingRepository;
		this.logsService = logsService;
	}

	@Override
	public List<InvoiceDetailModel> getAll() {
		List<InvoiceDetailEntity> entities = this.invoiceDetailRepository
				.findAll(Sort.by(Sort.Direction.DESC, "created"));
		return entities.stream().map(InvoiceDetailEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public InvoiceDetailModel getById(Long id) {
		InvoiceDetailEntity entity = this.invoiceDetailRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));
		return entity.entityToModel();
	}

	@Override
	public InvoiceDetailModel getDetailBySerie(String serieOrBoxNumber) {
		InvoiceDetailEntity entity = this.invoiceDetailRepository.getDetailBySerie(serieOrBoxNumber);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, serieOrBoxNumber));
		return entity.entityToModel();
	}

	@Override
	public List<InvoiceDetailModel> getDetailByIdInvoice(Long idInvoice) {
		List<InvoiceDetailEntity> entities = this.invoiceDetailRepository.getDetailByIdInvoice(idInvoice);
		return entities.stream().map(InvoiceDetailEntity::entityToModel).collect(Collectors.toList());

	}

	@Override
	public List<InvoiceDetailEntity> getDetailByIdInvoiceEntity(Long idInvoice) {
		List<InvoiceDetailEntity> entities = this.invoiceDetailRepository.getDetailByIdInvoice(idInvoice);
		return entities;

	}

	@Override
	public void add(InvoiceDetailModel model) {
		long startTime = System.currentTimeMillis();

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
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(15, 1L, "An error occurred while creating the invoice detail.: " + e.getMessage(), null,
					duration);
			throw e;
		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(15, 1L, "An error occurred while creating the invoice detail.: " + e.getMessage(), null,
					duration);
			throw e;
		}

	}

	@Override
	public void update(Long id, InvoiceDetailModel model) {
		long startTime = System.currentTimeMillis();

		try {
			InvoiceDetailEntity entity = this.invoiceDetailRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));

			BillingEntity billingEntity = this.billingRepository.findById(model.getIdPrefecture()).orElse(null);
			if (billingEntity == null)
				throw new BadRequestException(
						String.format(Constants.ERROR_NOT_FINDING_BY_ID_REFERENCE, model.getIdPrefecture()));

			entity.setModel(model.getModel());
			entity.setDescription(model.getDescription());
			entity.setQuantity(model.getQuantity());
			entity.setUnitPrice(model.getUnitPrice());
			entity.setAmountTotal(model.getAmountTotal());
			entity.setSerieOrBoxNumber(model.getSerieOrBoxNumber());
			entity.setStatus(1L);
			entity.setCreated(LocalDateTime.now());
			this.invoiceDetailRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(15, id, "An error occurred while updating the invoice detail.: " + e.getMessage(), null,
					duration);
			throw e;
		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(15, id, "An error occurred while updating the invoice detail.: " + e.getMessage(), null,
					duration);
			throw e;
		}
	}

	@Override
	public void delete(Long id) {
		InvoiceDetailEntity entity = this.invoiceDetailRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.invoiceDetailRepository.delete(entity);
	}

}
