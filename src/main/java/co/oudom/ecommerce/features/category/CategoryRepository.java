package co.oudom.ecommerce.features.category;

import co.oudom.ecommerce.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Boolean existsByName(String name);

    Optional<Category> findByUuid(String uuid);

    Optional<Category> findByName(String name);

}
