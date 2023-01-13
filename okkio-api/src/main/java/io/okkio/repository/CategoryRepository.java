package io.okkio.repository;

import io.okkio.domain.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * CategoryRepository
 */
@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long>, JpaSpecificationExecutor<Categories> {
	Categories findCategoriesById(Long id);
	@Transactional
	@Modifying
	void deleteCategoriesById(Long id);
	Categories findCategoriesByName(String name);
}