package hn.com.tigo.equipmentaccessoriesbilling.repositories.bsim;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.bsim.InvAllExistencesViewEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.bsim.InvExistencesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.bsim.InvExistencesViewEntity;

@Repository
public class ExistencesDaoImpl implements IExistencesDao {

	private final JdbcTemplate jdbcTemplate;

	public ExistencesDaoImpl(@Value("${spring.bsim.datasource.url}") String url,
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
	public List<InvExistencesEntity> getExistencesByFilter(Long warehouseId, Long typeInventoryId) {
	    String sql = "SELECT DISTINCT ima.MODEL, ima.DESCRIPTION,ima.CODE,ima.EQUIPMENT_LINE_ID " +
	                 "FROM STCINVENTORY.INV_EXISTENCES ie " +
	                 "INNER JOIN STCINVENTORY.INV_MODELS_OR_ARTICLES ima " +
	                 "ON ie.MODEL_OR_ARTICLE_ID = ima.ID " +
	                 "WHERE ie.WAREHOUSE_ID = ? AND ie.TYPE_INVENTORY_ID = ?";
	    List<InvExistencesEntity> list = new ArrayList<>();

	    List<Map<String, Object>> executedQuery = jdbcTemplate.queryForList(sql, warehouseId, typeInventoryId);
	    for (Map<String, Object> row : executedQuery) {
	        InvExistencesEntity entity = new InvExistencesEntity();
	        Number id = (Number) row.get("ID");
	        entity.setId(id != null ? id.longValue() : null);
	        entity.setVersion((BigDecimal) row.get("VERSION"));
	        entity.setTypeInventoryId((BigDecimal) row.get("TYPE_INVENTORY_ID"));
	        entity.setModelOrArticleId((BigDecimal) row.get("MODEL_OR_ARTICLE_ID"));
	        entity.setWarehouseId((BigDecimal) row.get("WAREHOUSE_ID"));
	        entity.setTotalCostMil((BigDecimal) row.get("TOTAL_COST_ML"));
	        entity.setQuantityExistence((BigDecimal) row.get("QUANTITY_EXISTENCE"));
	        entity.setQuantityReserved((BigDecimal) row.get("QUANTITY_RESERVED"));
	        entity.setWarrantyExpirationDate((Timestamp) row.get("WARRANTY_EXPIRATION_DATE"));
	        entity.setPrimaryIdentification((String) row.get("PRIMARY_IDENTIFICATION"));
	        entity.setSecondaryIdentification((String) row.get("SECONDARY_IDENTIFICATION"));
	        entity.setServiceIdentification((String) row.get("SERVICE_IDENTIFICATION"));
	        entity.setLocalization((String) row.get("LOCALIZATION"));
	        entity.setNumberLot((String) row.get("NUMBER_LOT"));
	        entity.setUnitCost((BigDecimal) row.get("UNIT_COST"));
	        entity.setLegacyCode((String) row.get("LEGACY_CODE"));
	        entity.setSubWarehouseId((BigDecimal) row.get("SUB_WAREHOUSE_ID"));
	        entity.setLastTransactionId((BigDecimal) row.get("LAST_TRANSACTION_ID"));
	        entity.setReviousTransactionId((BigDecimal) row.get("PREVIOUS_TRANSACTION_ID"));
	        entity.setAuthorized((String) row.get("AUTHORIZED"));
	        entity.setLastTransferId((BigDecimal) row.get("LAST_TRANSFER_ID"));
	        entity.setPreviousTransferId((BigDecimal) row.get("PREVIOUS_TRANSFER_ID"));
	        entity.setLastReceiptDate((Timestamp) row.get("LAST_RECEIPT_DATE"));
	        entity.setPreviousReceiptDate((Timestamp) row.get("PREVIOUS_RECEIPT_DATE"));
	        entity.setBoxNumber((BigDecimal) row.get("BOX_NUMBER"));
	        entity.setLoadDate((Timestamp) row.get("LOAD_DATE"));
	        entity.setLoadCost((BigDecimal) row.get("LOAD_COST"));
	        entity.setModel((String) row.get("MODEL"));
	        entity.setDescription((String) row.get("DESCRIPTION"));
	        entity.setCode((String) row.get("CODE"));
	        entity.setEquipmentLineId((BigDecimal) row.get("EQUIPMENT_LINE_ID"));;

	        list.add(entity);
	    }
	    return list;
	}
	
	@Override
	public List<InvExistencesViewEntity> getExistencesViewByFilter(Long warehouseId, String type, String equipmentLine){
		
		if(!(type.equalsIgnoreCase("catalog") || type.equalsIgnoreCase("inventory"))) {
			throw new RuntimeException(String.format("Type value of '%s' is not valid, expected 'catalog' or 'inventory'", type));
		}
		
		if(!(equipmentLine.equalsIgnoreCase("TEL") || equipmentLine.equalsIgnoreCase("SIM") || equipmentLine.equalsIgnoreCase("ALL"))) {
			throw new RuntimeException(String.format("Equipment Line value of '%s' is not valid, expected 'TEL', 'SIM' or 'ALL'", equipmentLine));
		}
		
		String sql = "SELECT WAREHOUSE_ID, WAREHOUSE_CODE, INVENTORY_TYPE, LINE_CODE, MODEL_CODE, MODEL, DESCRIPTION, BRAND, QUANTITY " +
                "FROM STCINVENTORY.VIEW_INV_EXISTENCES " +
                "WHERE WAREHOUSE_ID = ? ";
		
		List<Map<String, Object>> executedQuery = null;
		
		if(type.equalsIgnoreCase("inventory")) {
			sql += "AND QUANTITY > 0";
		}
		
		if(!equipmentLine.equalsIgnoreCase("ALL")) {
			sql += "AND LINE_CODE = ?";
			executedQuery = jdbcTemplate.queryForList(sql, warehouseId, equipmentLine.toUpperCase());
		}

		if(equipmentLine.equalsIgnoreCase("ALL")) {
			executedQuery = jdbcTemplate.queryForList(sql, warehouseId);
		}
		
		List<InvExistencesViewEntity> list = new ArrayList<>();
		
		for (Map<String, Object> row : executedQuery) {
			InvExistencesViewEntity entity = new InvExistencesViewEntity();
			BigDecimal id = (BigDecimal) row.get("WAREHOUSE_ID");
			entity.setWarehouseId(id);
			entity.setWarehouseCode((String) row.get("WAREHOUSE_CODE"));
			entity.setInventoryType((String) row.get("INVENTORY_TYPE"));
			entity.setLineCode((String) row.get("LINE_CODE"));
			entity.setModelCode((String) row.get("MODEL_CODE"));
			entity.setModel((String) row.get("MODEL"));
			entity.setDescription((String) row.get("DESCRIPTION"));
			entity.setBrand((String) row.get("BRAND"));
			entity.setQuantity((BigDecimal) row.get("QUANTITY"));
			list.add(entity);
		}
		
		return list;
	}
	
	@Override
	public List<InvAllExistencesViewEntity> getAllExistencesViewByFilter(Long warehouseId, String type, String equipmentLine){
		
		if(!(type.equalsIgnoreCase("catalog") || type.equalsIgnoreCase("inventory"))) {
			throw new RuntimeException(String.format("Type value of '%s' is not valid, expected 'catalog' or 'inventory'", type));
		}
		
		if(!(equipmentLine.equalsIgnoreCase("TEL") || equipmentLine.equalsIgnoreCase("SIM") || equipmentLine.equalsIgnoreCase("ALL"))) {
			throw new RuntimeException(String.format("Equipment Line value of '%s' is not valid, expected 'TEL', 'SIM' or 'ALL'", equipmentLine));
		}
		
		String sql = "SELECT WAREHOUSE_ID, WAREHOUSE_CODE, INVENTORY_TYPE, LINE_CODE, MODEL_CODE, MODEL, DESCRIPTION, BRAND, QUANTITY, QUANTITY_RESERVED " +
                "FROM STCINVENTORY.VIEW_INV_EXISTENCES_ALL " +
                "WHERE WAREHOUSE_ID = ? ";
		
		List<Map<String, Object>> executedQuery = null;
		
		if(type.equalsIgnoreCase("inventory")) {
			sql += "AND QUANTITY > 0";
		}
		
		if(!equipmentLine.equalsIgnoreCase("ALL")) {
			sql += "AND LINE_CODE = ?";
			executedQuery = jdbcTemplate.queryForList(sql, warehouseId, equipmentLine.toUpperCase());
		}

		if(equipmentLine.equalsIgnoreCase("ALL")) {
			executedQuery = jdbcTemplate.queryForList(sql, warehouseId);
		}
		
		List<InvAllExistencesViewEntity> list = new ArrayList<>();
		
		for (Map<String, Object> row : executedQuery) {
			InvAllExistencesViewEntity entity = new InvAllExistencesViewEntity();
			BigDecimal id = (BigDecimal) row.get("WAREHOUSE_ID");
			entity.setWarehouseId(id);
			entity.setWarehouseCode((String) row.get("WAREHOUSE_CODE"));
			entity.setInventoryType((String) row.get("INVENTORY_TYPE"));
			entity.setLineCode((String) row.get("LINE_CODE"));
			entity.setModelCode((String) row.get("MODEL_CODE"));
			entity.setModel((String) row.get("MODEL"));
			entity.setDescription((String) row.get("DESCRIPTION"));
			entity.setBrand((String) row.get("BRAND"));
			entity.setQuantity((BigDecimal) row.get("QUANTITY"));
			entity.setQuantityReserved((BigDecimal) row.get("QUANTITY_RESERVED"));
			list.add(entity);
		}
		
		return list;
	}

}
