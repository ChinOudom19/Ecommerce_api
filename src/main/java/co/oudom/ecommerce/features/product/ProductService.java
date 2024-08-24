package co.oudom.ecommerce.features.product;

import co.oudom.ecommerce.features.product.dto.ProductRequest;
import co.oudom.ecommerce.features.product.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    void deleteByUuid(String uuid);

    ProductResponse updateByUuid(String uuid,ProductRequest productRequest);

    ProductResponse findByUuid(String uuid);

    List<ProductResponse> findList();

    ProductResponse createProduct(ProductRequest productRequest);

}
