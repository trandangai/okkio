package io.okkio.repository;

import io.okkio.domain.Categories;
import io.okkio.domain.CustomerSupports;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * CategoryRepository
 */
@Repository
public interface CustomerSupportRepository
		extends JpaRepository<CustomerSupports, Long>, JpaSpecificationExecutor<CustomerSupports> {
	Optional<CustomerSupports> findById(Long id);

	void deleteById(Long id);
}