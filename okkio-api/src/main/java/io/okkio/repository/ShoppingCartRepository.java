package io.okkio.repository;

import io.okkio.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ShoppingCartRepository
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>, JpaSpecificationExecutor<ShoppingCart> {
	@Query(value = "select * from okkio.SHOPPING_CART cd where cd.status = ?2 and cd.user_id = ?1", nativeQuery = true)
	List<ShoppingCart> findShoppingCartByUserIdAndStatusContaining(@Param("userId") Long userId, @Param("status") String status);
	ShoppingCart findShoppingCartById(Long id);
}