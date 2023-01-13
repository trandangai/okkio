package io.okkio.repository;

import io.okkio.domain.Careers;
import io.okkio.domain.Categories;

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
public interface CareersRepository extends JpaRepository<Careers, Long>, JpaSpecificationExecutor<Careers> {
	Optional<Careers> findById(Long id);
	@Transactional
	@Modifying
	void deleteById(Long id);
}