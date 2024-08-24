package co.oudom.ecommerce.features.auth;

import co.oudom.ecommerce.domain.User;
import co.oudom.ecommerce.domain.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {

    Optional<UserVerification> findByUserAndVerifiedCode(User user, String verifiedCode);

    Optional<UserVerification> findByUser(User user);

}
