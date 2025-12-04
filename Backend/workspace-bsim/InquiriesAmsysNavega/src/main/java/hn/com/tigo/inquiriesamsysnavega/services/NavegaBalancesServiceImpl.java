package hn.com.tigo.inquiriesamsysnavega.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import hn.com.tigo.inquiriesamsysnavega.entities.NavegaBalancesEntity;
import hn.com.tigo.inquiriesamsysnavega.models.NavegaBalancesModel;
import hn.com.tigo.inquiriesamsysnavega.models.TableNameModel;
import hn.com.tigo.inquiriesamsysnavega.repositories.NavegaBalancesRepository;
import hn.com.tigo.inquiriesamsysnavega.services.interfaces.NavegaBalancesService;
import hn.com.tigo.inquiriesamsysnavega.utils.Constants;

@Service
public class NavegaBalancesServiceImpl implements NavegaBalancesService {

	private final NavegaBalancesRepository navegaBalancesRepository;
	private final JdbcTemplate jdbcTemplate;

	public NavegaBalancesServiceImpl(JdbcTemplate jdbcTemplate, NavegaBalancesRepository navegaBalancesRepository) {
		this.navegaBalancesRepository = navegaBalancesRepository;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<NavegaBalancesModel> getAllNavegaBalances() {
		List<NavegaBalancesEntity> entities = navegaBalancesRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return entities.stream().map(NavegaBalancesEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<TableNameModel> getTableNamesQueryNavega() {
		List<String> tableNames = navegaBalancesRepository.getAllTableNameNavega();
		List<TableNameModel> result = new ArrayList<>();

		// Inicializar un contador para el ID
		long idCounter = 1;

		for (String tableName : tableNames) {
			TableNameModel model = new TableNameModel();
			model.setTableName(tableName);

			// Establecer el ID con el valor del contador
			model.setId(idCounter++);

			// Lógica para establecer la descripción
			String description;
			if (tableName.startsWith("CN_NAVEGA_BALANCES")) {
				String formattedDate = extractDateFromTableName(tableName);
				description = "Registros " + formattedDate;
			} else {
				description = "Descripción no disponible";
			}

			model.setDescription(description);
			result.add(model);
		}

		return result;
	}

	@Override
	public List<NavegaBalancesModel> getTableDataQueryNavega(String tableName) {
		String sql = String.format("SELECT * FROM %s", tableName);
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			NavegaBalancesModel model = new NavegaBalancesModel();
			model.setId(rs.getLong("ID"));
			model.setCustomerName(rs.getString("CUSTOMER_NAME"));
			model.setCustomerNo(rs.getString("CUSTOMER_NO"));
			model.setCustomerCode(rs.getString("CUSTOMER_CODE"));
			model.setProduct(rs.getString("PRODUCT"));
			model.setEbsAccount(rs.getString("EBS_ACCOUNT"));
			model.setIdOrganization(rs.getLong("ID_ORGANIZATION"));
			model.setOrganizationName(rs.getString("ORGANIZATION_NAME"));
			model.setCurrency(rs.getString("CURRENCY"));
			model.setBalance(rs.getDouble("BALANCE"));
			model.setClosingDate(rs.getDate("CLOSING_DATE"));
			return model;
		});
	}

	// Método para extraer la fecha de la tabla
	private String extractDateFromTableName(String tableName) {
		// Si el nombre de la tabla tiene el formato AN_AMNET_BALANCES_YYYYMMDD
		if (tableName.matches("CN_NAVEGA_BALANCES_\\d{8}")) {
			// Extraer la fecha de la tabla
			String dateString = tableName.substring(tableName.length() - 8);
			// Formatear la fecha al estilo 'DD/MM/YYYY'
			return dateString.substring(6, 8) + "/" + dateString.substring(4, 6) + "/" + dateString.substring(0, 4);
		} else {
			// Si el nombre de la tabla no contiene la fecha
			return "Actuales";
		}
	}

	@Override
	public NavegaBalancesModel getBalanceByEbsAccount(String ebsAccount) {
		NavegaBalancesEntity entity = this.navegaBalancesRepository.getBalanceByEbsAccount(ebsAccount);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.RECORD_NOT_FOUND_EBSACCOUT, ebsAccount));
		return entity.entityToModel();
	}

}
