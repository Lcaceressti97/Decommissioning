package hn.com.tigo.comodatos.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.comodatos.entities.CancellationPermissionEntity;
import hn.com.tigo.comodatos.models.CancellationPermissionModel;
import hn.com.tigo.comodatos.repositories.ICancellationPermissionRepository;
import hn.com.tigo.comodatos.services.interfaces.ICancellationPermissionService;

@Service
public class CancellationPermissionServiceImpl implements ICancellationPermissionService{

	// Props
	private final ICancellationPermissionRepository cancellationPermissionRepository;
	
	public CancellationPermissionServiceImpl(ICancellationPermissionRepository cancellationPermissionRepository) {
		super();
		this.cancellationPermissionRepository = cancellationPermissionRepository;
		
	}
	
	@Override
	public CancellationPermissionModel findById(Long id) {
		
		CancellationPermissionEntity entity = this.cancellationPermissionRepository.findById(id).orElse(null);
		if(entity==null) {
			throw new BadRequestException(String.format("Error update CancellationPermission, Record with id %s is not valid", id));
		}
		
		CancellationPermissionModel model = entity.entityToModel();
		
		return model;
	}

	@Override
	public CancellationPermissionModel findByUserName(String userName) {
		
		
		
		List<CancellationPermissionEntity> entity = this.cancellationPermissionRepository.findByUserName(userName);
		
		if(entity.isEmpty()) {
			throw new BadRequestException(String.format("Error Cancellation Permission, Record with userName %s is not valid", userName));
		}
		
		CancellationPermissionModel model = entity.get(0).entityToModel();
		
		return model;
	}

	@Override
	public void add(CancellationPermissionModel model) {
		
		List<CancellationPermissionEntity> validateEntity = this.cancellationPermissionRepository.findByUserName(model.getUserName());
		
		if(validateEntity.isEmpty()) {
			CancellationPermissionEntity entity = new CancellationPermissionEntity();
			
			entity.setId(-1L);
			entity.setUserName(model.getUserName());
			entity.setPermitStatus(model.getPermitStatus());
			entity.setCreated(LocalDateTime.now());
			
			this.cancellationPermissionRepository.save(entity);
		}else {
			throw new BadRequestException("Error create CancellationPermission");
		}
		

		
	}

	@Override
	public void update(Long id, CancellationPermissionModel model) {
		
		CancellationPermissionEntity entity = this.cancellationPermissionRepository.findById(id).orElse(null);
		if(entity==null) {
			throw new BadRequestException(String.format("Error update CancellationPermission, Record with id %s is not valid", id));
		}
		
		entity.setUserName(model.getUserName());
		entity.setPermitStatus(model.getPermitStatus());
		
		this.cancellationPermissionRepository.save(entity);
	}

}
