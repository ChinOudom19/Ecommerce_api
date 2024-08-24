package co.oudom.ecommerce.features.review;

import co.oudom.ecommerce.domain.ProductItem;
import co.oudom.ecommerce.domain.Review;
import co.oudom.ecommerce.domain.User;
import co.oudom.ecommerce.features.productitem.ProductItemRepository;
import co.oudom.ecommerce.features.review.dto.ReviewRequest;
import co.oudom.ecommerce.features.review.dto.ReviewResponse;
import co.oudom.ecommerce.features.review.dto.ReviewUpdateRequest;
import co.oudom.ecommerce.features.user.UserRepository;
import co.oudom.ecommerce.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.internal.SessionFactoryOptionsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserRepository userRepository;
    private final ProductItemRepository productItemRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;

    @Override
    public void deleteByUuid(String uuid) {
        Review review = reviewRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Review not found"));

        reviewRepository.delete(review);
    }

    @Override
    public ReviewResponse updateByUuid(String uuid, ReviewUpdateRequest reviewUpdateRequest) {

        Review review = reviewRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Review not found"));

        review.setComment(reviewUpdateRequest.comment());

        reviewRepository.save(review);

        return reviewMapper.toReviewResponse(review);

    }

    @Override
    public List<ReviewResponse> findListByProductItemUuid(String uuid) {

        ProductItem productItem = productItemRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));

        List<Review> reviews = productItem.getReviews();

        return reviews.stream().map(reviewMapper::toReviewResponse).toList();
    }

    @Override
    public ReviewResponse createNew(ReviewRequest reviewRequest) {

        //Validate User
        User user = userRepository
                .findByUuid(reviewRequest.userUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

        ProductItem productItem = productItemRepository
                .findByUuid(reviewRequest.productItemUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));

        Review review = reviewMapper.fromReviewRequest(reviewRequest);
        review.setUuid(UUID.randomUUID().toString());
        review.setUser(user);
        review.setProductItem(productItem);

        reviewRepository.save(review);

        return reviewMapper.toReviewResponse(review);
    }

}
