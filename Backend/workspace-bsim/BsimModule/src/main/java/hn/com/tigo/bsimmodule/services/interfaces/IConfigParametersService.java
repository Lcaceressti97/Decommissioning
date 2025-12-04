package hn.com.tigo.bsimmodule.services.interfaces;

import hn.com.tigo.bsimmodule.models.ConfigParametersModel;

import java.util.List;

public interface IConfigParametersService {

    List<ConfigParametersModel> getAll();

    List<ConfigParametersModel> getByIdApplication(Long idApplication);

}
