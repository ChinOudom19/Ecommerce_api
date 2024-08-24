package co.oudom.ecommerce.features.review;

import co.oudom.ecommerce.features.review.dto.ReviewRequest;
import co.oudom.ecommerce.features.review.dto.ReviewResponse;
import co.oudom.ecommerce.features.review.dto.ReviewUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @DeleteMapping("/{uuid}")
    void deleteByUuid(@PathVariable("uuid") String uuid) {
        reviewService.deleteByUuid(uuid);
    }

    @PatchMapping("/{uuid}")
    ReviewResponse updateByUuid(@PathVariable("uuid") String uuid,@Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest){
        return reviewService.updateByUuid(uuid, reviewUpdateRequest);
    }

    @GetMapping("/{uuid}")
    List<ReviewResponse> findListByProductItemUuid(@PathVariable("uuid") String uuid) {
        return reviewService.findListByProductItemUuid(uuid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ReviewResponse createNew(@Valid @RequestBody ReviewRequest reviewRequest) {
        return reviewService.createNew(reviewRequest);
    }
}
