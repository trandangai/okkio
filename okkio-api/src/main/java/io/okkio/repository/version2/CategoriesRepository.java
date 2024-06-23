package io.okkio.repository.version2;

import io.okkio.domain.version2.CategoriesV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CategoryRepository
 */
@Repository
public interface CategoriesRepository extends JpaRepository<CategoriesV2, Long>, JpaSpecificationExecutor<CategoriesV2> {
	CategoriesV2 findCategoriesById(Long id);
	@Transactional
	@Modifying
	void deleteCategoriesById(Long id);
	CategoriesV2 findCategoriesByName(String name);
	@Query(value = "select cd.* from categories_v2 cd where cd.status = 'ACTIVATED' order by priority asc", nativeQuery = true)
	List<CategoriesV2> findCategoriesWithStatusActive();
}