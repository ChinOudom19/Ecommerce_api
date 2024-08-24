package co.oudom.ecommerce.features.category.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(

        String uuid,

        String name,

        String image

) {
}
