package io.okkio.repository;

import io.okkio.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductDetailRepository
 */
@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long>, JpaSpecificationExecutor<ProductDetail> {
	ProductDetail findProductDetailById(Long id);
	void deleteProductDetailById(Long id);
	ProductDetail findProductDetailByName(String name);
	@Query(value = "select cd.* from product_detail cd where cd.status = ?1", nativeQuery = true)
	List<ProductDetail> findProductDetailsByStatusActivated(@Param("status") String status);
}