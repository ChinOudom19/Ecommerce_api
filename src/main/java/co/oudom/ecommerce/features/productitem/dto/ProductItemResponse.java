package co.oudom.ecommerce.features.productitem.dto;

import co.oudom.ecommerce.features.product.dto.ProductResponse;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductItemResponse(

        String uuid,

        Double qty,

        Double discount,

        Double unitPrice,

        Double price,

        Boolean sku,

        Integer view,

        LocalDateTime createAt,

        LocalDateTime updateAt,

        ProductResponse product

) {
}
