package co.oudom.ecommerce.features.review;

import co.oudom.ecommerce.features.review.dto.ReviewRequest;
import co.oudom.ecommerce.features.review.dto.ReviewResponse;
import co.oudom.ecommerce.features.review.dto.ReviewUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    void deleteByUuid(String uuid);

    ReviewResponse updateByUuid(String uuid,ReviewUpdateRequest reviewUpdateRequest);

    List<ReviewResponse> findListByProductItemUuid(String uuid);

    ReviewResponse createNew(ReviewRequest reviewRequest);

}
