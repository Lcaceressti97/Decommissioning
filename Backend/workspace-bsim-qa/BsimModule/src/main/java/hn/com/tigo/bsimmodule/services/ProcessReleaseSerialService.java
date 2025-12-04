package hn.com.tigo.bsimmodule.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import hn.com.tigo.bsimmodule.entities.ReleaseSerialDetailEntity;
import hn.com.tigo.bsimmodule.entities.ReleaseSerialLogEntity;
import hn.com.tigo.bsimmodule.models.AuthenticationBsimResponse;
import hn.com.tigo.bsimmodule.models.ReleaseSerialBsimModel;
import hn.com.tigo.bsimmodule.models.ReleaseSerialBsimRequest;
import hn.com.tigo.bsimmodule.repositories.IReleaseSerialDetailRepository;
import hn.com.tigo.bsimmodule.repositories.IReleaseSerialLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProcessReleaseSerialService {

    private final ReleaseSerialBsimService releaseSerialBsimService;
    private final IReleaseSerialLogRepository releaseSerialLogRepository;
    private final IReleaseSerialDetailRepository releaseSerialDetailRepository;
    private final AuthenticationBsimService authenticationService;

    public ProcessReleaseSerialService(
            ReleaseSerialBsimService releaseSerialBsimService,
            IReleaseSerialLogRepository releaseSerialLogRepository,
            IReleaseSerialDetailRepository releaseSerialDetailRepository, AuthenticationBsimService authenticationService
    ) {
        this.releaseSerialBsimService = releaseSerialBsimService;
        this.releaseSerialLogRepository = releaseSerialLogRepository;
        this.releaseSerialDetailRepository = releaseSerialDetailRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public Long releaseSingleSeries(ReleaseSerialBsimRequest request) {

        ReleaseSerialLogEntity logEntity = new ReleaseSerialLogEntity();
        logEntity.setReleaseType(request.getReleaseType());
        logEntity.setTotalSeries(request.getSerialNumberList().size());
        logEntity.setFileName(null);
        logEntity.setStatus("P");
        logEntity.setCreatedAt(LocalDateTime.now());
        logEntity.setSuccessfulReleases(0);
        logEntity.setFailedReleases(0);

        // Guardar primero el log para obtener su ID
        logEntity = releaseSerialLogRepository.save(logEntity);

        int successfulCount = 0;
        int failedCount = 0;

        // Usar el tipo correcto para iterar sobre la lista de números de serie
        for (ReleaseSerialBsimRequest.SerialNumberList serialNumberEntry : request.getSerialNumberList()) {
            ReleaseSerialDetailEntity detailEntity = new ReleaseSerialDetailEntity();
            detailEntity.setReleaseSerialLog(logEntity);
            detailEntity.setInventoryType(request.getInventoryType());
            detailEntity.setItemCode(request.getItemCode());
            detailEntity.setWarehouseCode(request.getWarehouseCode());
            detailEntity.setSubWarehouseCode(request.getSubWarehouseCode());
            detailEntity.setSerialNumber(serialNumberEntry.getSerialNumber());
            detailEntity.setCreatedAt(LocalDateTime.now());

            try {
                AuthenticationBsimResponse accessToken = authenticationService.getAccessToken();
                ReleaseSerialBsimModel response = releaseSerialBsimService.releaseSeries(accessToken.getAccess_token(), request);

                if (response.getResult_code().equals("INV000")) {
                    detailEntity.setStatus("C");
                    detailEntity.setResultMessage(response.getResult_message());
                    successfulCount++;
                } else {
                    detailEntity.setStatus("E");
                    detailEntity.setResultMessage(response.getResult_message());
                    failedCount++;
                }

            } catch (Exception e) {
                detailEntity.setStatus("E");
                detailEntity.setResultMessage(e.getMessage());
                failedCount++;
            }

            // Guardar el detalle
            releaseSerialDetailRepository.save(detailEntity);
            logEntity.getDetails().add(detailEntity);
        }

        // Actualizar el log con los resultados finales
        logEntity.setSuccessfulReleases(successfulCount);
        logEntity.setFailedReleases(failedCount);
        logEntity.setStatus(failedCount > 0 ? "E" : "C");

        // Guardar el log actualizado
        logEntity = releaseSerialLogRepository.save(logEntity);

        return logEntity.getId();
    }

    @Transactional
    public Long releaseSeriesFromFile(
            MultipartFile file,
            String releaseType) throws IOException, CsvException {

        // Crear el log inicial
        ReleaseSerialLogEntity logEntity = new ReleaseSerialLogEntity();
        logEntity.setReleaseType(releaseType);
        logEntity.setFileName(file.getOriginalFilename());
        logEntity.setStatus("P");
        logEntity.setCreatedAt(LocalDateTime.now());
        logEntity.setSuccessfulReleases(0);
        logEntity.setFailedReleases(0);

        // Leer el archivo CSV
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows = reader.readAll();
            // Excluir la fila de encabezado
            rows.remove(0);
            logEntity.setTotalSeries(rows.size());

            // Guardar el log para obtener su ID
            logEntity = releaseSerialLogRepository.save(logEntity);

            int successfulCount = 0;
            int failedCount = 0;

            // Procesar cada fila del CSV
            for (String[] row : rows) {
                String inventoryType = row[0];
                String itemCode = row[1];
                String warehouseCode = row[2];
                String subWarehouseCode = row[3];
                String series = row[4];

                ReleaseSerialDetailEntity detailEntity = new ReleaseSerialDetailEntity();
                detailEntity.setReleaseSerialLog(logEntity);
                detailEntity.setInventoryType(inventoryType);
                detailEntity.setItemCode(itemCode);
                detailEntity.setWarehouseCode(warehouseCode);
                detailEntity.setSubWarehouseCode(subWarehouseCode);
                detailEntity.setSerialNumber(series);
                detailEntity.setCreatedAt(LocalDateTime.now());

                try {
                    // Crear request para cada serie
                    ReleaseSerialBsimRequest request = new ReleaseSerialBsimRequest();
                    request.setReleaseType(releaseType);
                    request.setInventoryType(inventoryType);
                    request.setItemCode(itemCode);
                    request.setWarehouseCode(warehouseCode);
                    request.setSubWarehouseCode(subWarehouseCode);

                    ReleaseSerialBsimRequest.SerialNumberList serialNumber = new ReleaseSerialBsimRequest.SerialNumberList();
                    serialNumber.setSerialNumber(series);
                    List<ReleaseSerialBsimRequest.SerialNumberList> serialList = new ArrayList<>();
                    serialList.add(serialNumber);
                    request.setSerialNumberList(serialList);

                    AuthenticationBsimResponse accessToken = authenticationService.getAccessToken();
                    ReleaseSerialBsimModel response = releaseSerialBsimService.releaseSeries(
                            accessToken.getAccess_token(), request);

                    if (response.getResult_code().equals("INV000")) {
                        detailEntity.setStatus("C");
                        detailEntity.setResultMessage(response.getResult_message());
                        successfulCount++;
                    } else {
                        detailEntity.setStatus("E");
                        detailEntity.setResultMessage(response.getResult_message());
                        failedCount++;
                    }

                } catch (Exception e) {
                    detailEntity.setStatus("E");
                    detailEntity.setResultMessage(e.getMessage());
                    failedCount++;
                }

                // Guardar el detalle
                releaseSerialDetailRepository.save(detailEntity);
                logEntity.getDetails().add(detailEntity);
            }

            // Actualizar el log con los resultados finales
            logEntity.setSuccessfulReleases(successfulCount);
            logEntity.setFailedReleases(failedCount);
            logEntity.setStatus(failedCount > 0 ? "E" : "C");
            logEntity = releaseSerialLogRepository.save(logEntity);
        }

        return logEntity.getId();
    }
}
