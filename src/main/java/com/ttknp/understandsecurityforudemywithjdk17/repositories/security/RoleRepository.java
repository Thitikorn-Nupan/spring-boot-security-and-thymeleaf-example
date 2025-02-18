package com.ttknp.understandsecurityforudemywithjdk17.repositories.security;

import com.ttknp.understandsecurityforudemywithjdk17.models.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String customer);
}
