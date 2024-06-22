package io.okkio.repository;

import io.okkio.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ShoppingCartRepository
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>, JpaSpecificationExecutor<ShoppingCart> {
	@Query(value = "select * from shopping_cart cd where cd.status = ?2 and cd.phone = ?1", nativeQuery = true)
	List<ShoppingCart> findShoppingCartByPhoneAndStatusContaining(@Param("phone") String phone, @Param("status") String status);
	ShoppingCart findShoppingCartById(Long id);
	@Transactional
	@Modifying
	@Query(value = "update shopping_cart cd set cd.status = ?2 where cd.id = ?1", nativeQuery = true)
	int updateStatusShoppingCart(@Param("id") Long id, @Param("status") String status);
	@Query(value = "select * from shopping_cart cd where cd.status = ?2 and cd.phone = ?1 and cd.order_id = ?3", nativeQuery = true)
	List<ShoppingCart> findShoppingCartByPhoneAndStatusContainingAndOrderId(@Param("phone") String phone,
																			@Param("status") String status,
																			@Param("orderId") Long orderId);
}