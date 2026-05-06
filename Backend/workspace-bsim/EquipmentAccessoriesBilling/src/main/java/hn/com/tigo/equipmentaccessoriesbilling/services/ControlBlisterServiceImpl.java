package hn.com.tigo.equipmentaccessoriesbilling.services;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlBlisterEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ControlBlisterModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IControlBlisterRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IControlBlisterService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;

@Service
public class ControlBlisterServiceImpl implements IControlBlisterService {

    private final IControlBlisterRepository controlBlisterRepository;
    private final ILogsService logsService;

    public ControlBlisterServiceImpl(IControlBlisterRepository controlBlisterRepository, ILogsService logsService) {
        super();
        this.controlBlisterRepository = controlBlisterRepository;
        this.logsService = logsService;
    }

    @Override
    public Page<ControlBlisterModel> getAllControlBlister(Pageable pageable) {
        Pageable descendingPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("id").descending()
        );

        Page<ControlBlisterEntity> entities = this.controlBlisterRepository.findAll(descendingPageable);
        return entities.map(ControlBlisterEntity::entityToModel);
    }

    @Override
    public ControlBlisterModel getControlBlisterById(Long id) {
        ControlBlisterEntity entity = this.controlBlisterRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
        }

        return entity.entityToModel();
    }

    @Override
    public void addControlBlister(ControlBlisterModel model) {
        long startTime = System.currentTimeMillis();

        try {
            if (model == null) {
                throw new BadRequestException("Request body cannot be null");
            }

            String normalizedModel = model.getModel() == null ? null : model.getModel().trim();

            if (normalizedModel == null || normalizedModel.isEmpty()) {
                throw new BadRequestException("MODEL cannot be null or blank");
            }

            if (controlBlisterRepository.existsByModelIgnoreCase(normalizedModel)) {
                throw new BadRequestException("There is already a record with the same MODEL.");
            }

            ControlBlisterEntity entity = new ControlBlisterEntity();
            entity.setId(-1L);
            entity.setModel(normalizedModel);
            entity.setCreated(LocalDateTime.now());

            controlBlisterRepository.save(entity);

        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;
            Long idForLog = (model != null && model.getId() != null) ? model.getId() : -1L;

            logsService.saveLog(
                    20,
                    idForLog,
                    "Error occurred while adding ControlBlister: " + e.getMessage(),
                    null,
                    duration
            );
            throw e;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            Long idForLog = (model != null && model.getId() != null) ? model.getId() : -1L;

            logsService.saveLog(
                    20,
                    idForLog,
                    "Error occurred while adding ControlBlister: " + e.getMessage(),
                    null,
                    duration
            );
            throw e;
        }
    }

    @Override
    public void updateControlBlister(Long id, ControlBlisterModel model) {
        long startTime = System.currentTimeMillis();
        long idForLog = (id != null) ? id : -1L;

        try {
            if (id == null) {
                throw new BadRequestException("ID cannot be null");
            }

            if (model == null) {
                throw new BadRequestException("Request body cannot be null");
            }

            ControlBlisterEntity entity = controlBlisterRepository.findById(id).orElse(null);
            if (entity == null) {
                throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));
            }

            String normalizedModel = model.getModel() == null ? null : model.getModel().trim();

            if (normalizedModel == null || normalizedModel.isEmpty()) {
                throw new BadRequestException("MODEL cannot be null or blank");
            }

            if (controlBlisterRepository.existsByModelIgnoreCaseAndIdNot(normalizedModel, id)) {
                throw new BadRequestException("There is already a record with the same MODEL.");
            }

            entity.setModel(normalizedModel);
            controlBlisterRepository.save(entity);

        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(
                    20,
                    idForLog,
                    "Error occurred while update ControlBlister: " + e.getMessage(),
                    null,
                    duration
            );
            throw e;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(
                    20,
                    idForLog,
                    "Error occurred while update ControlBlister: " + e.getMessage(),
                    null,
                    duration
            );
            throw e;
        }
    }

    @Override
    public void deleteControlBlister(Long id) {
        ControlBlisterEntity entity = this.controlBlisterRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));
        }

        this.controlBlisterRepository.delete(entity);
    }
}