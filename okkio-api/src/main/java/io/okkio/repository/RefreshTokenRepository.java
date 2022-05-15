package io.okkio.repository;

import io.okkio.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RefreshTokenRepository
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>, JpaSpecificationExecutor<RefreshToken> {
	RefreshToken findByToken(String token);

	Optional<RefreshToken> findById(Long id);
}