package hn.com.tigo.equipmentinsurance.repositories.bsim;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.bsim.InvWarehouseByLocationEntity;



@Repository
public class IWarehouseMaintenanceDaoImpl implements IWarehouseMaintenanceDao {

	private final JdbcTemplate jdbcTemplate;
	

	public IWarehouseMaintenanceDaoImpl(@Value("${spring.bsim.datasource.url}") String url,
			@Value("${spring.bsim.datasource.username}") String username,
			@Value("${spring.bsim.datasource.password}") String password,
			@Value("${spring.bsim.datasource.driver-class-name}") String driverClassName) {

		DataSource dataSource = createDataSource(url, username, password, driverClassName);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
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


}
