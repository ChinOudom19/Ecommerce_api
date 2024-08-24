package co.oudom.ecommerce.mapper;

import co.oudom.ecommerce.domain.ProductItem;
import co.oudom.ecommerce.features.productitem.dto.ProductItemRequest;
import co.oudom.ecommerce.features.productitem.dto.ProductItemResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductItemMapper {

    ProductItemResponse toProductItemResponse(ProductItem productItem);

    ProductItem fromProductItemRequest(ProductItemRequest productItemRequest);

}
