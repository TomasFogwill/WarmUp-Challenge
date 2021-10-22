package com.challenge.warmup.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.challenge.warmup.enums.RoleName;
import com.challenge.warmup.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

   Optional<Role> findByRoleName(RoleName roleName);
}
