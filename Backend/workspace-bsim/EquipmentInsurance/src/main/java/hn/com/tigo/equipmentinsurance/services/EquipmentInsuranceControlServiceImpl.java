package hn.com.tigo.equipmentinsurance.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.EquipmentInsuranceControlEntity;

import javax.ws.rs.BadRequestException;

import hn.com.tigo.equipmentinsurance.models.CalculateOutstandingFeesReponse;
import hn.com.tigo.equipmentinsurance.models.CalculateOutstandingFeesRequest;
import hn.com.tigo.equipmentinsurance.models.ConfigParametersModel;
import hn.com.tigo.equipmentinsurance.models.EquipmentInsuranceControlModel;
import hn.com.tigo.equipmentinsurance.repositories.IEquipmentInsuranceControlRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentinsurance.services.interfaces.IEquipmentInsuranceControlService;
import hn.com.tigo.equipmentinsurance.utils.Constants;
import hn.com.tigo.equipmentinsurance.utils.ReadFilesConfig;

@Service
public class EquipmentInsuranceControlServiceImpl implements IEquipmentInsuranceControlService {

    private final IEquipmentInsuranceControlRepository safeControlEquipmentRepository;
    private final ProcessQueue processQueue;
    private final IConfigParametersService configParametersService;

    public EquipmentInsuranceControlServiceImpl(IEquipmentInsuranceControlRepository safeControlEquipmentRepository,
                                                ProcessQueue processQueue, IConfigParametersService configParametersService) {
        this.safeControlEquipmentRepository = safeControlEquipmentRepository;
        this.processQueue = processQueue;
        this.configParametersService = configParametersService;

    }

    @Override
    public Page<EquipmentInsuranceControlModel> getAll(Pageable pageable) {
        Page<EquipmentInsuranceControlEntity> entityPage = this.safeControlEquipmentRepository
                .getEquipmentInsuranceControlPaginated(pageable);
        return entityPage.map(EquipmentInsuranceControlEntity::entityToModel);
    }

    @Override
    public List<EquipmentInsuranceControlModel> getByEsn(String esn) {
        List<EquipmentInsuranceControlEntity> entities;
        entities = this.safeControlEquipmentRepository.getEquipmentInsuranceControlByEsn(esn);
        return entities.stream().map(EquipmentInsuranceControlEntity::entityToModel).collect(Collectors.toList());
    }

    @Override
    public List<EquipmentInsuranceControlModel> getByPhoneNumber(String phoneNumber) {
        List<EquipmentInsuranceControlEntity> entities;
        entities = this.safeControlEquipmentRepository.getEquipmentInsuranceControlByPhoneNumber(phoneNumber);
        return entities.stream().map(EquipmentInsuranceControlEntity::entityToModel).collect(Collectors.toList());
    }

    @Override
    public EquipmentInsuranceControlModel getById(Long id) {
        EquipmentInsuranceControlEntity entity = this.safeControlEquipmentRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
        return entity.entityToModel();
    }

    @Override
    public CalculateOutstandingFeesReponse calculateOutstandingFees(CalculateOutstandingFeesRequest request) {
        Optional<EquipmentInsuranceControlEntity> optionalEntity = this.safeControlEquipmentRepository
                .getByEquipmentModelAndEsn(request.getEquipmentModel(), request.getEsn());

        if (!optionalEntity.isPresent()) {
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_MODEL, request.getEquipmentModel()));
        }

        EquipmentInsuranceControlEntity entity = optionalEntity.get();

        List<ConfigParametersModel> listParameters = this.configParametersService.getByIdApplication(1000L);
        Map<String, List<String>> parametersSe = new HashMap<>();
        Map<String, String> paramsInsurance = new HashMap<>();

        for (ConfigParametersModel parameter : listParameters) {
            String paramName = parameter.getParameterName();
            String paramValue = parameter.getParameterValue();

            if (!parametersSe.containsKey(paramName)) {
                parametersSe.put(paramName, new ArrayList<>());
            }

            parametersSe.get(paramName).add(paramValue);
            paramsInsurance.put(paramName, paramValue);
        }

        String insurancePremiums = paramsInsurance.get("INSURANCE_PREMIUMS");

        CalculateOutstandingFeesReponse response = new CalculateOutstandingFeesReponse();
        response.setEquipmentModel(entity.getEquipmentModel());
        response.setEsn(entity.getEsn());

        LocalDateTime requestStartDate = request.getStartDate();
        LocalDateTime contractStartDate = entity.getStartDate();
        LocalDateTime contractEndDate = entity.getEndDate();

        // Calcular los periodos
        LocalDateTime period1Start = contractStartDate;
        LocalDateTime period1End = contractStartDate.plusYears(1);

        LocalDateTime period2Start = period1End;
        LocalDateTime period2End = period2Start.plusYears(1);

        LocalDateTime period3Start = period2End;
        LocalDateTime period3End = contractEndDate;

        // Inicializar totales de cada periodo
        double totalPeriod1 = calculatePeriod1Total(requestStartDate, period1Start, period1End,
                entity.getInsuranceRate());
        double totalPeriod2 = calculatePeriod2Total(requestStartDate, period1End, period2Start, period2End,
                entity.getInsuranceRate2());
        double totalPeriod3 = calculatePeriod3Total(requestStartDate, period2End, period3Start, period3End,
                entity.getInsuranceRate3());

        // Calcular el total de los periodos según el valor de insurancePremiums
        double totalPeriods = calculateTotalPeriods(insurancePremiums, totalPeriod1, totalPeriod2, totalPeriod3);

        // Asignar valores a la respuesta con 2 decimales
        response.setTotalPeriod1(roundToTwoDecimals(totalPeriod1));
        response.setTotalPeriod2(roundToTwoDecimals(totalPeriod2));
        response.setTotalPeriod3(roundToTwoDecimals(totalPeriod3));
        response.setTotalPeriods(roundToTwoDecimals(totalPeriods));

        return response;
    }

    private double calculatePeriod1Total(LocalDateTime requestStartDate, LocalDateTime period1Start,
                                         LocalDateTime period1End, double insuranceRate) {
        if (requestStartDate.isBefore(period1End)) {
            int monthsPassedPeriod1 = (int) ChronoUnit.MONTHS.between(period1Start, requestStartDate);
            int monthsRemainingPeriod1 = Math.max(0, 12 - monthsPassedPeriod1);
            return roundToTwoDecimals(monthsRemainingPeriod1 * insuranceRate);
        }
        return 0.00;
    }

    private double calculatePeriod2Total(LocalDateTime requestStartDate, LocalDateTime period1End,
                                         LocalDateTime period2Start, LocalDateTime period2End, double insuranceRate2) {
        if (requestStartDate.isAfter(period1End) && requestStartDate.isBefore(period2End)) {
            int monthsPassedPeriod2 = (int) ChronoUnit.MONTHS.between(period2Start, requestStartDate);
            int monthsRemainingPeriod2 = Math.max(0, 12 - monthsPassedPeriod2);
            return roundToTwoDecimals(monthsRemainingPeriod2 * insuranceRate2);
        } else if (requestStartDate.isAfter(period2End)) {
            return 0.00;
        } else {
            return roundToTwoDecimals(12 * insuranceRate2);
        }
    }

    private double calculatePeriod3Total(LocalDateTime requestStartDate, LocalDateTime period2End,
                                         LocalDateTime period3Start, LocalDateTime period3End, double insuranceRate3) {
        if (requestStartDate.isAfter(period2End) && requestStartDate.isBefore(period3End)) {
            int monthsPassedPeriod3 = (int) ChronoUnit.MONTHS.between(period3Start, requestStartDate);
            int monthsRemainingPeriod3 = Math.max(0, 12 - monthsPassedPeriod3);
            return roundToTwoDecimals(monthsRemainingPeriod3 * insuranceRate3);
        } else if (requestStartDate.isAfter(period3End)) {
            return 0.00;
        } else {
            return roundToTwoDecimals(12 * insuranceRate3);
        }
    }

    private double calculateTotalPeriods(String insurancePremiums, double totalPeriod1, double totalPeriod2,
                                         double totalPeriod3) {
        if ("1".equals(insurancePremiums)) {
            // Suma de todos los periodos
            return roundToTwoDecimals(totalPeriod1 + totalPeriod2 + totalPeriod3);
        } else {
            // Lógica para cuando insurancePremiums es "0"
            if (totalPeriod1 != 0.00) {
                return roundToTwoDecimals(totalPeriod1);
            } else if (totalPeriod2 != 0.00) {
                return roundToTwoDecimals(totalPeriod2);
            } else {
                return roundToTwoDecimals(totalPeriod3);
            }
        }
    }

    private double roundToTwoDecimals(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void add(EquipmentInsuranceControlModel model) throws Exception {

        this.processQueue.getProps();
        ReadFilesConfig readConfig = null;
        long startTimeQueue = 0;

        try {
            readConfig = new ReadFilesConfig();
            String trama = generateTrama(model);

            this.processQueue.processTrama(readConfig, startTimeQueue, trama);

            EquipmentInsuranceControlEntity entity = new EquipmentInsuranceControlEntity();
            entity.setId(-1L);
            entity.setTransactionCode(model.getTransactionCode());
            entity.setUserAs(model.getUserAs());
            entity.setDateConsultation(model.getDateConsultation());
            entity.setCustomerAccount(model.getCustomerAccount());
            entity.setServiceAccount(model.getServiceAccount());
            entity.setBillingAccount(model.getBillingAccount());
            entity.setPhoneNumber(model.getPhoneNumber());
            entity.setEquipmentModel(model.getEquipmentModel());
            entity.setEsn(model.getEsn());
            entity.setOriginAs(model.getOriginAs());
            entity.setInventoryTypeAs(model.getInventoryTypeAs());
            entity.setOriginTypeAs(model.getOriginTypeAs());
            entity.setDateContract(model.getDateContract());
            entity.setStartDate(model.getStartDate());
            entity.setEndDate(model.getEndDate());
            entity.setDateInclusion(model.getDateInclusion());
            entity.setInsuranceRate(model.getInsuranceRate());
            entity.setPeriod(model.getPeriod());
            entity.setInsuranceRate2(model.getInsuranceRate2());
            entity.setPeriod2(model.getPeriod2());
            entity.setInsuranceRate3(model.getInsuranceRate3());
            entity.setPeriod3(model.getPeriod3());
            entity.setInsuranceStatus(1L);
            entity.setSubscriber(model.getSubscriber());
            entity.setTrama(trama);

            this.safeControlEquipmentRepository.save(entity);
        } catch (BadRequestException e) {

            throw e;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void update(Long id, EquipmentInsuranceControlModel model) {

        EquipmentInsuranceControlEntity entity = this.safeControlEquipmentRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

        entity.setTransactionCode(model.getTransactionCode());
        entity.setUserAs(model.getUserAs());
        entity.setDateConsultation(model.getDateConsultation());
        entity.setCustomerAccount(model.getCustomerAccount());
        entity.setServiceAccount(model.getServiceAccount());
        entity.setBillingAccount(model.getBillingAccount());
        entity.setPhoneNumber(model.getPhoneNumber());
        entity.setEquipmentModel(model.getEquipmentModel());
        entity.setEsn(model.getEsn());
        entity.setOriginAs(model.getOriginAs());
        entity.setInventoryTypeAs(model.getInventoryTypeAs());
        entity.setOriginTypeAs(model.getOriginTypeAs());
        entity.setDateContract(model.getDateContract());
        entity.setStartDate(model.getStartDate());
        entity.setEndDate(model.getEndDate());
        entity.setDateInclusion(model.getDateInclusion());
        entity.setInsuranceRate(model.getInsuranceRate());
        entity.setPeriod(model.getPeriod());
        entity.setInsuranceRate2(model.getInsuranceRate2());
        entity.setPeriod2(model.getPeriod2());
        entity.setInsuranceRate3(model.getInsuranceRate3());
        entity.setPeriod3(model.getPeriod3());
        entity.setInsuranceStatus(model.getInsuranceStatus());
        entity.setSubscriber(model.getSubscriber());
        this.safeControlEquipmentRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        EquipmentInsuranceControlEntity entity = this.safeControlEquipmentRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

        this.safeControlEquipmentRepository.delete(entity);
    }

    public String generateTrama(EquipmentInsuranceControlModel model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        StringBuilder marco = new StringBuilder("SEGUROS");

        if (model.getTransactionCode().equalsIgnoreCase("SEG62")) {
            marco.append("2");
        } else if (model.getTransactionCode().equalsIgnoreCase("SEG63")) {
            marco.append("3");
        }

        marco.append("|SERIE=").append(model.getEsn());
        marco.append("|MODELO=").append(model.getEquipmentModel());
        marco.append("|SUSCRIPTOR=").append(model.getSubscriber());
        marco.append("|TELEFONO=").append(model.getPhoneNumber());
        marco.append("|FECINICIO=").append(model.getStartDate().format(formatter));
        marco.append("|FECFIN=").append(model.getEndDate().format(formatter));
        marco.append("|");

        return marco.toString();
    }

}
