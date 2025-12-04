package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlUserPermissionsEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.TypeUserEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.UserBranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.BranchOfficesModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IBranchOfficesRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IControlUserPermissionsRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.ITypeUserRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IUserBranchOfficesRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBranchOfficesService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class BranchOfficesServiceImpl implements IBranchOfficesService {

	private final IBranchOfficesRepository branchOfficesRepository;
	private final ILogsService logsService;
	private final IControlUserPermissionsRepository controlUserPermissionsRepository;
	private final IConfigParametersService configParametersService;
	private final ITypeUserRepository typeUserRepository;
	private final IUserBranchOfficesRepository userBranchOfficesRepository;

	public BranchOfficesServiceImpl(IBranchOfficesRepository branchOfficesRepository, ILogsService logsService,
			IControlUserPermissionsRepository controlUserPermissionsRepository,
			IConfigParametersService configParametersService, ITypeUserRepository typeUserRepository,
			IUserBranchOfficesRepository userBranchOfficesRepository) {
		super();
		this.branchOfficesRepository = branchOfficesRepository;
		this.logsService = logsService;
		this.controlUserPermissionsRepository = controlUserPermissionsRepository;
		this.configParametersService = configParametersService;
		this.typeUserRepository = typeUserRepository;
		this.userBranchOfficesRepository = userBranchOfficesRepository;
	}

	@Override
	public Page<BranchOfficesModel> getAllBranchOffices(Pageable pageable) {
		Pageable descendingPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by("id").descending());

		Page<BranchOfficesEntity> entities = this.branchOfficesRepository.findAll(descendingPageable);
		return entities.map(BranchOfficesEntity::entityToModel);
	}

	@Override
	public List<BranchOfficesModel> getBranchOfficesReport(String seller) {
		ControlUserPermissionsEntity controlUserPermissionsEntity = controlUserPermissionsRepository
				.findByUserName(seller.toUpperCase());
		if (controlUserPermissionsEntity == null)
			throw new BadRequestException(String.format(Constants.ERROR_USER_NOT_EXISTS, seller));

		TypeUserEntity typeUserEntity = this.typeUserRepository.findById(controlUserPermissionsEntity.getTypeUser())
				.orElse(null);
		if (typeUserEntity == null)
			throw new BadRequestException(String.format(Constants.ERROR_USER_TYPE));

		List<UserBranchOfficesEntity> userBranchOfficesEntities = this.userBranchOfficesRepository
				.findByIdUserActivated(controlUserPermissionsEntity.getIdUser());
		if (userBranchOfficesEntities.isEmpty())
			throw new BadRequestException(String.format(Constants.ERROR_USER_BRANCHOFFICE));

		List<BranchOfficesEntity> branchOfficesEntities = new ArrayList<>();

		for (UserBranchOfficesEntity userBranchOfficesEntity : userBranchOfficesEntities) {
			Long idBranchOffices = userBranchOfficesEntity.getIdBranchOffices();
			BranchOfficesEntity branchOfficesEntity = this.branchOfficesRepository.findById(idBranchOffices)
					.orElse(null);
			if (branchOfficesEntity != null) {
				branchOfficesEntities.add(branchOfficesEntity);
			}
		}

		List<ConfigParametersModel> listTypeUser = this.configParametersService.getByIdApplication(1003L);
		Map<String, List<String>> parametersTypeUser = new HashMap<>();
		Map<String, String> paramsTypeUser = new HashMap<>();

		for (ConfigParametersModel parameter : listTypeUser) {
			String paramName = parameter.getParameterName();
			String paramValue = parameter.getParameterValue();

			if (!parametersTypeUser.containsKey(paramName)) {
				parametersTypeUser.put(paramName, new ArrayList<>());
			}

			parametersTypeUser.get(paramName).add(paramValue);
			paramsTypeUser.put(paramName, paramValue);
		}

		List<String> userSeeAll = parametersTypeUser.get("SEE_ALL_REPORT");
		List<String> userSeeOnlyTheir = parametersTypeUser.get("SEE_ONLY_YOUR_BRANCH");

		List<BranchOfficesModel> branchOfficesModels = new ArrayList<>();

		if (userSeeAll != null && userSeeAll.contains(typeUserEntity.getTypeUser())) {
			List<BranchOfficesEntity> allBranchOfficesEntities = branchOfficesRepository
					.findAll(Sort.by(Sort.Direction.DESC, "created"));
			for (BranchOfficesEntity branchOfficesEntity : allBranchOfficesEntities) {
				branchOfficesModels.add(branchOfficesEntity.entityToModel());
			}
		} else if (userSeeOnlyTheir != null && userSeeOnlyTheir.contains(typeUserEntity.getTypeUser())) {
			for (BranchOfficesEntity branchOfficesEntity : branchOfficesEntities) {
				branchOfficesModels.add(branchOfficesEntity.entityToModel());
			}
		} else {
			throw new BadRequestException(String.format("User does not have permission to view invoices"));
		}
		return branchOfficesModels;
	}

	/**
	 * Método que se utiliza para consumir el addVoucher
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public BranchOfficesModel findById(Long id) {
		BranchOfficesEntity entity = this.branchOfficesRepository.findById(id).orElse(null);
		if (entity == null) {
			return null;
		}
		return entity.entityToModel();
	}

	@Override
	public BranchOfficesModel getById(Long id) {
		BranchOfficesEntity entity = this.branchOfficesRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));
		return entity.entityToModel();
	}

	@Override
	public void add(BranchOfficesModel model) {
		long startTime = System.currentTimeMillis();

		try {

			if (branchOfficesRepository.existsByWineryCode(model.getWineryCode())) {
				throw new BadRequestException(String.format(Constants.ERROR_WINERY_CODE, model.getWineryCode()));
			}

			BranchOfficesEntity entity = new BranchOfficesEntity();

			entity.setId(-1L);
			entity.setIdPoint(model.getIdPoint());
			entity.setName(model.getName());
			entity.setAddress(model.getAddress());
			entity.setPhone(model.getPhone());
			entity.setRtn(model.getRtn());
			entity.setFax(model.getFax());
			entity.setPbx(model.getPbx());
			entity.setEmail(model.getEmail());
			entity.setAcctCode(model.getAcctCode());
			entity.setFictitiousNumber(model.getFictitiousNumber());
			entity.setIdCompany(model.getIdCompany());
			entity.setIdSystem(model.getIdSystem());
			entity.setWineryCode(model.getWineryCode());
			entity.setWineryName(model.getWineryName());
			entity.setTerritory(model.getTerritory());
			entity.setStatus(1L);
			entity.setCreated(LocalDateTime.now());
			entity.setWareHouseManager(model.getWareHouseManager());

			this.branchOfficesRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(11, model.getIdSystem(),
					"Error occurred while adding Branch Offices: " + e.getMessage(), null, duration);
			throw e;

		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(11, model.getIdSystem(),
					"Error occurred while adding Branch Offices: " + e.getMessage(), null, duration);
			throw e;
		}
	}

	@Override
	public void update(Long id, BranchOfficesModel model) {
		long startTime = System.currentTimeMillis();

		try {
			BranchOfficesEntity entity = this.branchOfficesRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));

		      if (branchOfficesRepository.existsByWineryCodeAndIdNot(model.getWineryCode(), id)) {
		            throw new BadRequestException(String.format(Constants.ERROR_WINERY_CODE, model.getWineryCode()));
		        }


			entity.setIdPoint(model.getIdPoint());
			entity.setName(model.getName());
			entity.setAddress(model.getAddress());
			entity.setPhone(model.getPhone());
			entity.setRtn(model.getRtn());
			entity.setFax(model.getFax());
			entity.setPbx(model.getPbx());
			entity.setEmail(model.getEmail());
			entity.setAcctCode(model.getAcctCode());
			entity.setFictitiousNumber(model.getFictitiousNumber());
			entity.setIdCompany(model.getIdCompany());
			entity.setIdSystem(model.getIdSystem());
			entity.setWineryCode(model.getWineryCode());
			entity.setWineryName(model.getWineryName());
			entity.setTerritory(model.getTerritory());
			entity.setStatus(1L);
			entity.setCreated(LocalDateTime.now());
			entity.setWareHouseManager(model.getWareHouseManager());
			this.branchOfficesRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(11, id, "Error occurred while adding Branch Offices: " + e.getMessage(), null,
					duration);
			throw e;

		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(11, id, "Error occurred while adding Branch Offices: " + e.getMessage(), null,
					duration);
			throw e;
		}
	}

	@Override
	public void delete(Long id) {
		BranchOfficesEntity entity = this.branchOfficesRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.branchOfficesRepository.delete(entity);

	}

}
