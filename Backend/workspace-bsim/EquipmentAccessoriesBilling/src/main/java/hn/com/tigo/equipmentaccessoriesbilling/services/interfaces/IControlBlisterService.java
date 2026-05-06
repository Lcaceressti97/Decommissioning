package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import hn.com.tigo.equipmentaccessoriesbilling.models.ControlBlisterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IControlBlisterService {

    Page<ControlBlisterModel> getAllControlBlister(Pageable pageable);

    ControlBlisterModel getControlBlisterById(Long id);

    void addControlBlister(ControlBlisterModel model);

    void updateControlBlister(Long id, ControlBlisterModel model);

    void deleteControlBlister(Long id);
}
