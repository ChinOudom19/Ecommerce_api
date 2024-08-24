package co.oudom.ecommerce.features.productitem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductItemRequest(

        @NotBlank(message = "Product is request")
        String productUuid,

        @NotNull(message = "Discount is request")
        Double discount,

        @NotNull(message = "Quality is request")
        Double qty,

        @NotNull(message = "Unit Price is request")
        Double unitPrice,

        Boolean sku,

        Integer view

) {
}
