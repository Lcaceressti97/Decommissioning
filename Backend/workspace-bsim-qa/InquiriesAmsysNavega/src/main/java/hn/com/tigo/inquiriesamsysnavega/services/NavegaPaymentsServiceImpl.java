package hn.com.tigo.inquiriesamsysnavega.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.inquiriesamsysnavega.entities.NavegaPaymentsEntity;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import hn.com.tigo.inquiriesamsysnavega.models.NavegaPaymentsModel;
import hn.com.tigo.inquiriesamsysnavega.repositories.NavegaPaymentsRepository;
import hn.com.tigo.inquiriesamsysnavega.services.interfaces.ILogsService;
import hn.com.tigo.inquiriesamsysnavega.services.interfaces.NavegaPaymentsService;
import hn.com.tigo.inquiriesamsysnavega.utils.Constants;

@Service
public class NavegaPaymentsServiceImpl implements NavegaPaymentsService {

	private final NavegaPaymentsRepository navegaPaymentsRepository;
	private final ILogsService logsService;

	public NavegaPaymentsServiceImpl(NavegaPaymentsRepository navegaPaymentsRepository, ILogsService logsService) {
		this.navegaPaymentsRepository = navegaPaymentsRepository;
		this.logsService = logsService;
	}

	@Override
	public List<NavegaPaymentsModel> getAllPaymentsNavega() {
		List<NavegaPaymentsEntity> entities = this.navegaPaymentsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return entities.stream().map(NavegaPaymentsEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<NavegaPaymentsModel> getPaymentsNavegaByDateRange(Optional<LocalDate> startDate,
			Optional<LocalDate> endDate) {

		if (!startDate.isPresent() || !endDate.isPresent()) {
			throw new BadRequestException(String.format("Both start date and end date are required."));

		}

		LocalDate start = startDate.get();
		LocalDate end = endDate.get();

		if (end.isBefore(start)) {
			throw new BadRequestException(String.format("The end date must be greater than the start date."));
		}

		LocalDate endDateTime = end.plusDays(1);
		List<NavegaPaymentsEntity> entities = navegaPaymentsRepository.getByDateRange(start, endDateTime);
		return entities.stream().map(NavegaPaymentsEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<NavegaPaymentsModel> getPaymentsByEbsAccount(String ebsAccount) {
		List<NavegaPaymentsEntity> entities = navegaPaymentsRepository.findByEbsAccountAndTransactionSts(ebsAccount,
				"P");
		if (entities.isEmpty())
			throw new BadRequestException(String.format(Constants.NOT_FOUND_RECORD_EBSACCOUNT, ebsAccount));
		return entities.stream().map(NavegaPaymentsEntity::entityToModel).collect(Collectors.toList());

	}

	@Override
	public void add(NavegaPaymentsModel model) {
		try {
			NavegaPaymentsEntity entity = new NavegaPaymentsEntity();
			entity.setId(-1L);
			entity.setNavegaCode(model.getNavegaCode());
			entity.setProductCode(model.getProductCode());
			entity.setCurrency(model.getCurrency());
			entity.setPaymentAmount(model.getPaymentAmount());
			entity.setBank(model.getBank());
			entity.setBankAcct(model.getBankAcct());
			entity.setBankAuthorization(model.getBankAuthorization());
			entity.setExchangeRate(model.getExchangeRate());
			entity.setPaymentDate(LocalDateTime.now());
			entity.setIdOrganization(model.getIdOrganization());
			entity.setEbsAccount(model.getEbsAccount());
			entity.setTransactionSts("P");
			entity.setOuName(model.getOuName());
			entity.setSynchron(model.getSynchron());
			entity.setRecMethod(model.getRecMethod());
			entity.setReceiptDate(model.getReceiptDate());
			entity.setGlDate(model.getGlDate());
			entity.setRecNumber(model.getRecNumber());
			entity.setRecAmount(model.getRecAmount());
			entity.setCustAcctNum(model.getCustAcctNum());
			entity.setComments(model.getComments());
			entity.setAttributeCat(model.getAttributeCat());
			entity.setAttribute1(model.getAttribute1());
			entity.setAttribute2(model.getAttribute2());
			entity.setAttribute3(model.getAttribute3());
			this.navegaPaymentsRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(3, model.getBankAuthorization(),
					"Error occurred while adding Navega Payment: " + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(3, model.getBankAuthorization(),
					"Error occurred while adding Navega Payment: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public void cancellationPaymentsNavega(Long id) throws BadRequestException, NotFoundException {
		try {
			NavegaPaymentsEntity entity = this.navegaPaymentsRepository.findById(id)
					.orElseThrow(() -> new NotFoundException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id)));

			if (entity.getTransactionSts().equals("F")) {
				throw new BadRequestException(String.format(Constants.ERROR_CANCELLATION_F, 1));
			} else if (entity.getTransactionSts().equals("A")) {
				throw new BadRequestException(String.format(Constants.ERROR_CANCELLATION_A, 1));
			}

			entity.setTransactionSts("A");
			this.navegaPaymentsRepository.save(entity);
		} catch (BadRequestException | NotFoundException e) {
			logsService.saveLog(4, id, "Error occurred while updating Status Navega Payment: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logsService.saveLog(4, id, "Error occurred while updating Status Navega Payment: " + e.getMessage());
			throw e;
		}
	}

}
