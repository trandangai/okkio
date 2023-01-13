package io.okkio.repository;

import io.okkio.domain.Categories;
import io.okkio.domain.Image;

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
public interface ImageRepository extends JpaRepository<Image, Long>, JpaSpecificationExecutor<Image> {
	Optional<Image> findById(Long id);
	@Transactional
	@Modifying
	void deleteById(Long id);
}