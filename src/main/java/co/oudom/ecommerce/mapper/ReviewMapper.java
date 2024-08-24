package co.oudom.ecommerce.mapper;

import co.oudom.ecommerce.domain.Review;
import co.oudom.ecommerce.features.review.dto.ReviewRequest;
import co.oudom.ecommerce.features.review.dto.ReviewResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review fromReviewRequest(ReviewRequest reviewRequest);

    ReviewResponse toReviewResponse(Review review);

}
