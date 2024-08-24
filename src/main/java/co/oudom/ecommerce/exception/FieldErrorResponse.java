package co.oudom.ecommerce.exception;

import lombok.Builder;

@Builder
public record FieldErrorResponse(

        String field,
        String detail

) {
}
