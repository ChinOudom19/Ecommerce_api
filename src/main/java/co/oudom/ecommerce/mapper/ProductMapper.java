package co.oudom.ecommerce.mapper;

import co.oudom.ecommerce.domain.Product;
import co.oudom.ecommerce.features.product.dto.ProductRequest;
import co.oudom.ecommerce.features.product.dto.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    List<ProductResponse> toProductResponses(List<Product> products);

    Product fromProductRequest(ProductRequest productRequest);

    ProductResponse toProductResponse(Product product);
}
