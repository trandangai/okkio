package io.okkio.repository;

import io.okkio.domain.Product;
import io.okkio.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProductRepository
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	Product findProductById(Long id);
	@Transactional
	@Modifying
	void deleteProductById(Long id);
	Product findProductByName(String name);
	List<Product> findProductByCategoryId(Long id);
	@Query(value = "select cd.* from product cd where cd.status = ?1", nativeQuery = true)
	List<Product> findProductByStatusActivated(@Param("status") String status);
}