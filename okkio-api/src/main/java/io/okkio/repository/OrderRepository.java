package io.okkio.repository;

import io.okkio.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * OrderRepository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
	Order findProductById(Long id);
	@Query(value = "select cd.* from `order` cd where cd.user_id = ?1 and cd.OKKIO_STATUS_ID = ?2 and cd.status = ?3", nativeQuery = true)
	List<Order> findOrderByUserIdAndOrderStatus(@Param("userId") Long userId, @Param("orderStatus") int orderStatus,
												@Param("status") String status);
	@Transactional
	@Modifying
	@Query(value = "update `order` cd set cd.OKKIO_STATUS_ID = ?1, cd.updated_by = ?2 where cd.id = ?3", nativeQuery = true)
	int update(@Param("orderStatus") Long orderStatus, @Param("updatedBy") String updatedBy, @Param("orderId") Long orderId);
	Order findOrderByOrderCode(String orderCode);
	@Query(
			value = "SELECT * FROM `order` u WHERE u.status = 'ACTIVATED' and u.ORDER_CODE LIKE %:keyword% or u.phone LIKE %:keyword% or u.email LIKE %:keyword%",
			nativeQuery = true)
	Page<Order> findOrderByStatus(String keyword, Pageable paging);
}