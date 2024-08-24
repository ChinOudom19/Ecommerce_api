package co.oudom.ecommerce.features.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(

        @NotBlank(message = "Name is request")
        String name,

        @NotBlank(message = "Image is request")
        String image

) {
}
