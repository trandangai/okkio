package io.okkio.repository;

import io.okkio.domain.Util;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UtilRepository
 */
@Repository
public interface UtilRepository extends JpaRepository<Util, Long>, JpaSpecificationExecutor<Util> {
	Util findUtilById(Long id);
	void deleteUtilById(Long id);
	List<Util> findUtilByName(String name);
}