package io.okkio.repository.version2;

import io.okkio.domain.version2.ProductV2;
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
	@Query(value = "select * from product_v2 cd left join product_detail_v2 dt on cd.id = dt.productv2_id where cd.status = 'ACTIVATED' and cd.level in (0) " +
			"order by cd.id asc", nativeQuery = true)
	Page<ProductV2> findProductByStatusActivated(Pageable paging);
	@Query(value = "select * from product_v2 cd left join product_detail_v2 dt on cd.id = dt.productv2_id where cd.status = ?1 and cd.level in (?2) order by cd.id asc", nativeQuery = true)
	List<ProductV2> findProductByStatusActivatedWithoutPaging(@Param("status") String status, @Param("level") int level);
	ProductV2 findProductV2BySlug(String slug);
	List<ProductV2> findProductV2ByLevelCode(String levelCode);
	@Query(
			value = "SELECT * FROM product_v2 u WHERE u.level_code = ?1 and u.status = 'ACTIVATED' and u.name LIKE ?2",
			nativeQuery = true)
	List<ProductV2> findProductV2ByLevelCodeAndKeyword(String levelCode, String keyword);
}