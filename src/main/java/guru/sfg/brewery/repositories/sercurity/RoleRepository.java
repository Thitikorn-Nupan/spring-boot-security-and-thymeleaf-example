package guru.sfg.brewery.repositories.sercurity;

import guru.sfg.brewery.models.security.Role;
import guru.sfg.brewery.models.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {
}
