package hn.com.tigo.comodatos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.comodatos.entities.CancellationPermissionEntity;

import java.util.List;


@Repository
public interface ICancellationPermissionRepository extends JpaRepository<CancellationPermissionEntity, Long>{

	List<CancellationPermissionEntity> findByUserName(String userName);
	
}
