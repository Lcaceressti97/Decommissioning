package hn.com.tigo.equipmentaccessoriesbilling.repositories.bsim;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlUserPermissionsEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.TypeUserEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.UserBranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.bsim.InvWarehouseBsimEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.bsim.InvWarehouseByLocationEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IBranchOfficesRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IControlUserPermissionsRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.ITypeUserRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IUserBranchOfficesRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IUsersRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Repository
public class IWarehouseMaintenanceDaoImpl implements IWarehouseMaintenanceDao {

	private final JdbcTemplate jdbcTemplate;
	private final IControlUserPermissionsRepository controlUserPermissionsRepository;
	private final IConfigParametersService configParametersService;
	private final ITypeUserRepository typeUserRepository;
	private final IUserBranchOfficesRepository userBranchOfficesRepository;
	private final IBranchOfficesRepository branchOfficesRepository;
	private final IUsersRepository usersRepository;

	public IWarehouseMaintenanceDaoImpl(@Value("${spring.bsim.datasource.url}") String url,
			@Value("${spring.bsim.datasource.username}") String username,
			@Value("${spring.bsim.datasource.password}") String password,
			@Value("${spring.bsim.datasource.driver-class-name}") String driverClassName,
			IControlUserPermissionsRepository controlUserPermissionsRepository,
			IConfigParametersService configParametersService, ITypeUserRepository typeUserRepository,
			IUserBranchOfficesRepository userBranchOfficesRepository, IBranchOfficesRepository branchOfficesRepository,
			IUsersRepository usersRepository) {

		DataSource dataSource = createDataSource(url, username, password, driverClassName);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.controlUserPermissionsRepository = controlUserPermissionsRepository;
		this.configParametersService = configParametersService;
		this.typeUserRepository = typeUserRepository;
		this.userBranchOfficesRepository = userBranchOfficesRepository;
		this.branchOfficesRepository = branchOfficesRepository;
		this.usersRepository = usersRepository;

	}

	private DataSource createDataSource(String url, String username, String password, String driverClassName) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Override
	public List<InvWarehouseByLocationEntity> getAll() {
		String sql = "SELECT wbl.*, wt.NAME TYPE FROM STCINVENTORY.INV_WAREHOUSE_BY_LOCATION wbl INNER JOIN STCINVENTORY.INV_WAREHOUSE_TYPES wt ON wbl.WAREHOUSE_TYPE_ID = wt.ID";
		List<InvWarehouseByLocationEntity> list = new ArrayList<>();

		List<Map<String, Object>> executedQuery = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : executedQuery) {
			InvWarehouseByLocationEntity entity = new InvWarehouseByLocationEntity();
			Number id = (Number) row.get("ID");
			entity.setId(id != null ? id.longValue() : null);

			entity.setVersion((BigDecimal) row.get("VERSION"));
			entity.setCode(String.valueOf(row.get("CODE")));
			entity.setName(String.valueOf(row.get("NAME")));
			entity.setAddress(String.valueOf(row.get("ADDRESS")));
			entity.setResponsible(String.valueOf(row.get("RESPONSIBLE")));
			entity.setStatus(String.valueOf(row.get("STATUS")));
			entity.setWarehouseTypeId(String.valueOf(row.get("TYPE")));
			entity.setImmediateBoss(String.valueOf(row.get("IMMEDIATE_BOSS")));

			list.add(entity);
		}
		return list;
	}

	@Override
	public List<InvWarehouseByLocationEntity> findByCode(String code) {
		String sql = "SELECT wbl.*, wt.NAME TYPE FROM STCINVENTORY.INV_WAREHOUSE_BY_LOCATION wbl INNER JOIN STCINVENTORY.INV_WAREHOUSE_TYPES wt ON wbl.WAREHOUSE_TYPE_ID = wt.ID WHERE wbl.CODE = ?";
		List<InvWarehouseByLocationEntity> list = new ArrayList<>();

		List<Map<String, Object>> executedQuery = jdbcTemplate.queryForList(sql, code);
		for (Map<String, Object> row : executedQuery) {
			InvWarehouseByLocationEntity entity = new InvWarehouseByLocationEntity();
			Number id = (Number) row.get("ID");
			entity.setId(id != null ? id.longValue() : null);

			entity.setVersion((BigDecimal) row.get("VERSION"));
			entity.setCode(String.valueOf(row.get("CODE")));
			entity.setName(String.valueOf(row.get("NAME")));
			entity.setAddress(String.valueOf(row.get("ADDRESS")));
			entity.setResponsible(String.valueOf(row.get("RESPONSIBLE")));
			entity.setStatus(String.valueOf(row.get("STATUS")));
			entity.setWarehouseTypeId(String.valueOf(row.get("TYPE")));
			entity.setImmediateBoss(String.valueOf(row.get("IMMEDIATE_BOSS")));

			list.add(entity);
		}
		return list;
	}

	@Override
	public List<InvWarehouseByLocationEntity> getWarehouseRepot(String seller) {
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
		List<InvWarehouseByLocationEntity> list = new ArrayList<>();

		if (userSeeAll != null && userSeeAll.contains(typeUserEntity.getTypeUser().toString())) {
			list = this.getAll();
		} else if (userSeeOnlyTheir != null && userSeeOnlyTheir.contains(typeUserEntity.getTypeUser().toString())) {
			for (BranchOfficesEntity branchOfficesEntity : branchOfficesEntities) {
				list.addAll(this.findByCode(branchOfficesEntity.getWineryCode()));
			}
		} else {
			throw new BadRequestException(String.format("User does not have permission to view warehouse"));
		}
		return list;
	}

	@Override
	public String getWarehouseNameByCode(String code) {
		String sql = "SELECT wbl.CODE, wbl.NAME FROM STCINVENTORY.INV_WAREHOUSE_BY_LOCATION wbl WHERE wbl.CODE = ?";
		List<Map<String, Object>> executedQuery = jdbcTemplate.queryForList(sql, code);

		if (!executedQuery.isEmpty()) {
			Map<String, Object> row = executedQuery.get(0);
			return String.valueOf(row.get("CODE")) + " - " + String.valueOf(row.get("NAME"));
		}

		return "Not found";
	}

	@Override
	public List<InvWarehouseBsimEntity> getInvWarehouseBsimByUser(String userName) {

		// Obtener los códigos de bodega asociados al usuario
		List<String> wineryCodes = this.usersRepository.findWineryCodesByUserName(userName.toUpperCase());

		System.out.println(wineryCodes);
		// Si no hay códigos, retornar una lista vacía
		if (wineryCodes.isEmpty()) {
			return new ArrayList<>();
		}

		// Construir la lista de códigos para la consulta
		String codesInClause = wineryCodes.stream().map(code -> "'" + code + "'").collect(Collectors.joining(", "));

		String sql = "select b.id, b.code, b.name, b. address ,b.status, CASE WHEN name LIKE '%T1%' THEN 'T1' WHEN name LIKE '%T2%' THEN 'T2' WHEN name LIKE '%T3%' THEN 'T3' ELSE 'NO DEFINIDO' END AS ZONE FROM stcinventory.inv_warehouse_by_location b where b.code in ("
				+ codesInClause + ") and b.status = 'A'";
		List<InvWarehouseBsimEntity> list = new ArrayList<>();

		List<Map<String, Object>> executedQuery = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : executedQuery) {
			InvWarehouseBsimEntity entity = new InvWarehouseBsimEntity();
			Number id = (Number) row.get("ID");
			entity.setId(id != null ? id.longValue() : null);

			entity.setCode(String.valueOf(row.get("CODE")));
			entity.setName(String.valueOf(row.get("NAME")));
			entity.setAddress(String.valueOf(row.get("ADDRESS")));
			entity.setZone(String.valueOf(row.get("ZONE")));
			entity.setBusinessUnit("MOBILE");
			entity.setStatus(String.valueOf(row.get("STATUS")));

			// Obtener la lista de sucursales según el código de almacén
			List<BranchOfficesEntity> branchOfficesEntity = this.branchOfficesRepository
					.getBranchOfficesByWineryCode(String.valueOf(row.get("CODE")));

			// Si existe alguna sucursal asociada, establece el teléfono
			if (!branchOfficesEntity.isEmpty()) {
				entity.setPhone(branchOfficesEntity.get(0).getPhone());
			} else {
				entity.setPhone(null);
			}

			list.add(entity);
		}
		return list;
	}

}
