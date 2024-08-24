package co.oudom.ecommerce.features.productitem;

import co.oudom.ecommerce.domain.Category;
import co.oudom.ecommerce.domain.Product;
import co.oudom.ecommerce.domain.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {

    List<ProductItem> findByProductIn(List<Product> products);

    Optional<ProductItem> findByUuid(String uuid);

    Boolean existsByProduct(Product product);

}
