package co.oudom.ecommerce.features.product.dto;

import co.oudom.ecommerce.features.category.dto.CategoryResponse;
import lombok.Builder;

@Builder
public record ProductResponse(

        String uuid,

        String name,

        String title,

        String description,

        String image,

        CategoryResponse category

) {
}
