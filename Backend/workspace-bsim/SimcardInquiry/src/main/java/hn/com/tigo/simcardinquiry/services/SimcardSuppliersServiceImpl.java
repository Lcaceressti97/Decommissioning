package hn.com.tigo.simcardinquiry.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.SimcardSuppliersEntity;
import hn.com.tigo.simcardinquiry.models.SimcardSuppliersModel;
import hn.com.tigo.simcardinquiry.repositories.ISimcardSuppliersRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardSuppliersService;
import hn.com.tigo.simcardinquiry.utils.Constants;

@Service
public class SimcardSuppliersServiceImpl implements ISimcardSuppliersService {

	private final ISimcardSuppliersRepository simcardSuppliersRepository;
	private final ILogsSimcardService logsService;

	public SimcardSuppliersServiceImpl(ISimcardSuppliersRepository simcardSuppliersRepository,
			ILogsSimcardService logsService) {
		super();
		this.simcardSuppliersRepository = simcardSuppliersRepository;
		this.logsService = logsService;
	}

	@Override
	public List<SimcardSuppliersModel> getAll() {
		List<Object[]> results = this.simcardSuppliersRepository.findAllFieldsExceptBaseFile();
		return results.stream().map(this::mapToSimcardSuppliersModel).collect(Collectors.toList());
	}

	@Override
	public SimcardSuppliersModel getById(Long id) {
		SimcardSuppliersEntity entity = this.simcardSuppliersRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
		return entity.entityToModel();
	}

	@Override
	public void createSupplier(SimcardSuppliersModel model) {
		try {
			SimcardSuppliersEntity entity = new SimcardSuppliersEntity();
			entity.setId(-1L);
			entity.setSupplierNo(model.getSupplierNo());
			entity.setSupplierName(model.getSupplierName());
			entity.setAddress(model.getAddress());
			entity.setPhone(model.getPhone());
			entity.setPostalMail(model.getPostalMail());
			entity.setEmailSupplier(model.getEmailSupplier());
			entity.setEmail(model.getEmail());
			entity.setBaseFile(model.getBaseFile());
			entity.setSubject(model.getSubject());
			entity.setTextEmail(model.getTextEmail());
			entity.setInitialIccd(model.getInitialIccd());
			entity.setKey(model.getKey());
			entity.setCreatedDate(LocalDateTime.now());
			entity.setStatus(1L);

			this.simcardSuppliersRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}
	}

	@Override
	public void updateSupplier(Long id, SimcardSuppliersModel model) {
		try {
			SimcardSuppliersEntity entity = this.simcardSuppliersRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			entity.setSupplierNo(model.getSupplierNo());
			entity.setSupplierName(model.getSupplierName());
			entity.setAddress(model.getAddress());
			entity.setPhone(model.getPhone());
			entity.setPostalMail(model.getPostalMail());
			entity.setEmailSupplier(model.getEmailSupplier());
			entity.setEmail(model.getEmail());
			if (model.getBaseFile() != null) {
				entity.setBaseFile(model.getBaseFile());
			}
			entity.setSubject(model.getSubject());
			entity.setTextEmail(model.getTextEmail());
			entity.setInitialIccd(model.getInitialIccd());
			entity.setKey(model.getKey());

			this.simcardSuppliersRepository.save(entity);

		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}

	}

	@Override
	public void deleteSupplier(Long id) {
		SimcardSuppliersEntity entity = this.simcardSuppliersRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		this.simcardSuppliersRepository.delete(entity);
	}

	private SimcardSuppliersModel mapToSimcardSuppliersModel(Object[] row) {
		SimcardSuppliersModel model = new SimcardSuppliersModel();
		model.setId((Long) row[0]);
		model.setSupplierNo((Long) row[1]);
		model.setSupplierName((String) row[2]);
		model.setAddress((String) row[3]);
		model.setPhone((String) row[4]);
		model.setPostalMail((String) row[5]);
		model.setEmailSupplier((String) row[6]);
		model.setEmail((String) row[7]);
		model.setSubject((String) row[8]);
		model.setTextEmail((String) row[9]);
		model.setInitialIccd((String) row[10]);
		model.setKey((String) row[11]);
		model.setStatus((Long) row[12]);
		model.setCreatedDate((LocalDateTime) row[13]);
		return model;
	}

}
