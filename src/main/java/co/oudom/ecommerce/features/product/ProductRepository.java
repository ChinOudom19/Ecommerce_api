package co.oudom.ecommerce.features.product;

import co.oudom.ecommerce.domain.Category;
import co.oudom.ecommerce.domain.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByUuid(String uuid);

    List<Product> findByCategory(Category category);

}
