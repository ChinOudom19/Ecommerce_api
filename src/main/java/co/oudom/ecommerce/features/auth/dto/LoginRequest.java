package co.oudom.ecommerce.features.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Phone number is required")
        String email,

        @NotBlank(message = "Password is required")
        String password

) {
}
