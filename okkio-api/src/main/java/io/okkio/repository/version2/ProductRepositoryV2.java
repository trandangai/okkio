package io.okkio.repository.version2;

import io.okkio.domain.version2.ProductV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProductRepositoryV2
 */
@Repository
public interface ProductRepositoryV2 extends JpaRepository<ProductV2, Long>, JpaSpecificationExecutor<ProductV2> {
	ProductV2 findProductById(Long id);
	@Transactional
	@Modifying
	void deleteProductById(Long id);
	ProductV2 findProductByName(String name);
	List<ProductV2> findProductByCategoriesV2Id(Long id);
	@Query(value = "select * from product_v2 cd left join product_detail_v2 dt on cd.id = dt.productv2_id where cd.status = ?1 and cd.level in (?2) order by cd.id asc", nativeQuery = true)
	List<ProductV2> findProductByStatusActivated(@Param("status") String status, @Param("level") int level);
	ProductV2 findProductV2BySlug(String slug);
	List<ProductV2> findProductV2ByLevelCode(String levelCode);
}