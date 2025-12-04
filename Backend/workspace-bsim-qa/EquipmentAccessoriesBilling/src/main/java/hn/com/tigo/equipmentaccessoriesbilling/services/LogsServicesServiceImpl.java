package hn.com.tigo.equipmentaccessoriesbilling.services;

import hn.com.tigo.equipmentaccessoriesbilling.entities.LogsServicesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.LogsServiceModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.ILogsServiceRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsServicesService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class LogsServicesServiceImpl implements ILogsServicesService {

    private final ILogsServiceRepository logsServiceRepository;

    public LogsServicesServiceImpl(ILogsServiceRepository logsServiceRepository) {
        this.logsServiceRepository = logsServiceRepository;
    }

    @Override
    public void saveLog(LogsServiceModel model) {
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

            LogsServicesEntity entity = new LogsServicesEntity();
            entity.setId(-1L);
            entity.setRequest(model.getRequest());
            entity.setResponse(model.getResponse());
            entity.setReference(model.getReference());
            entity.setService(model.getService());
            entity.setUrl(url);
            entity.setUserCreate(model.getUserCreate());
            entity.setExecutionTime(model.getExecutionTime());
            entity.setCreated(LocalDateTime.now());

            logsServiceRepository.save(entity);

        } catch (Exception ex) {
           throw new BadRequestException(ex.toString()) ;
        }
    }
}
