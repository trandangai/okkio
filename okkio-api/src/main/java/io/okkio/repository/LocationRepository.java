package io.okkio.repository;

import io.okkio.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * LocationRepository
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {
	Location findLocationById(Long id);
	@Transactional
	@Modifying
	void deleteLocationById(Long id);
	List<Location> findLocationByName(String name);
}