package io.okkio.repository.version2;

import io.okkio.domain.version2.ProductDetailV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProductDetailRepositoryV2
 */
@Repository
public interface ProductDetailRepositoryV2 extends JpaRepository<ProductDetailV2, Long>, JpaSpecificationExecutor<ProductDetailV2> {
	ProductDetailV2 findProductDetailById(Long id);
	@Transactional
	@Modifying
	void deleteProductDetailById(Long id);
	ProductDetailV2 findProductDetailByName(String name);
	@Query(value = "select cd.* from product_detail_v2 cd where cd.status = ?1", nativeQuery = true)
	List<ProductDetailV2> findProductDetailsByStatusActivated(@Param("status") String status);
	@Query(value = "select cd.* from product_detail_v2 cd where cd.status = ?1 and cd.product_id = ?2", nativeQuery = true)
	List<ProductDetailV2> findProductDetailsByStatusActivatedAndProductId(@Param("status") String status, @Param("productId") Long productId);
	ProductDetailV2 findProductDetailV2BySlug(String slug);
}