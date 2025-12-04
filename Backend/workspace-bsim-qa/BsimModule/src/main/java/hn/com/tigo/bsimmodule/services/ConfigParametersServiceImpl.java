package hn.com.tigo.bsimmodule.services;

import java.util.List;
import java.util.stream.Collectors;

import hn.com.tigo.bsimmodule.entities.ConfigParametersEntity;
import hn.com.tigo.bsimmodule.models.ConfigParametersModel;
import hn.com.tigo.bsimmodule.repositories.IConfigParametersRepository;
import hn.com.tigo.bsimmodule.services.interfaces.IConfigParametersService;
import hn.com.tigo.bsimmodule.utils.Constants;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;


@Service
public class ConfigParametersServiceImpl implements IConfigParametersService {

    private final IConfigParametersRepository configParametersRepository;

    public ConfigParametersServiceImpl(IConfigParametersRepository configParametersRepository) {
        this.configParametersRepository = configParametersRepository;
    }


    @Override
    public List<ConfigParametersModel> getAll() {
        List<ConfigParametersEntity> entities = this.configParametersRepository
                .findAll(Sort.by(Sort.Direction.DESC, "created"));
        return entities.stream().map(ConfigParametersEntity::entityToModel).collect(Collectors.toList());
    }

    @Override
    public List<ConfigParametersModel> getByIdApplication(Long idApplication) {
        List<ConfigParametersEntity> entities = configParametersRepository.findByIdApplication(idApplication);

        if (entities.isEmpty()) {
            throw new BadRequestException(String.format(Constants.ERROR_ID_APP, idApplication.toString()));
        }

        return entities.stream().map(ConfigParametersEntity::entityToModel).collect(Collectors.toList());
    }
}
