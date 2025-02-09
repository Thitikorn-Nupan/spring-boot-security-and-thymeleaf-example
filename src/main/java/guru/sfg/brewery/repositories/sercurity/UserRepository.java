package guru.sfg.brewery.repositories.sercurity;

import guru.sfg.brewery.models.BeerInventory;
import guru.sfg.brewery.models.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository  extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
