package andefi.furnish.product.service;

import andefi.furnish.product.model.Review;
import andefi.furnish.product.repository.ReviewRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReviewService {

  @Inject MediaService mediaService;

  @Inject ReviewRepository reviewRepository;

  public List<Review> getProductReviews(UUID id) {
    List<Review> reviews = reviewRepository.findByProductId(id);

    for (Review review : reviews) {
      review.setMedia(mediaService.getReviewMedia(review.getId()));
    }

    return reviews;
  }
}
