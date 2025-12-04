package hn.com.tigo.inquiriesamsysnavega.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.inquiriesamsysnavega.entities.NavegaBalancesEntity;

@Repository
public interface NavegaBalancesRepository extends JpaRepository<NavegaBalancesEntity, Long> {

	@Query(value = "SELECT table_name FROM all_tables WHERE table_name LIKE 'CN_NAVEGA_BALANCES%'", nativeQuery = true)
	List<String> getAllTableNameNavega();

	@Query(value = "SELECT * FROM %s", nativeQuery = true)
	List<NavegaBalancesEntity> getNavegaBalanceData(String tableName);

	NavegaBalancesEntity getBalanceByEbsAccount(String ebsAccount);
}
