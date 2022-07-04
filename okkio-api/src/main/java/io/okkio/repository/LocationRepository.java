package io.okkio.repository;

import io.okkio.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LocationRepository
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {
	Location findLocationById(Long id);
	void deleteLocationById(Long id);
	List<Location> findLocationByName(String name);
}