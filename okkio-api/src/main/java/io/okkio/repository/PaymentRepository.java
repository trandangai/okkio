package io.okkio.repository;

import io.okkio.domain.Payment;
import io.okkio.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PaymentRepository
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
	@Query(value = "select * from payment cd where cd.status = ?1", nativeQuery = true)
	List<Payment> findPaymentByStatus(@Param("status") String status);
	Payment findPaymentById(Long id);
	@Query(value = "update payment cd set cd.status = ?2 where cd.id = ?1", nativeQuery = true)
	int updateStatusPayment(@Param("id") Long id, @Param("status") String status);
}