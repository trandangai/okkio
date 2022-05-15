package io.okkio.repository;

import io.okkio.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	User findUserByUsername(String username);
	User findUserByEmail(String email);
	User findUserById(Long id);
	User findUserByEmailAndPassword(String email, String password);
}