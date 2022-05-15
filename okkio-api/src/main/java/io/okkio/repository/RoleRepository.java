package io.okkio.repository;

import io.okkio.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
	Role findRoleById(Integer id);
}