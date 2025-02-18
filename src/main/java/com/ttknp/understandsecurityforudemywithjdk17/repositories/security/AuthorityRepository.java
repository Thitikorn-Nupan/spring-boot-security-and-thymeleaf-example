package com.ttknp.understandsecurityforudemywithjdk17.repositories.security;

import com.ttknp.understandsecurityforudemywithjdk17.models.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
