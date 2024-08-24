package co.oudom.ecommerce.features.review.dto;

import co.oudom.ecommerce.domain.User;
import co.oudom.ecommerce.features.user.dto.UserResponse;
import lombok.Builder;

@Builder
public record ReviewResponse(

        String uuid,

        String comment,

        String image,

        UserResponse user

) {
}
