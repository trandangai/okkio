package io.okkio.repository;

import io.okkio.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductRepository
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	Product findProductById(Long id);
	void deleteProductById(Long id);
	Product findProductByName(String name);
	List<Product> findProductByCategoryId(Long id);
}