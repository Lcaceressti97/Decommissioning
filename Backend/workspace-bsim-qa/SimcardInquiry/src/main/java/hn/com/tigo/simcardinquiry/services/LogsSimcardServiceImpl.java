package hn.com.tigo.simcardinquiry.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import hn.com.tigo.simcardinquiry.entities.LogsSimcardEntity;
import hn.com.tigo.simcardinquiry.models.LogsSimcardModel;
import hn.com.tigo.simcardinquiry.repositories.ILogsSimcardRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;

@Service
public class LogsSimcardServiceImpl implements ILogsSimcardService {

	private final ILogsSimcardRepository logsRepository;

	public LogsSimcardServiceImpl(ILogsSimcardRepository logsRepository) {
		super();
		this.logsRepository = logsRepository;
	}

	@Override
	public Page<LogsSimcardModel> getAllLogsByPagination(Pageable pageable) {
		Page<LogsSimcardEntity> entities = this.logsRepository.findAll(pageable);
		return entities.map(LogsSimcardEntity::entityToModel);
	}

	@Override
	public List<LogsSimcardModel> getLogsByDateRange(Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException("Both start date and end date are required.");
		}
		LocalDate startDateTime = startDate.get();
		LocalDate endDateTime = endDate.get().plusDays(1);
		List<LogsSimcardEntity> entities = logsRepository.getLogsByDateRange(startDateTime, endDateTime);
		return entities.stream().map(LogsSimcardEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<LogsSimcardModel> getLogsByTypeError(long typeError) {
		List<LogsSimcardEntity> entities = logsRepository.getLogsByTypeError(typeError);

		if (entities.isEmpty()) {
			throw new BadRequestException("No records found for the type error: " + typeError);
		}

		return entities.stream().map(LogsSimcardEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public void saveLog(long typeError, long reference, String message) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();

			String url = request.getRequestURL().toString();

			StringBuilder sb = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
			String requestPayload = sb.toString();

			LogsSimcardEntity entity = new LogsSimcardEntity();
			entity.setId(-1L);
			entity.setCreated(LocalDateTime.now());
			entity.setTypeError(typeError);
			entity.setMessage(message);
			entity.setReference(reference);
			entity.setSrt(requestPayload);
			entity.setUrl(url);

			logsRepository.save(entity);

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}