package co.oudom.ecommerce.features.product.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(

        @NotBlank(message = "Name is request")
        String name,

        @NotBlank(message = "Title is request")
        String title,

        @NotBlank(message = "Description is request")
        String description,

        @NotBlank(message = "Image is request")
        String image,

        @NotBlank(message = "Category is request")
        String categoryName

) {
}
