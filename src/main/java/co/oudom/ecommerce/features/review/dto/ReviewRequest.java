package co.oudom.ecommerce.features.review.dto;

import jakarta.validation.constraints.NotBlank;

public record ReviewRequest(

        @NotBlank(message = "User ID is request")
        String userUuid,

        @NotBlank(message = "Product Item ID is request")
        String productItemUuid,

        @NotBlank(message = "Comment is request")
        String comment,

        String image

) {
}
