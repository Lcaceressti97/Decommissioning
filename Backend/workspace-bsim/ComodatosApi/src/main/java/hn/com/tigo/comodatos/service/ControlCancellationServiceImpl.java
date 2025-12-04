package hn.com.tigo.comodatos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.comodatos.entities.ControlCancellationEntity;
import hn.com.tigo.comodatos.entities.MooringBillingEntity;
import hn.com.tigo.comodatos.entities.MooringEntity;
import hn.com.tigo.comodatos.models.CancelMooringModel;
import hn.com.tigo.comodatos.models.ControlCancellationModel;
import hn.com.tigo.comodatos.models.MooringModel;
import hn.com.tigo.comodatos.repositories.IControlCancellationRepository;
import hn.com.tigo.comodatos.repositories.IMooringBillingRepository;
import hn.com.tigo.comodatos.repositories.MooringRepository;
import hn.com.tigo.comodatos.services.interfaces.IControlCancellationService;
import hn.com.tigo.comodatos.utils.ValidateParam;

@Service
public class ControlCancellationServiceImpl implements IControlCancellationService {

	// Props
	private final IControlCancellationRepository controlCancellationRepository;
	private final IMooringBillingRepository iMooringBillingRepository;
	private final MooringRepository iMooringRepository;

	public ControlCancellationServiceImpl(IControlCancellationRepository controlCancellationRepository,
			IMooringBillingRepository iMooringBillingRepository, MooringRepository iMooringRepository) {
		super();
		this.controlCancellationRepository = controlCancellationRepository;
		this.iMooringBillingRepository = iMooringBillingRepository;
		this.iMooringRepository = iMooringRepository;
	}

	@Override
	public List<ControlCancellationModel> getAll() {

		return this.controlCancellationRepository.findAll().stream().map(ControlCancellationEntity::entityToModel)
				.collect(Collectors.toList());
	}

	@Override
	public void add(ControlCancellationModel model) {

		ControlCancellationEntity entity = new ControlCancellationEntity();

		entity.setId(-1L);
		entity.setCancellationUser(model.getCancellationUser());
		entity.setDescription(model.getDescription());
		entity.setIdReference(model.getIdReference());
		entity.setCreated(LocalDateTime.now());

		this.controlCancellationRepository.save(entity);

	}

	@Override
	public CancelMooringModel cancel(CancelMooringModel model) {

		final boolean validateParam = model.getType().equals("subscriber");

		List<MooringBillingEntity> data = null;

		// Condición que nos ayuda a validar que método usar para traer la data
		if (validateParam) {

			data = this.iMooringBillingRepository.findByCmdActive(model.getSubscriber());

			if (data.isEmpty()) {
				throw new BadRequestException(String.format("No active comodato was found for the value %s of the [%s]",
						model.getSubscriber(), model.getType()));
			}

			LocalDateTime date = LocalDateTime.now();
			model.setDueDate(date);
			
			// Setear los valores de cancelación
			MooringBillingEntity updateData = data.get(0);
			updateData.setCmdStatus("C");
			updateData.setUserCancelled(model.getCancellationUser());
			updateData.setDueDate(date);
			updateData.setMooring(0L);

			// Obtenemos los datos de la tabla hija CMD_MOORING
			List<MooringEntity> dataMooring = this.iMooringRepository.findByIdMooringBilling(updateData.getId());
			List<MooringEntity> morringUpdate = new ArrayList<>();
			/**
			 * Si hay registro se modificando los nuevos valores
			 * 
			 */
			if (!dataMooring.isEmpty()) {

				for (MooringEntity mooring : dataMooring) {
					mooring.setUnmooringUser(model.getCancellationUser());
					mooring.setUnmooringDate(date);
					mooring.setMooringStatus(0);

					morringUpdate.add(mooring);
				}

			} // Fin del bucle

			// Construimos el logs de cancelación
			ControlCancellationEntity entity = new ControlCancellationEntity();

			entity.setId(-1L);
			entity.setCancellationUser(model.getCancellationUser());
			entity.setDescription(model.getDescription());
			entity.setIdReference(updateData.getId());
			entity.setCreated(date);

			System.out.println(morringUpdate.size());

			// Guardamos todos los cambios
			this.iMooringBillingRepository.save(updateData);

			if (morringUpdate.size() > 0) {
				this.iMooringRepository.saveAll(morringUpdate);
			}

			this.controlCancellationRepository.save(entity);
			;

		} else {

			data = this.iMooringBillingRepository.findByCmdActiveBill(model.getBillingAccount());

			if (data.isEmpty()) {
				throw new BadRequestException(String.format("No active comodato was found for the value %s of the [%s]",
						model.getBillingAccount(), model.getType()));
			}

			LocalDateTime date = LocalDateTime.now();
			model.setDueDate(date);

			// Setear los valores de cancelación
			MooringBillingEntity updateData = data.get(0);
			updateData.setCmdStatus("C");
			updateData.setUserCancelled(model.getCancellationUser());
			updateData.setDueDate(date);
			updateData.setMooring(0L);

			// Obtenemos los datos de la tabla hija CMD_MOORING
			List<MooringEntity> dataMooring = this.iMooringRepository.findByIdMooringBilling(updateData.getId());
			List<MooringEntity> morringUpdate = new ArrayList<>();

			/**
			 * Si hay registro se modificando los nuevos valores
			 * 
			 */
			if (!dataMooring.isEmpty()) {

				for (MooringEntity mooring : dataMooring) {
					mooring.setUnmooringUser(model.getCancellationUser());
					mooring.setUnmooringDate(date);
					mooring.setMooringStatus(0);

					morringUpdate.add(mooring);
				}

			} // Fin del bucle

			// Construimos el logs de cancelación
			ControlCancellationEntity entity = new ControlCancellationEntity();

			entity.setId(-1L);
			entity.setCancellationUser(model.getCancellationUser());
			entity.setDescription(model.getDescription());
			entity.setIdReference(updateData.getId());
			entity.setCreated(date);

			//System.out.println(morringUpdate.size());

			// Guardamos todos los cambios
			this.iMooringBillingRepository.save(updateData);

			if (morringUpdate.size() > 0) {
				this.iMooringRepository.saveAll(morringUpdate);
			}

			this.controlCancellationRepository.save(entity);

		}
		
		
		return model;

	}

}
