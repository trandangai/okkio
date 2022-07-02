package io.okkio.repository;

import io.okkio.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * ProductDetailRepository
 */
@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long>, JpaSpecificationExecutor<ProductDetail> {
	ProductDetail findProductDetailById(Long id);
	void deleteProductDetailById(Long id);
	ProductDetail findProductDetailByName(String name);
}