package hn.com.tigo.comodatos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hn.com.tigo.comodatos.entities.MooringBillingEntity;
import hn.com.tigo.comodatos.entities.MooringEntity;
import hn.com.tigo.comodatos.models.MooringBillingModel;
import hn.com.tigo.comodatos.repositories.IMooringBillingRepository;
import hn.com.tigo.comodatos.repositories.MooringRepository;
import hn.com.tigo.comodatos.services.interfaces.IMooringBillingService;

@Service
public class MooringBillingServiceImpl implements IMooringBillingService{
	
	private final IMooringBillingRepository iMooringBillingRepository;
	private final MooringRepository iMooringRepository;
	
	public MooringBillingServiceImpl(IMooringBillingRepository iMooringBillingRepository, MooringRepository iMooringRepository) {
		//super();
		this.iMooringBillingRepository = iMooringBillingRepository;
		this.iMooringRepository = iMooringRepository;
	}

	@Override
	public Page<MooringBillingModel> getAllCmdByPagination(Pageable pageable) {
		Page<MooringBillingEntity> entities = this.iMooringBillingRepository.findAll(pageable);
		return entities.map(MooringBillingEntity::entityToModel);
	}

	@Override
	public List<MooringBillingModel> findByFilter(int type, String value) {
		List<MooringBillingEntity> entities;
		entities = this.iMooringBillingRepository.findByFilter(type, value);
		return entities.stream().map(MooringBillingEntity::entityToModel).collect(Collectors.toList());
	}
	
	@Override
	public List<MooringBillingModel> findByConsult(int type, Long value) {
		List<MooringBillingEntity> entities;
		entities = this.iMooringBillingRepository.findByConsult(type, value);
		return entities.stream().map(MooringBillingEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public MooringBillingModel findById(Long id) {
		
		MooringBillingEntity entity = this.iMooringBillingRepository.findById(id).orElse(null);
		if (entity == null) {
			return null;
		}
		return entity.entityToModel();
		
	}

	
	@Override
	public Long add(MooringBillingModel model) {
		
		// Validaciones
		
		List<MooringBillingEntity> list = this.iMooringBillingRepository.findByCmdActive(model.getSubscriberId());
		
		/**
		 * Validamos si el registro existe y está activo en la en la tabla principal
		 * 
		 */
		if(!list.isEmpty()) {
			throw new BadRequestException(String.format("The [subscriberId] = %s belongs to an active loan and cannot be created", model.getSubscriberId()));
		}
		
		List<MooringEntity> listMorring = this.iMooringRepository.findByCmdMorringActive(model.getSubscriberId());
		
		/**
		 * Validamos si el registro existe y está activo en la tabla hija
		 * 
		 */
		if(!listMorring.isEmpty()) {
			throw new BadRequestException(String.format("The [subscriberId] = %s is already tied and the record cannot be created", model.getSubscriberId()));
		}
		
		MooringBillingEntity entity = new MooringBillingEntity();
		
		entity.setCorrelativeCmd(model.getCorrelativeCmd());
		entity.setCorrelativeMooringCmd(model.getCorrelativeMooringCmd());
		entity.setSubscriberId(model.getSubscriberId());
		entity.setMonthsOfPermanence(model.getMonthsOfPermanence());
		entity.setCmdStatus("A");
		entity.setUserName(model.getUserName());
		entity.setSupervisorUser(model.getSupervisorUser());
		entity.setUserMooring(model.getUserMooring());
		entity.setUserCancelled(model.getUserCancelled());
		entity.setDateOfAdmission(model.getDateOfAdmission());
		
		entity.setInvoiceLocation(model.getInvoiceLocation());
		entity.setInvoiceSubLocity(model.getInvoiceSubLocity());
		entity.setInvoiceType(model.getInvoiceType());
		entity.setInvoiceReading(model.getInvoiceReading());
		entity.setInvoiceNumber(model.getInvoiceNumber());
		entity.setInventoryType(model.getInventoryType());
		entity.setInventoryModel(model.getInventoryModel());		
		entity.setTeamSeries(model.getTeamSeries());		
		entity.setPhoneCost(model.getPhoneCost());
		entity.setPhoneDiscount(model.getPhoneDiscount());
		entity.setVac(model.getVac());
		entity.setMooring(0L);
		entity.setPromotion(model.getPromotion());
		entity.setTransactionId(model.getTransactionId());
		entity.setCustomerAccount(model.getCustomerAccount());
		entity.setServiceAccount(model.getServiceAccount());
		entity.setBillingAccount(model.getBillingAccount());
		entity.setObservations(model.getObservations());
		
		entity.setCreateDate(LocalDateTime.now());
		
		MooringBillingEntity morringEntity = this.iMooringBillingRepository.save(entity);
		
		return morringEntity.getId();
	}

	@Transactional
	@Override
	public void update(Long id, MooringBillingModel model) {
		
		
		MooringBillingEntity entity = this.iMooringBillingRepository.findById(id).orElse(null);
		
		if(entity==null) {
			throw new BadRequestException(String.format("Error update, Record with id %s is not valid", id));
		}
		
		
		entity.setCorrelativeCmd(model.getCorrelativeCmd());
		entity.setCorrelativeMooringCmd(model.getCorrelativeMooringCmd());
		entity.setSubscriberId(model.getSubscriberId());
		entity.setMonthsOfPermanence(model.getMonthsOfPermanence());
		entity.setCmdStatus(model.getCmdStatus());
		entity.setUserName(model.getUserName());
		entity.setSupervisorUser(model.getSupervisorUser());
		entity.setUserMooring(model.getUserMooring());
		entity.setUserCancelled(model.getUserCancelled());
		entity.setDateOfAdmission(model.getDateOfAdmission());
		
		if(model.getDueDate()==null) {
			entity.setDueDate(LocalDateTime.now());
		}
		
		
		entity.setInvoiceLocation(model.getInvoiceLocation());
		entity.setInvoiceSubLocity(model.getInvoiceSubLocity());
		entity.setInvoiceType(model.getInvoiceType());
		entity.setInvoiceReading(model.getInvoiceReading());
		entity.setInvoiceNumber(model.getInvoiceNumber());
		entity.setInventoryType(model.getInventoryType());
		entity.setInventoryModel(model.getInventoryModel());		
		entity.setTeamSeries(model.getTeamSeries());		
		entity.setPhoneCost(model.getPhoneCost());
		entity.setPhoneDiscount(model.getPhoneDiscount());
		entity.setVac(model.getVac());		
		entity.setMooring(model.getMooring());
		entity.setPromotion(model.getPromotion());
		entity.setTransactionId(model.getTransactionId());
		entity.setCustomerAccount(model.getCustomerAccount());
		entity.setServiceAccount(model.getServiceAccount());
		entity.setBillingAccount(model.getBillingAccount());
		entity.setObservations(model.getObservations());
		
		
		this.iMooringBillingRepository.save(entity);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
