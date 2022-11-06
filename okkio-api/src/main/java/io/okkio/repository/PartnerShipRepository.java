package io.okkio.repository;

import io.okkio.domain.Categories;
import io.okkio.domain.PartnerShip;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * CategoryRepository
 */
@Repository
public interface PartnerShipRepository extends JpaRepository<PartnerShip, Long>, JpaSpecificationExecutor<Categories> {
	Optional<PartnerShip> findById(Long id);

	void deleteById(Long id);
}