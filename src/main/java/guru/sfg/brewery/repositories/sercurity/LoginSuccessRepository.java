package guru.sfg.brewery.repositories.sercurity;

import guru.sfg.brewery.models.security.LoginSuccess;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 7/20/20.
 */
public interface LoginSuccessRepository extends JpaRepository<LoginSuccess, Integer> {
}