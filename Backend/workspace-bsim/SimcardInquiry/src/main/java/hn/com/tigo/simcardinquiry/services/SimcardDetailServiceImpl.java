package hn.com.tigo.simcardinquiry.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.SimcardDetailEntity;
import hn.com.tigo.simcardinquiry.entities.SimcardOrderControlEntity;
import hn.com.tigo.simcardinquiry.entities.SimcardVersionEntity;
import hn.com.tigo.simcardinquiry.models.SimcardDetailModel;
import hn.com.tigo.simcardinquiry.models.SimcardOrderControlModel;
import hn.com.tigo.simcardinquiry.models.SimcardOrderDetailModel;
import hn.com.tigo.simcardinquiry.repositories.ISimcardDetailRepository;
import hn.com.tigo.simcardinquiry.repositories.ISimcardOrderControlRepository;
import hn.com.tigo.simcardinquiry.repositories.ISimcardVersionRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardDetailService;
import hn.com.tigo.simcardinquiry.utils.Constants;
import hn.com.tigo.simcardinquiry.utils.EncryptDecrypt;

@Service
public class SimcardDetailServiceImpl implements ISimcardDetailService {

	private final ISimcardDetailRepository simcardDetailRepository;
	private final ISimcardOrderControlRepository simcardOrderControlRepository;
	private final ILogsSimcardService logsService;
	private final ISimcardVersionRepository simcardVersionRepository;

	public SimcardDetailServiceImpl(ISimcardDetailRepository simcardDetailRepository,
			ISimcardOrderControlRepository simcardOrderControlRepository, ILogsSimcardService logsService,
			ISimcardVersionRepository simcardVersionRepository) {
		super();
		this.simcardDetailRepository = simcardDetailRepository;
		this.simcardOrderControlRepository = simcardOrderControlRepository;
		this.logsService = logsService;
		this.simcardVersionRepository = simcardVersionRepository;

	}

	@Override
	public List<SimcardDetailModel> getSimcardDetailById(Long idOrderControl) {
		List<SimcardDetailEntity> entities;
		entities = this.simcardDetailRepository.getSimcardDetailById(idOrderControl);
		if (entities.isEmpty())
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, idOrderControl));
		return entities.stream().map(SimcardDetailEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public SimcardDetailModel getById(Long id) {
		SimcardDetailEntity entity = this.simcardDetailRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
		return entity.entityToModel();
	}

	@Override
	public SimcardDetailModel getBySimcard(String icc) {
		SimcardDetailEntity entity = this.simcardDetailRepository.getByIcc(icc);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD_SIMCARD, icc));
		return entity.entityToModel();
	}

	@Override
	public void updateStatusSimcardDetails(Long idOrderControl) {
		try {
			simcardDetailRepository.updateStatusByIdOrderControl(idOrderControl);
		} catch (BadRequestException e) {
			logsService.saveLog(2, idOrderControl,
					"Error occurred while updating Status Simcard Details: " + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(2, idOrderControl,
					"Error occurred while updating Status Simcard Details: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public SimcardOrderDetailModel findByImsiOrIcc(int type, String value) throws Exception {
		try {

			SimcardDetailEntity detailEntity = this.simcardDetailRepository.findByImsiOrIcc(type, value);
			if (detailEntity == null) {
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND, value));
			}

			SimcardOrderControlEntity orderControlEntity = this.simcardOrderControlRepository
					.findById(detailEntity.getIdOrderControl()).orElse(null);
			if (orderControlEntity == null) {
				throw new BadRequestException(
						String.format(Constants.ERROR_NOT_FOUND_RECORD, detailEntity.getIdOrderControl()));
			}

			SimcardVersionEntity simcardVersionEntity = this.simcardVersionRepository
					.getCapacityByIdModel(Long.parseLong(orderControlEntity.getModel()));

			SimcardOrderControlModel orderControlModel = orderControlEntity.entityToModel();
			orderControlModel.setModel(simcardVersionEntity.getCapacity());
			SimcardDetailModel detailModel = detailEntity.entityToModel();
			detailModel.setId(detailEntity.getId());
			detailModel.setIdOrderControl(detailEntity.getIdOrderControl());
			detailModel.setIcc(detailEntity.getIcc());
			detailModel.setImsi(detailEntity.getImsi());
			detailModel.setImsib(EncryptDecrypt.decript(detailEntity.getImsib()));
			detailModel.setKi(detailEntity.getKi());
			detailModel.setPin1(EncryptDecrypt.decript(detailEntity.getPin1()));
			detailModel.setPuk1(EncryptDecrypt.decript(detailEntity.getPuk1()));
			detailModel.setPin2(EncryptDecrypt.decript(detailEntity.getPin2()));
			detailModel.setPuk2(EncryptDecrypt.decript(detailEntity.getPuk2()));
			detailModel.setAdm1(detailEntity.getAdm1());
			detailModel.setAdm2(EncryptDecrypt.decript(detailEntity.getAdm2()));
			detailModel.setAdm3(EncryptDecrypt.decript(detailEntity.getAdm3()));
			detailModel.setAcc(EncryptDecrypt.decript(detailEntity.getAcc()));
			detailModel.setStatus(detailEntity.getStatus());

			return new SimcardOrderDetailModel(orderControlModel, Arrays.asList(detailModel));
		} catch (BadRequestException e) {
			logsService.saveLog(2, 0, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;
		} catch (Exception e) {
			logsService.saveLog(2, 0, Constants.EXCEPTION + e.getMessage());
			throw e;
		}
	}

}
