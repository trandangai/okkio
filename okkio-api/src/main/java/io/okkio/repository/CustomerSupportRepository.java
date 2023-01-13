package io.okkio.repository;

import io.okkio.domain.Categories;
import io.okkio.domain.CustomerSupports;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * CategoryRepository
 */
@Repository
public interface CustomerSupportRepository
		extends JpaRepository<CustomerSupports, Long>, JpaSpecificationExecutor<CustomerSupports> {
	Optional<CustomerSupports> findById(Long id);
	@Transactional
	@Modifying
	void deleteById(Long id);
}