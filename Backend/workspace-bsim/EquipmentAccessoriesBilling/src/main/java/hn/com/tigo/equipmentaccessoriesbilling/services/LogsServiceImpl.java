package hn.com.tigo.equipmentaccessoriesbilling.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import hn.com.tigo.equipmentaccessoriesbilling.entities.LogsEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.LogsModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.ILogsRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class LogsServiceImpl implements ILogsService {

    private final ILogsRepository logsRepository;

    public LogsServiceImpl(ILogsRepository logsRepository) {
        super();
        this.logsRepository = logsRepository;
    }

    @Override
    public Page<LogsModel> getAllLogsByPagination(Pageable pageable) {
        Page<LogsEntity> entities = this.logsRepository.getLogs(pageable);
        return entities.map(LogsEntity::entityToModel);
    }

    @Override
    public Page<LogsModel> getLogsByDateRange(Pageable pageable, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
        if (!startDate.isPresent() || !endDate.isPresent()) {
            throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
        }
        LocalDate startDateTime = startDate.get();
        LocalDate endDateTime = endDate.get().plusDays(1);
        Page<LogsEntity> entities = logsRepository.getLogsByDateRange(pageable, startDateTime, endDateTime);
        return entities.map(LogsEntity::entityToModel);
    }

    @Override
    public Page<LogsModel> getLogsByTypeError(Pageable pageable, long typeError) {
        Page<LogsEntity> entities = logsRepository.getLogsByTypeError(pageable, typeError);

        if (entities.isEmpty()) {
            throw new BadRequestException("No records found for the type error: " + typeError);
        }

        return entities.map(LogsEntity::entityToModel);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(long typeError, long reference, String message, String user, long time) {
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

            LogsEntity entity = new LogsEntity();
            entity.setCreated(LocalDateTime.now());
            entity.setTypeError(typeError);
            entity.setMessage(message);
            entity.setReference(reference);
            entity.setSrt(requestPayload);
            entity.setUrl(url);
            entity.setUserCreate(user);
            entity.setExecutionTime(time);

            logsRepository.save(entity);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
