package hn.com.tigo.equipmentaccessoriesbilling.repositories.bsim;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.bsim.TypeInventoryEntity;

@Repository
public class TypeInventoryDaoImpl implements ITypeInventoryDao {

	private final JdbcTemplate jdbcTemplate;

	public TypeInventoryDaoImpl(@Value("${spring.bsim.datasource.url}") String url,
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
	public List<TypeInventoryEntity> getAll() {
		String sql = "SELECT * FROM STCINVENTORY.INV_TYPE_INVENTORY";
		List<TypeInventoryEntity> list = new ArrayList<>();

		List<Map<String, Object>> executedQuery = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : executedQuery) {
			TypeInventoryEntity entity = new TypeInventoryEntity();
			Number id = (Number) row.get("ID");
			entity.setId(id != null ? id.longValue() : null);

			entity.setVersion((BigDecimal) row.get("VERSION"));
			entity.setType(String.valueOf(row.get("TYPE")));
			entity.setLongDescription(String.valueOf(row.get("LONG_DESCRIPTION")));
			entity.setShortDescription(String.valueOf(row.get("SHORT_DESCRIPTION")));
			entity.setExternalCode(String.valueOf(row.get("EXTERNAL_CODE")));
			list.add(entity);
		}
		return list;
	}

}
