package co.oudom.ecommerce.features.order.dto;

import co.oudom.ecommerce.features.product.dto.ProductResponse;
import co.oudom.ecommerce.features.productitem.dto.ProductItemResponse;
import lombok.Builder;

@Builder
public record OrderProductResponse(

        String uuid,

        Double qty,

        Double price,

        ProductItemResponse productItem

) {
}
