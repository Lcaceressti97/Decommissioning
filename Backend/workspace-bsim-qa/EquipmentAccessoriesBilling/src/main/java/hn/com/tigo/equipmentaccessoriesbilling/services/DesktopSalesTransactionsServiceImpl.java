package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.DesktopSalesTransactionsEntity;
import hn.com.tigo.equipmentaccessoriesbilling.exceptions.BadRequestException;
import hn.com.tigo.equipmentaccessoriesbilling.models.DesktopSalesTransactionsModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IDesktopSalesTransactionsRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IDesktopSalesTransactionsService;

@Service
public class DesktopSalesTransactionsServiceImpl implements IDesktopSalesTransactionsService {

	private final IDesktopSalesTransactionsRepository desktopSalesTransactionsRepository;

	public DesktopSalesTransactionsServiceImpl(IDesktopSalesTransactionsRepository desktopSalesTransactionsRepository) {
		super();
		this.desktopSalesTransactionsRepository = desktopSalesTransactionsRepository;
	}

	@Override
	public Page<DesktopSalesTransactionsModel> getAllByPagination(Pageable pageable) {
		Page<DesktopSalesTransactionsEntity> entities = this.desktopSalesTransactionsRepository.findAll(pageable);
		return entities.map(DesktopSalesTransactionsEntity::entityToModel);
	}

	@Override
	public DesktopSalesTransactionsModel getById(Long id) {
		DesktopSalesTransactionsEntity entity = this.desktopSalesTransactionsRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format("Error get,Record with id %s is not valid", id));
		return entity.entityToModel();
	}

	@Override
	public List<DesktopSalesTransactionsModel> findByClosingDateAndSeller(Optional<LocalDate> closingDate,
			Long seller) {
		LocalDate closingDateTime = closingDate.get();
        LocalDate nextDay = closingDateTime.plusDays(1); // Agrega un día para incluir todas las horas del día especificado
        
		List<DesktopSalesTransactionsEntity> entities;
		entities = this.desktopSalesTransactionsRepository.getByClosingDateAndSeller(closingDateTime, nextDay, seller);
		return entities.stream().map(DesktopSalesTransactionsEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public void add(DesktopSalesTransactionsModel model) {
		DesktopSalesTransactionsEntity entity = new DesktopSalesTransactionsEntity();

		entity.setId(-1L);
		entity.setLocation(model.getLocation());
		entity.setPeriodDate(model.getPeriodDate());
		entity.setAgency(model.getAgency());
		entity.setAnnexed(model.getAnnexed());
		entity.setSeller(model.getSeller());
		entity.setChargeAmount(model.getChargeAmount());
		entity.setPaymentAmount(model.getPaymentAmount());
		entity.setClosingDate(model.getClosingDate());
		entity.setSts(model.getSts());

		this.desktopSalesTransactionsRepository.save(entity);
	}

	@Override
	public void update(Long id, DesktopSalesTransactionsModel model) {
		DesktopSalesTransactionsEntity entity = this.desktopSalesTransactionsRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format("Error update, Record with id %s is not valid", id));

		entity.setLocation(model.getLocation());
		entity.setPeriodDate(model.getPeriodDate());
		entity.setAgency(model.getAgency());
		entity.setAnnexed(model.getAnnexed());
		entity.setSeller(model.getSeller());
		entity.setChargeAmount(model.getChargeAmount());
		entity.setPaymentAmount(model.getPaymentAmount());
		entity.setClosingDate(model.getClosingDate());
		entity.setSts(model.getSts());

		this.desktopSalesTransactionsRepository.save(entity);
	}

	@Override
	public ResponseEntity<Object> updateChargeAmount(Long id, Double chargeAmount, LocalDateTime closingDate,
			Long seller) {
		DesktopSalesTransactionsEntity entity = this.desktopSalesTransactionsRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new BadRequestException(String.format("Error update, Record with id %s is not valid", id));
		}

		DesktopSalesTransactionsEntity existingEntity = this.desktopSalesTransactionsRepository
				.findByClosingDateAndSeller(closingDate, seller).orElseThrow(() -> new BadRequestException(
						"Error update, Closing date or seller does not match the record"));

		if (!Objects.equals(entity, existingEntity)) {
			throw new BadRequestException("Error update, Closing date or seller does not match the record");
		}

		entity.setChargeAmount(chargeAmount);

		this.desktopSalesTransactionsRepository.save(entity);

		return ResponseEntity.ok("Charge Amount updated successfully");
	}

	@Override
	public void delete(Long id) {
		DesktopSalesTransactionsEntity entity = this.desktopSalesTransactionsRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format("Record with id %s is not valid", id));

		this.desktopSalesTransactionsRepository.delete(entity);

	}

}
