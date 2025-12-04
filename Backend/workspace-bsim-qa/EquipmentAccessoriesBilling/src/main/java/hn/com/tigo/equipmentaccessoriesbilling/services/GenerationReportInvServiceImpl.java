package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hn.com.tigo.equipmentaccessoriesbilling.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.GenerationReportInvModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IBillingRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IGenerationReportInvRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.ISimcardDetailRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IGenerationReportInvService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;
import hn.com.tigo.equipmentaccessoriesbilling.exceptions.BadRequestException;

@Service
public class GenerationReportInvServiceImpl implements IGenerationReportInvService {

    private final IGenerationReportInvRepository generationReportInvRepository;
    private final IBillingRepository billingRepository;
    private final IConfigParametersService configParametersService;
    private final ISimcardDetailRepository simcardDetailRepository;

    public GenerationReportInvServiceImpl(IGenerationReportInvRepository generationReportInvRepository,
                                          IBillingRepository billingRepository, IConfigParametersService configParametersService,
                                          ISimcardDetailRepository simcardDetailRepository) {
        this.generationReportInvRepository = generationReportInvRepository;
        this.billingRepository = billingRepository;
        this.configParametersService = configParametersService;
        this.simcardDetailRepository = simcardDetailRepository;

    }

    @Override
    public Page<GenerationReportInvModel> getAll(Pageable pageable) {
        Pageable descendingPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("id").descending());

        Page<GenerationReportInvEntity> entities = this.generationReportInvRepository
                .findAll(descendingPageable);
        return entities.map(GenerationReportInvEntity::entityToModel);
    }

    @Override
    public GenerationReportInvModel getById(Long id) {
        GenerationReportInvEntity entity = this.generationReportInvRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));
        return entity.entityToModel();
    }

    @Override
    @Transactional
    public String generateReport() {
        List<ConfigParametersModel> listAuthentication = this.configParametersService.getByIdApplication(4443L);
        Map<String, List<String>> parametersBsim = new HashMap<>();
        Map<String, String> paramsAuthentication = new HashMap<>();

        for (ConfigParametersModel parameter : listAuthentication) {
            String paramName = parameter.getParameterName();
            String paramValue = parameter.getParameterValue();

            if (!parametersBsim.containsKey(paramName)) {
                parametersBsim.put(paramName, new ArrayList<>());
            }

            parametersBsim.get(paramName).add(paramValue);
            paramsAuthentication.put(paramName, paramValue);
        }

        String reportInv = paramsAuthentication.get("REPORT_INV");

        List<BillingEntity> bills = billingRepository.getInvoicesOfTheDayAndStatus(2L);

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String fileName = "REPORT_" + date + ".txt";
        byte[] fileContent = new byte[0];

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            for (BillingEntity bill : bills) {
                // Recorremos todos los detalles de la factura
                for (InvoiceDetailEntity invoiceDetail : bill.getInvoiceDetails()) {
                    String serieOrBoxNumber = invoiceDetail.getSerieOrBoxNumber();

                    // Validar si serieOrBoxNumber no está presente
                    if (serieOrBoxNumber == null || serieOrBoxNumber.isEmpty()) {
                        continue; // Omitir este invoiceDetail si no hay serieOrBoxNumber
                    }

                    SimcardDetailEntity simcardDetail = this.simcardDetailRepository.findByImsiOrIcc(2, serieOrBoxNumber);
                    String imsi = (simcardDetail != null) ? simcardDetail.getImsi() : "";


                    bos.write(reportInv.getBytes());
                    bos.write((bill.getInvoiceType() + " " + bill.getPrimaryIdentity() + "|||||").getBytes());

                    if (simcardDetail == null) {
                        // Si simcardDetail es null, escribimos serieOrBoxNumber en ambas posiciones
                        bos.write((serieOrBoxNumber + "|").getBytes());
                        bos.write((serieOrBoxNumber + "||||||").getBytes());
                    } else {
                        // Si simcardDetail no es null, escribimos serieOrBoxNumber y imsi
                        bos.write((serieOrBoxNumber + "|").getBytes());
                        bos.write((imsi + "||||||").getBytes());
                    }

                    bos.write("\n".getBytes());
                }
            }
            fileContent = bos.toByteArray();
        } catch (IOException e) {
            e.getMessage();
        }

        String base64File = Base64.getEncoder().encodeToString(fileContent);

        GenerationReportInvEntity report = new GenerationReportInvEntity();
        report.setId(-1L);
        report.setFileName(fileName);
        report.setDescription("Reporte INV " + date);
        report.setReportInv(base64File);
        report.setStatus(1L);
        report.setCreated(LocalDateTime.now());
        generationReportInvRepository.save(report);

        return base64File;
    }

    @Override
    public void delete(Long id) {
        GenerationReportInvEntity entity = this.generationReportInvRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

        this.generationReportInvRepository.delete(entity);

    }

}
