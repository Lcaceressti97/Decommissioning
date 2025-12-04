package hn.com.tigo.simcardinquiry.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.simcardinquiry.entities.SimcardOrderControlEntity;
import hn.com.tigo.simcardinquiry.models.OrderFilesModel;

@Repository
public interface ISimcardOrderControlRepository extends JpaRepository<SimcardOrderControlEntity, Long> {

	@Query(value = "SELECT * FROM SIMCARD_ORDER_CONTROL ORDER BY CREATED_DATE DESC", nativeQuery = true)
	Page<SimcardOrderControlEntity> getSimcardOrderControlPaginated(Pageable pageable);

	@Query(value = "SELECT * FROM SIMCARD_ORDER_CONTROL WHERE ID_SIMCARD_PADRE = :idSimcardPadre", nativeQuery = true)
	SimcardOrderControlEntity getOrderByIdPadre(Long idSimcardPadre);

	@Query("SELECT COALESCE(MAX(soc.id), 0) + 1 FROM SimcardOrderControlEntity soc")
	Long findNextId();

	@Query(value = "SELECT * FROM (SELECT * FROM SIMCARD_ORDER_CONTROL WHERE ID_SUPPLIER = :idSupplier ORDER BY CREATED_DATE DESC) WHERE ROWNUM = 1", nativeQuery = true)
	Optional<SimcardOrderControlEntity> findNextNoOrderByIdSupplier(@Param("idSupplier") Long idSupplier);
	
	@Query(value = "SELECT * FROM (SELECT * FROM SIMCARD_ORDER_CONTROL ORDER BY CREATED_DATE DESC) WHERE ROWNUM = 1", nativeQuery = true)
	Optional<SimcardOrderControlEntity> findNextBatch();

	List<SimcardOrderControlEntity> getOrderControlByIdSupplier(Long idSupplier);

	List<SimcardOrderControlEntity> getOrderControlByInitialImsi(String initialImsi);

	List<SimcardOrderControlEntity> getOrderControlByInitialIccd(String initialIccd);

	@Query("SELECT new hn.com.tigo.simcardinquiry.models.OrderFilesModel(s.orderFile, s.orderDetailFile) FROM SimcardOrderControlEntity s WHERE s.id = :id")
	OrderFilesModel getOrderFilesById(@Param("id") Long id);

}
