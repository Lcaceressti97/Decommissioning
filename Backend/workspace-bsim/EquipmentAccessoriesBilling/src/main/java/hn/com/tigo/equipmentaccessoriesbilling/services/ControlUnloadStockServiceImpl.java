package hn.com.tigo.equipmentaccessoriesbilling.services;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlUnloadStockEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ControlUnloadStockModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IControlUnloadStockRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IControlUnloadStockService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ControlUnloadStockServiceImpl implements IControlUnloadStockService {

    private final IControlUnloadStockRepository repository;
    private final ILogsService logsService;

    @Override
    public void saveControlUnloadStock(ControlUnloadStockModel model) {
        long start = System.currentTimeMillis();
        try {
            if (model != null && model.getReference() != null) {
                boolean alreadyPending = repository.existsByReferenceAndStatus(model.getReference(), "PENDING");
                if (alreadyPending) {
                    return;
                }
            }

            ControlUnloadStockEntity e = new ControlUnloadStockEntity();

            e.setReference(model.getReference());
            e.setService(model.getService());
            e.setUrl(model.getUrl());

            e.setInventoryType(model.getInventoryType());
            e.setItemCode(model.getItemCode());
            e.setWarehouseCode(model.getWarehouseCode());
            e.setSubWarehouseCode(model.getSubWarehouseCode());
            e.setReserveKey(model.getReserveKey());

            e.setRequest(model.getRequestJson());
            e.setResponse(model.getResponseJson());

            e.setErrorCode(model.getErrorCode());

            String msg = model.getErrorMessage();
            e.setErrorMessage(msg != null && msg.length() > 2000 ? msg.substring(0, 2000) : msg);

            e.setStacktrace(stackTraceToString(model.getException()));

            e.setStatus(model.getStatus() != null ? model.getStatus() : "PENDING");
            e.setUserCreate(model.getUserCreate());
            e.setExecutionTimeMs(model.getExecutionTimeMs());

            e.setCreated(LocalDateTime.now());
            e.setUpdated(model.getUpdated());

            repository.save(e);

        } catch (Exception ex) {
            long duration = System.currentTimeMillis() - start;
            logsService.saveLog(
                    8,
                    model != null ? model.getReference() : null,
                    "Error occurred while saving ControlUnloadStock failure: " + ex.getMessage(),
                    model != null ? model.getUserCreate() : null,
                    duration
            );
            throw ex;
        }
    }

    public static String stackTraceToString(Throwable ex) {
        if (ex == null) return null;
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
