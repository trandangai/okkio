package io.okkio.repository;

import io.okkio.domain.Receipt;
import io.okkio.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ReceiptRepository
 */
@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long>, JpaSpecificationExecutor<Receipt> {
	@Transactional
	@Modifying
	@Query(value = "update receipt cd set cd.okkio_status_id = ?2, cd.updated_by = ?3 where cd.id = ?1", nativeQuery = true)
	int updateStatusReceipt(@Param("id") Long id, @Param("okkioStatusId") Long okkioStatusId,
							@Param("updatedBy") String updatedBy);
	Receipt findReceiptById(Long id);
}