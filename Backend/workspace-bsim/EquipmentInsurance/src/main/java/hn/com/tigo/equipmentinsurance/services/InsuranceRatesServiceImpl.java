package hn.com.tigo.equipmentinsurance.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.InsuranceRatesEntity;
import hn.com.tigo.equipmentinsurance.entities.PriceMasterEntity;
import hn.com.tigo.equipmentinsurance.models.ConsultationResponse;
import hn.com.tigo.equipmentinsurance.models.InsuranceRatesModel;
import hn.com.tigo.equipmentinsurance.repositories.IInsuranceRatesRepository;
import hn.com.tigo.equipmentinsurance.repositories.IPriceMasterRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IInsuranceRatesService;
import hn.com.tigo.equipmentinsurance.utils.Constants;

@Service
public class InsuranceRatesServiceImpl implements IInsuranceRatesService {

    private final IInsuranceRatesRepository insuranceRatesRepository;
    private final IPriceMasterRepository priceMasterRepository;

    public InsuranceRatesServiceImpl(IInsuranceRatesRepository insuranceRatesRepository,
                                     IPriceMasterRepository priceMasterRepository) {
        super();
        this.insuranceRatesRepository = insuranceRatesRepository;
        this.priceMasterRepository = priceMasterRepository;
    }

    @Override
    public List<InsuranceRatesModel> getAll() {
        List<InsuranceRatesEntity> entities = this.insuranceRatesRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return entities.stream().map(InsuranceRatesEntity::entityToModel).collect(Collectors.toList());

    }

    @Override
    public List<ConsultationResponse> getMonthlyFeesByModel(String model) {
        List<ConsultationResponse> responses = new ArrayList<>();

        // Obtener el PriceMasterEntity por modelo
        PriceMasterEntity priceMasterEntity = priceMasterRepository.findByModel(model);

        if (priceMasterEntity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_MODEL, model));

        Double price = priceMasterEntity.getPrice();

        // Encontrar InsuranceRatesEntities que coinciden con el rango de precio
        List<InsuranceRatesEntity> insuranceRatesEntities = insuranceRatesRepository
                .findByModelAndValueFromLessThanEqualAndValueUpGreaterThanEqual(model, price, price);

        if (insuranceRatesEntities.isEmpty())
            throw new BadRequestException(
                    String.format(Constants.ERROR_NOT_FOUND_INSURANCE_RATES, model)
            );

        // Iterar sobre cada InsuranceRatesEntity
        for (InsuranceRatesEntity insuranceRatesEntity : insuranceRatesEntities) {
            ConsultationResponse response = new ConsultationResponse();
            response.setPeriodNumber(insuranceRatesEntity.getPeriodNumber());
            response.setMonthlyFee(insuranceRatesEntity.getMonthlyFee());
            responses.add(response);
        }

        return responses;
    }

    @Override
    public InsuranceRatesModel getById(Long id) {
        InsuranceRatesEntity entity = this.insuranceRatesRepository.findById(id).orElse(null);

        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

        return entity.entityToModel();
    }

    @Override
    public void add(InsuranceRatesModel model) {
        try {
            InsuranceRatesEntity entity = new InsuranceRatesEntity();
            entity.setId(-1L);
            entity.setEfectiveDate(model.getEfectiveDate());
            entity.setPeriodNumber(model.getPeriodNumber());
            entity.setValueFrom(model.getValueFrom());
            entity.setValueUp(model.getValueUp());
            entity.setMonthlyFee(model.getMonthlyFee());
            entity.setTextCoverage(model.getTextCoverage());
            entity.setModel(model.getModel());
            this.insuranceRatesRepository.save(entity);
        } catch (BadRequestException e) {
            throw e;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void update(Long id, InsuranceRatesModel model) {
        try {
            InsuranceRatesEntity entity = this.insuranceRatesRepository.findById(id).orElse(null);
            if (entity == null)
                throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));

            entity.setEfectiveDate(model.getEfectiveDate());
            entity.setPeriodNumber(model.getPeriodNumber());
            entity.setValueFrom(model.getValueFrom());
            entity.setValueUp(model.getValueUp());
            entity.setMonthlyFee(model.getMonthlyFee());
            entity.setTextCoverage(model.getTextCoverage());
            entity.setModel(model.getModel());
            this.insuranceRatesRepository.save(entity);
        } catch (BadRequestException e) {

            throw e;

        } catch (Exception e) {

            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        InsuranceRatesEntity entity = this.insuranceRatesRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

        this.insuranceRatesRepository.delete(entity);

    }

}
