package com.ttknp.understandsecurityforudemywithjdk17.repositories.security;

import com.ttknp.understandsecurityforudemywithjdk17.models.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
