package hn.com.tigo.equipmentinsurance.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import hn.com.tigo.equipmentinsurance.entities.EquipmentInsuranceControlEntity;
import hn.com.tigo.equipmentinsurance.repositories.IEquipmentInsuranceControlRepository;
import hn.com.tigo.equipmentinsurance.services.executeorder.OrderResponse;
import hn.com.tigo.equipmentinsurance.services.interfaces.IProvisioningInsuranceService;
import hn.com.tigo.equipmentinsurance.utils.ReadFilesConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.InsuranceClaimEntity;
import hn.com.tigo.equipmentinsurance.models.InsuranceClaimModel;
import hn.com.tigo.equipmentinsurance.repositories.IInsuranceClaimRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IInsuranceClaimService;
import hn.com.tigo.equipmentinsurance.utils.Constants;

@Service
public class InsuranceClaimServiceImpl implements IInsuranceClaimService {

	private final IInsuranceClaimRepository insuranceClaimRepository;
	private final IProvisioningInsuranceService provisioningInsuranceService;
	private final IEquipmentInsuranceControlRepository equipmentInsuranceControlRepository;
	private final ProcessQueue processQueue;

	public InsuranceClaimServiceImpl(IInsuranceClaimRepository insuranceClaimRepository, IProvisioningInsuranceService provisioningInsuranceService, IEquipmentInsuranceControlRepository equipmentInsuranceControlRepository, ProcessQueue processQueue) {
		this.insuranceClaimRepository = insuranceClaimRepository;
		this.provisioningInsuranceService = provisioningInsuranceService;
		this.equipmentInsuranceControlRepository = equipmentInsuranceControlRepository;
		this.processQueue = processQueue;
	}

	@Override
	public Page<InsuranceClaimModel> getAll(Pageable pageable) {
		Page<InsuranceClaimEntity> entityPage = this.insuranceClaimRepository.getInsuranceClaimPaginated(pageable);
		return entityPage.map(InsuranceClaimEntity::entityToModel);
	}

	@Override
	public List<InsuranceClaimModel> getByPhone(String phone) {
		List<InsuranceClaimEntity> entities = this.insuranceClaimRepository.getInsuranceClaimByPhone(phone);
		return entities.stream().map(InsuranceClaimEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public InsuranceClaimModel getById(Long id) {
		InsuranceClaimEntity entity = this.insuranceClaimRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
		return entity.entityToModel();
	}

	@Override
	public List<InsuranceClaimModel> getByActualEsn(String actualEsn) {
		List<InsuranceClaimEntity> entities = this.insuranceClaimRepository.getInsuranceClaimByActualEsn(actualEsn);
		return entities.stream().map(InsuranceClaimEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public void add(InsuranceClaimModel model) {
		InsuranceClaimEntity entity = new InsuranceClaimEntity();
		entity.setId(-1L);
		entity.setCustomerAccount(model.getCustomerAccount());
		entity.setServiceAccount(model.getServiceAccount());
		entity.setCustomerName(model.getCustomerName());
		entity.setBillingAccount(model.getBillingAccount());
		entity.setPhone(model.getPhone());
		entity.setPhoneStatus(model.getPhoneStatus());
		entity.setClientType(model.getClientType());
		entity.setActualPrice(model.getActualPrice());
		entity.setActualEsn(model.getActualEsn());
		entity.setActualModel(model.getActualModel());
		entity.setActualInvType(model.getActualInvType());
		entity.setReasonClaim(model.getReasonClaim());
		entity.setNewEsn(model.getNewEsn());
		entity.setNewModel(model.getNewModel());
		entity.setNewInvType(model.getNewInvType());
		entity.setUserCreate(model.getUserCreate());
		entity.setDateCreate(LocalDateTime.now());
		entity.setUserResolution(model.getUserResolution());
		entity.setDateResolution(model.getDateResolution());
		entity.setInvoiceType(model.getInvoiceType());
		entity.setInvoiceLetter(model.getInvoiceLetter());
		entity.setInvoiceNumber(model.getInvoiceNumber());
		entity.setBranchAnnex(model.getBranchAnnex());
		entity.setStatusClaim(model.getStatusClaim());
		entity.setObservations(model.getObservations());
		entity.setInsurancePremium(model.getInsurancePremium());
		entity.setDeductible(model.getDeductible());
		entity.setWarehouse(model.getWarehouse());
		entity.setBandit(model.getBandit());
		entity.setNewModelDescription(model.getNewModelDescription());
		entity.setWorkshopOrderNumber(model.getWorkshopOrderNumber());
		entity.setPriceAdjustment(model.getPriceAdjustment());
		entity.setAdjustmentPremiums(model.getAdjustmentPremiums());
		entity.setInsuredSum(model.getInsuredSum());
		this.insuranceClaimRepository.save(entity);
	}

	@Override
	public void update(Long id, InsuranceClaimModel model) {
		InsuranceClaimEntity entity = this.insuranceClaimRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		entity.setCustomerAccount(model.getCustomerAccount());
		entity.setServiceAccount(model.getServiceAccount());
		entity.setBillingAccount(model.getBillingAccount());
		entity.setPhone(model.getPhone());
		entity.setPhoneStatus(model.getPhoneStatus());
		entity.setClientType(model.getClientType());
		entity.setActualPrice(model.getActualPrice());
		entity.setActualEsn(model.getActualEsn());
		entity.setActualModel(model.getActualModel());
		entity.setActualInvType(model.getActualInvType());
		entity.setReasonClaim(model.getReasonClaim());
		entity.setNewEsn(model.getNewEsn());
		entity.setNewModel(model.getNewModel());
		entity.setNewInvType(model.getNewInvType());
		entity.setUserCreate(model.getUserCreate());
		entity.setDateCreate(model.getDateCreate());
		entity.setUserResolution(model.getUserResolution());
		entity.setDateResolution(LocalDateTime.now());
		entity.setInvoiceType(model.getInvoiceType());
		entity.setInvoiceLetter(model.getInvoiceLetter());
		entity.setInvoiceNumber(model.getInvoiceNumber());
		entity.setBranchAnnex(model.getBranchAnnex());
		entity.setStatusClaim(model.getStatusClaim());
		entity.setObservations(model.getObservations());
		entity.setInsurancePremium(model.getInsurancePremium());
		entity.setDeductible(model.getDeductible());
		entity.setWarehouse(model.getWarehouse());
		entity.setBandit(model.getBandit());
		entity.setCustomerName(model.getCustomerName());
		entity.setNewModelDescription(model.getNewModelDescription());
		entity.setWorkshopOrderNumber(model.getWorkshopOrderNumber());
		entity.setPriceAdjustment(model.getPriceAdjustment());
		entity.setAdjustmentPremiums(model.getAdjustmentPremiums());
		entity.setInsuredSum(model.getInsuredSum());

		this.insuranceClaimRepository.save(entity);
	}

	@Override
	public void delete(Long id) {
		try {
			InsuranceClaimEntity entity = this.insuranceClaimRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			OrderResponse orderResponse = provisioningInsuranceService.executeTask(entity, true);
			if (orderResponse.getCode() != 0) {
				throw new BadRequestException("Error Provisioning Insurance: " + orderResponse.getMessage());

			}

			entity.setStatusClaim("A");
			this.insuranceClaimRepository.save(entity);

			updateDeleteInsuranceControl(entity.getActualEsn());

			createBothInsuranceControls(entity);

		} catch (Exception error) {
			throw new BadRequestException(String.format(error.getMessage(), id));

		}
	}

	public void updateDeleteInsuranceControl(String serie) throws IOException {
		try {

			List<EquipmentInsuranceControlEntity> entities = this.equipmentInsuranceControlRepository
					.getEquipmentInsuranceControlByEsn(serie);

			if (!entities.isEmpty()) {
				for (EquipmentInsuranceControlEntity entity : entities) {
					if ("SEG61".equals(entity.getTransactionCode()) || "SEG63".equals(entity.getTransactionCode())) {

						this.processQueue.getProps();
						ReadFilesConfig readConfig = null;
						long startTimeQueue = 0;

						readConfig = new ReadFilesConfig();
						String trama = generateTramaInsurance(entity);

						this.processQueue.processTrama(readConfig, startTimeQueue, trama);

						entity.setInsuranceStatus(1L);
						this.equipmentInsuranceControlRepository.save(entity);
					}

					if ("SEG62".equals(entity.getTransactionCode())) {
						this.equipmentInsuranceControlRepository.delete(entity);
					}
				}
			}

		} catch (BadRequestException e) {
			throw e;

		} catch (Exception e) {
			throw e;
		}
	}

	public String generateTramaInsurance(EquipmentInsuranceControlEntity model) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		StringBuilder marco = new StringBuilder("SEGUROS");

		marco.append("|SERIE=").append(model.getEsn());
		marco.append("|MODELO=").append(model.getEquipmentModel());
		marco.append("|SUSCRIPTOR=").append(model.getSubscriber());
		marco.append("|TELEFONO=").append(model.getPhoneNumber());
		marco.append("|FECINICIO=").append(model.getStartDate().format(formatter));
		marco.append("|FECFIN=").append(model.getEndDate().format(formatter));
		marco.append("|");

		return marco.toString();
	}

	public void createBothInsuranceControls(InsuranceClaimEntity insuranceClaimEntity) {
		createInsuranceControls(
				insuranceClaimEntity.getActualEsn(),
				insuranceClaimEntity.getNewEsn(),
				"SEG62"
		);
	}

	public void createInsuranceControls(String seriesToConsult, String newSeries, String transactionCode2) {
		try {
			List<EquipmentInsuranceControlEntity> entities = this.equipmentInsuranceControlRepository
					.getEquipmentInsuranceControlByEsn(seriesToConsult);

			if (!entities.isEmpty()) {
				EquipmentInsuranceControlEntity existingEntity = entities.get(0);

				createInsuranceControl(existingEntity, newSeries, transactionCode2);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void createInsuranceControl(EquipmentInsuranceControlEntity existingEntity, String series, String transactionCode) {
		try {
			EquipmentInsuranceControlEntity newEntity = createNewEntityFromExisting(existingEntity, series, transactionCode);
			this.equipmentInsuranceControlRepository.save(newEntity);
		} catch (Exception e) {
			throw e;
		}
	}

	private EquipmentInsuranceControlEntity createNewEntityFromExisting(
			EquipmentInsuranceControlEntity existingEntity,
			String series,
			String transactionCode) {


		EquipmentInsuranceControlEntity newEntity = new EquipmentInsuranceControlEntity();
		newEntity.setId(-1L);
		newEntity.setTransactionCode(transactionCode);
		newEntity.setUserAs(existingEntity.getUserAs());
		newEntity.setDateConsultation(existingEntity.getDateConsultation());
		newEntity.setCustomerAccount(existingEntity.getCustomerAccount());
		newEntity.setServiceAccount(existingEntity.getServiceAccount());
		newEntity.setBillingAccount(existingEntity.getBillingAccount());
		newEntity.setPhoneNumber(existingEntity.getPhoneNumber());
		newEntity.setEquipmentModel(existingEntity.getEquipmentModel());
		newEntity.setEsn(series);
		newEntity.setOriginAs(existingEntity.getOriginAs());
		newEntity.setInventoryTypeAs(existingEntity.getInventoryTypeAs());
		newEntity.setOriginTypeAs(existingEntity.getOriginTypeAs());
		newEntity.setDateContract(existingEntity.getDateContract());
		newEntity.setStartDate(existingEntity.getStartDate());
		newEntity.setEndDate(existingEntity.getEndDate());
		newEntity.setDateInclusion(existingEntity.getDateInclusion());
		newEntity.setInsuranceRate(existingEntity.getInsuranceRate());
		newEntity.setPeriod(existingEntity.getPeriod());
		newEntity.setInsuranceRate2(existingEntity.getInsuranceRate2());
		newEntity.setPeriod2(existingEntity.getPeriod2());
		newEntity.setInsuranceRate3(existingEntity.getInsuranceRate3());
		newEntity.setPeriod3(existingEntity.getPeriod3());
		newEntity.setInsuranceStatus(1L);
		newEntity.setSubscriber(existingEntity.getSubscriber());
		newEntity.setTrama(existingEntity.getTrama());

		return newEntity;
	}
}
