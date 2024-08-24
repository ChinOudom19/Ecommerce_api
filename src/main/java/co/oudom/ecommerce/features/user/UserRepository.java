package co.oudom.ecommerce.features.user;

import co.oudom.ecommerce.domain.OrderDetail;
import co.oudom.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Boolean existsByEmail(String email);

    Boolean existsByIsVerified(Boolean isVerified);

    Optional<User> findByEmail(String email);

    Optional<User> findByUuid(String uuid);

}
