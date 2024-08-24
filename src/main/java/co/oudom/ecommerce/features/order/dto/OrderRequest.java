package co.oudom.ecommerce.features.order.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record OrderRequest(

        @NotBlank(message = "User is request")
        String userUuid,

        @NotBlank(message = "Address is request")
        String addressUuid,

        List<OrderProductRequest> OrderProductList

) {
}
