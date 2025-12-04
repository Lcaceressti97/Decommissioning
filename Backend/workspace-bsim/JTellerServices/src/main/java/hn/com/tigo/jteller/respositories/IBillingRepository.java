package hn.com.tigo.jteller.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import hn.com.tigo.jteller.entities.BillingEntity;

@Repository
public interface IBillingRepository extends JpaRepository<BillingEntity,Long>{

	List<BillingEntity> getBillingEntityByAcctCode(String acctCode);

	@Query("SELECT b FROM BillingEntity b WHERE b.id = :id AND b.status IN :statuses")
	BillingEntity getByIdAndStatus(@Param("id") Long id, @Param("statuses") List<Long> statuses);
}
