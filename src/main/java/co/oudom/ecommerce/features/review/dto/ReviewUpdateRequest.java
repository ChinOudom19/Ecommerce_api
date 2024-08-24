package co.oudom.ecommerce.features.review.dto;

import jakarta.validation.constraints.NotBlank;

public record ReviewUpdateRequest(

        @NotBlank(message = "Comment is request")
        String comment

) {
}
