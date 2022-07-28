package io.okkio.repository;

import io.okkio.domain.ShoppingCart;
import io.okkio.domain.Util;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UtilRepository
 */
@Repository
public interface UtilRepository extends JpaRepository<Util, Long>, JpaSpecificationExecutor<Util> {
	Util findUtilById(Long id);
	void deleteUtilById(Long id);

	@Query(value = "select * from util cd where cd.status = 'ACTIVATED' and cd.name = ?1", nativeQuery = true)
	List<Util> findUtilByName(@Param("name") String name);
}