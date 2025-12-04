package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import hn.com.tigo.equipmentaccessoriesbilling.entities.PriceMasterEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.PriceMasterDTO;
import hn.com.tigo.equipmentaccessoriesbilling.models.PriceMasterModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IPriceMasterRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IPriceMasterService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class PriceMasterServiceImpl implements IPriceMasterService {

    private final IPriceMasterRepository priceMasterRepository;
    private final ILogsService logsService;

    public PriceMasterServiceImpl(IPriceMasterRepository priceMasterRepository, ILogsService logsService) {
        super();
        this.priceMasterRepository = priceMasterRepository;
        this.logsService = logsService;
    }

    @Override
    public Page<PriceMasterModel> getAll(Pageable pageable) {
        Pageable descendingPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("id").descending());
        Page<PriceMasterEntity> entities = this.priceMasterRepository.findAll(descendingPageable);
        return entities.map(PriceMasterEntity::entityToModel);
    }

    @Override
    public List<PriceMasterDTO> getAllPriceMaster() {
        List<PriceMasterEntity> entities = this.priceMasterRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return entities.stream().map(PriceMasterDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<PriceMasterDTO> getPriceMasterByModel(String model) {
        List<PriceMasterEntity> entities = this.priceMasterRepository.getPriceMasterByModel(model);

        if (entities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.OK, String.format(Constants.ERROR_NOT_FOUND_MODEL, model));
        }

        return entities.stream().map(PriceMasterDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<PriceMasterModel> getPriceMasterModelByModel(String model) {
        List<PriceMasterEntity> entities = this.priceMasterRepository.getPriceMasterByModel(model);

        if (entities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(Constants.ERROR_NOT_FOUND_MODEL, model));
        }

        return entities.stream().map(PriceMasterEntity::entityToModel).collect(Collectors.toList());
    }

    @Override
    public PriceMasterModel getPriceMasterById(Long id) {
        PriceMasterEntity entity = this.priceMasterRepository.findById(id).orElse(null);

        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

        return entity.entityToModel();
    }

    @Override
    public PriceMasterModel getPriceMasterByModelAndInventoryType(String model, String inventoryType) {
        PriceMasterEntity entity = this.priceMasterRepository.getPriceMasterByModelAndInventoryType(model,
                inventoryType);

        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_MODEL, model));

        return entity.entityToModel();
    }

    @Override
    public void addPriceMaster(PriceMasterModel model) {
        long startTime = System.currentTimeMillis();
        try {
            PriceMasterEntity entity = new PriceMasterEntity();
            entity.setId(-1L);
            entity.setInventoryType(model.getInventoryType());
            entity.setModel(model.getModel());
            entity.setDescription(model.getDescription());
            entity.setBaseCost(model.getBaseCost());
            entity.setFactorCode(model.getFactorCode());
            entity.setPrice(model.getPrice());
            entity.setUserCreated(model.getUserCreated());
            entity.setScreen(model.getScreen());
            entity.setCreated(LocalDateTime.now());
            entity.setCurrency(model.getCurrency());
            entity.setConvertLps(model.getConvertLps());
            entity.setPriceLps(model.getPriceLps());
            entity.setLastCost(model.getLastCost());
            entity.setCostTemporary(model.getCostTemporary());
            entity.setPriceChangeEsn(model.getPriceChangeEsn());
            entity.setEsn(model.getEsn());
            entity.setPriceEsn(model.getPriceEsn());
            entity.setPriceLpsEsn(model.getPriceLpsEsn());
            this.priceMasterRepository.save(entity);
        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(17, model.getId(), "Error occurred while adding Price Master: " + e.getMessage(),
                    model.getUserCreated(), duration);
            throw e;

        }
    }

    @Override
    public void updatePriceMaster(Long id, PriceMasterModel model) {
        long startTime = System.currentTimeMillis();
        try {
            PriceMasterEntity entity = this.priceMasterRepository.findById(id).orElse(null);
            if (entity == null)
                throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));

            entity.setInventoryType(model.getInventoryType());
            entity.setModel(model.getModel());
            entity.setDescription(model.getDescription());
            entity.setBaseCost(model.getBaseCost());
            entity.setFactorCode(model.getFactorCode());
            entity.setPrice(model.getPrice());
            entity.setUserCreated(model.getUserCreated());
            entity.setScreen(model.getScreen());
            entity.setCreated(LocalDateTime.now());
            entity.setCurrency(model.getCurrency());
            entity.setConvertLps(model.getConvertLps());
            entity.setPriceLps(model.getPriceLps());
            entity.setLastCost(model.getLastCost());
            entity.setCostTemporary(model.getCostTemporary());
            entity.setPriceChangeEsn(model.getPriceChangeEsn());
            entity.setEsn(model.getEsn());
            entity.setPriceEsn(model.getPriceEsn());
            entity.setPriceLpsEsn(model.getPriceLpsEsn());
            this.priceMasterRepository.save(entity);
        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(17, model.getId(), "Error occurred while update Price Master: " + e.getMessage(),
                    model.getUserCreated(), duration);
            throw e;

        }
    }

    @Override
    public void deletePriceMaster(Long id) {
        PriceMasterEntity entity = this.priceMasterRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

        this.priceMasterRepository.delete(entity);

    }

}
