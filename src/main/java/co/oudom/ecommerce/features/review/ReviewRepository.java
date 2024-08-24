package co.oudom.ecommerce.features.review;

import co.oudom.ecommerce.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Optional<Review> findByUuid(String uuid);

}
