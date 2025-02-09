package guru.sfg.brewery.repositories.sercurity;

import guru.sfg.brewery.models.security.Authority;
import guru.sfg.brewery.models.security.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
