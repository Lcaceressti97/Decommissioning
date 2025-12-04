package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingServicesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.BillingServicesModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IBillingServicesRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBillingServicesService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class BillingServicesServiceImpl implements IBillingServicesService {

    private final IBillingServicesRepository billingServicesRepository;
    private final ILogsService logsService;

    public BillingServicesServiceImpl(IBillingServicesRepository billingServicesRepository, ILogsService logsService) {
        super();
        this.billingServicesRepository = billingServicesRepository;
        this.logsService = logsService;
    }

    @Override
    public Page<BillingServicesModel> getAllPageable(Pageable pageable) {
        Pageable descendingPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("id").descending());
        Page<BillingServicesEntity> entities = this.billingServicesRepository.findAll(descendingPageable);
        return entities.map(BillingServicesEntity::entityToModel);
    }

    @Override
    public BillingServicesModel getById(Long id) {
        BillingServicesEntity entity = this.billingServicesRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));
        return entity.entityToModel();
    }

    @Override
    public void add(BillingServicesModel model) {
        long startTime = System.currentTimeMillis();
        try {
            BillingServicesEntity entity = new BillingServicesEntity();
            entity.setId(-1L);
            entity.setServiceCode(model.getServiceCode());
            entity.setServiceName(model.getServiceName());
            entity.setTotalQuantity(model.getTotalQuantity());
            entity.setCreationUser(model.getCreationUser());
            entity.setCreationDate(LocalDateTime.now());
            entity.setStatus(1L);
            this.billingServicesRepository.save(entity);
        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(16, model.getServiceCode(),
                    "Error occurred while adding Billing Services: " + e.getMessage(), model.getCreationUser(),
                    duration);
            throw e;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(16, model.getServiceCode(),
                    "Error occurred while adding Billing Services: " + e.getMessage(), model.getCreationUser(),
                    duration);
            throw e;
        }

    }

    @Override
    public void update(Long id, BillingServicesModel model) {
        long startTime = System.currentTimeMillis();

        try {
            BillingServicesEntity entity = this.billingServicesRepository.findById(id).orElse(null);
            if (entity == null)
                throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));

            entity.setServiceCode(model.getServiceCode());
            entity.setServiceName(model.getServiceName());
            entity.setTotalQuantity(model.getTotalQuantity());
            entity.setModificationUser(model.getModificationUser());
            entity.setModificationDate(LocalDateTime.now());
            entity.setStatus(model.getStatus());
            this.billingServicesRepository.save(entity);
        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(16, model.getServiceCode(),
                    "Error occurred while update Billing Services: " + e.getMessage(), model.getModificationUser(),
                    duration);
            throw e;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(16, model.getServiceCode(),
                    "Error occurred while update Billing Services: " + e.getMessage(), model.getModificationUser(),
                    duration);
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        BillingServicesEntity entity = this.billingServicesRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

        this.billingServicesRepository.delete(entity);
    }

}
