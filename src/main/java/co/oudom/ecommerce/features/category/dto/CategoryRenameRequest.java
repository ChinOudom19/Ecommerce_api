package co.oudom.ecommerce.features.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRenameRequest(

        @NotBlank(message = "Name is request")
        String name

) {
}
