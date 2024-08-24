package co.oudom.ecommerce.features.auth.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(

        String message,
        String email

) {
}
