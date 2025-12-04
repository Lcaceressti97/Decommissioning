package hn.com.tigo.equipmentaccessoriesbilling.services;

import javax.ws.rs.BadRequestException;

import hn.com.tigo.equipmentaccessoriesbilling.entities.PriceMasterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ModelAsEbsEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ModelAsEbsModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IModelAsEbsRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IModelAsEbsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelAsEbsServiceImpl implements IModelAsEbsService {

    private final IModelAsEbsRepository modelAsEbsRepository;
    private final ILogsService logsService;

    public ModelAsEbsServiceImpl(IModelAsEbsRepository modelAsEbsRepository, ILogsService logsService) {
        super();
        this.modelAsEbsRepository = modelAsEbsRepository;
        this.logsService = logsService;
    }

    @Override
    public Page<ModelAsEbsModel> getAllModelAsEbs(Pageable pageable) {
        Page<ModelAsEbsEntity> entities = this.modelAsEbsRepository.getAllModelAsEbs(pageable);
        return entities.map(ModelAsEbsEntity::entityToModel);
    }

    @Override
    public List<ModelAsEbsModel> getModelAsEbsByCodEbs(String codEbs) {
        List<ModelAsEbsEntity> entities = this.modelAsEbsRepository.getModelAsEbsByCodEbs(codEbs);

        if (entities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(Constants.ERROR_NOT_FOUND_MODEL, codEbs));
        }

        return entities.stream().map(ModelAsEbsEntity::entityToModel).collect(Collectors.toList());
    }

    @Override
    public ModelAsEbsModel getModelAsEbsById(Long id) {
        ModelAsEbsEntity entity = this.modelAsEbsRepository.findById(id).orElse(null);

        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

        return entity.entityToModel();
    }

    @Override
    public ModelAsEbsModel findByCodMod(String codMod) {
        ModelAsEbsEntity entity = this.modelAsEbsRepository.findByCodMod(codMod);

        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_CODMOD, codMod));

        return entity.entityToModel();
    }

    @Override
    public void addModelAsEbs(ModelAsEbsModel model) {
        long startTime = System.currentTimeMillis();
        try {
            ModelAsEbsEntity entity = new ModelAsEbsEntity();
            entity.setId(-1L);
            entity.setCodMod(model.getCodMod());
            entity.setCodEbs(model.getCodEbs());
            entity.setSubBod(model.getSubBod());
            entity.setNewMod(model.getNewMod());
            entity.setName(model.getName());
            this.modelAsEbsRepository.save(entity);
        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(18, model.getId(), "Error occurred while adding Model As Ebs: " + e.getMessage(), null,
                    duration);
            throw e;

        }

    }

    @Override
    public void updateModelAsEbs(Long id, ModelAsEbsModel model) {
        long startTime = System.currentTimeMillis();
        try {
            ModelAsEbsEntity entity = this.modelAsEbsRepository.findById(id).orElse(null);
            if (entity == null)
                throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));

            entity.setCodMod(model.getCodMod());
            entity.setCodEbs(model.getCodEbs());
            entity.setSubBod(model.getSubBod());
            entity.setNewMod(model.getNewMod());
            entity.setName(model.getName());
            this.modelAsEbsRepository.save(entity);
        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(17, model.getId(), "Error occurred while update Model As Ebs: " + e.getMessage(), null,
                    duration);
            throw e;

        }

    }

    @Override
    public void deleteModelAsEbs(Long id) {
        ModelAsEbsEntity entity = this.modelAsEbsRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

        this.modelAsEbsRepository.delete(entity);

    }

}
