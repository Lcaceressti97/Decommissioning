package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.TypeUserEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.TypeUserModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.ITypeUserRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ITypeUserService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class TypeUserServiceImpl implements ITypeUserService {

	private final ITypeUserRepository typeUserRepository;

	public TypeUserServiceImpl(ITypeUserRepository typeUserRepository) {
		super();
		this.typeUserRepository = typeUserRepository;
	}

	@Override
	public List<TypeUserModel> getAllTypeUser() {
		List<TypeUserEntity> entities = this.typeUserRepository.findAll();
		return entities.stream().map(TypeUserEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public TypeUserModel getTypeUserById(Long id) {
		TypeUserEntity entity = this.typeUserRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));
		return entity.entityToModel();
	}

	@Override
	public void addTypeUser(TypeUserModel model) {
		try {
			TypeUserEntity entity = new TypeUserEntity();
			entity.setId(-1L);
			entity.setTypeUser(model.getTypeUser());
			entity.setDescription(model.getDescription());
			entity.setStatus(0L);
			entity.setCreated(LocalDateTime.now());
			this.typeUserRepository.save(entity);
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updateTypeUser(Long id, TypeUserModel model) {
		try {
			TypeUserEntity entity = this.typeUserRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));

			entity.setTypeUser(model.getTypeUser());
			entity.setDescription(model.getDescription());
			entity.setStatus(1L);
			entity.setCreated(LocalDateTime.now());
			this.typeUserRepository.save(entity);
		} catch (BadRequestException e) {

			throw e;
		} catch (Exception e) {

			throw e;
		}

	}

	@Override
	public void deleteTypeUser(Long id) {
		TypeUserEntity entity = this.typeUserRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.typeUserRepository.delete(entity);

	}

}
