package hn.com.tigo.inquiriesamsysnavega.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.inquiriesamsysnavega.entities.NavegaBankEntity;
import hn.com.tigo.inquiriesamsysnavega.models.NavegaBankModel;
import hn.com.tigo.inquiriesamsysnavega.repositories.INavegaBankRepository;
import hn.com.tigo.inquiriesamsysnavega.services.interfaces.ILogsService;
import hn.com.tigo.inquiriesamsysnavega.services.interfaces.INavegaBankService;
import hn.com.tigo.inquiriesamsysnavega.utils.Constants;

@Service
public class NavegaBankServiceImpl implements INavegaBankService {

	private final INavegaBankRepository navegaBankRepository;
	private final ILogsService logsService;

	public NavegaBankServiceImpl(INavegaBankRepository navegaBankRepository, ILogsService logsService) {
		super();
		this.navegaBankRepository = navegaBankRepository;
		this.logsService = logsService;
	}

	@Override
	public List<NavegaBankModel> getAllNavegaBank() {
		List<NavegaBankEntity> entities = this.navegaBankRepository
				.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
		return entities.stream().map(NavegaBankEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public NavegaBankModel getNavegaBankById(Long id) {
		NavegaBankEntity entity = this.navegaBankRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
		return entity.entityToModel();
	}

	@Override
	public NavegaBankModel getBankByIdentifyingLetter(String identifyingLetter) {
		NavegaBankEntity entity = this.navegaBankRepository.findBankByIdentifyingLetter(identifyingLetter);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.NOT_FOUND_RECORD_LETTER, identifyingLetter));
		return entity.entityToModel();
	}

	@Override
	public void addNavegaBank(NavegaBankModel model) {
		try {
			NavegaBankEntity entity = new NavegaBankEntity();
			entity.setId(-1L);
			entity.setCode(model.getCode());
			entity.setNameCompany(model.getNameCompany());
			entity.setBankName(model.getBankName());
			entity.setNameTransaction(model.getNameTransaction());
			entity.setBankAccountName(model.getBankAccountName());
			entity.setBankAccountNum(model.getBankAccountNum());
			entity.setCurrencyCode(model.getCurrencyCode());
			entity.setIdentifyingLetter(model.getIdentifyingLetter());
			entity.setBankAccountEbs(model.getBankAccountEbs());
			entity.setBankCodeAmsys(model.getBankCodeAmsys());
			entity.setCompany(model.getCompany());
			entity.setStatus(model.getStatus());
			entity.setCreatedDate(LocalDateTime.now());
			this.navegaBankRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}
	}

	@Override
	public void updateNavegaBank(Long id, NavegaBankModel model) {
		try {
			NavegaBankEntity entity = this.navegaBankRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			entity.setCode(model.getCode());
			entity.setNameCompany(model.getNameCompany());
			entity.setBankName(model.getBankName());
			entity.setNameTransaction(model.getNameTransaction());
			entity.setBankAccountName(model.getBankAccountName());
			entity.setBankAccountNum(model.getBankAccountNum());
			entity.setCurrencyCode(model.getCurrencyCode());
			entity.setIdentifyingLetter(model.getIdentifyingLetter());
			entity.setBankAccountEbs(model.getBankAccountEbs());
			entity.setBankCodeAmsys(model.getBankCodeAmsys());
			entity.setCompany(model.getCompany());
			entity.setStatus(model.getStatus());
			entity.setCreatedDate(LocalDateTime.now());
			this.navegaBankRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}
	}

	@Override
	public void deleteNavegaBank(Long id) {
		NavegaBankEntity entity = this.navegaBankRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		this.navegaBankRepository.delete(entity);

	}

}
