package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import hn.com.tigo.equipmentaccessoriesbilling.models.GenerationReportInvModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IGenerationReportInvService {

    Page<GenerationReportInvModel> getAll(Pageable pageable);

    GenerationReportInvModel getById(Long id);

    String generateReport();

    void delete(Long id);
}
