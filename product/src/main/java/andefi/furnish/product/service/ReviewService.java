package andefi.furnish.product.service;

import andefi.furnish.common.utility.CursorCodec;
import andefi.furnish.product.model.Review;
import andefi.furnish.product.repository.ReviewRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReviewService {

  @Inject MediaService mediaService;

  @Inject ReviewRepository reviewRepository;

  public List<Review> getProductReviews(UUID id, int limit, String cursor) {
    List<Review> reviews;

    if (limit < 1 || limit > 100) {
      throw new BadRequestException();
    }

    if (cursor == null || cursor.isBlank()) {
      reviews =
          reviewRepository
              .find("product.id", Sort.by("id", Sort.Direction.Ascending), id)
              .page(0, limit + 1)
              .list();
    } else {
      UUID c = UUID.fromString(CursorCodec.decode(cursor));

      reviews =
          reviewRepository
              .find("product.id = ?1 and id >= ?2", Sort.by("id", Sort.Direction.Ascending), id, c)
              .page(0, limit + 1)
              .list();
    }

    if (!reviews.isEmpty()) {
      reviews.forEach(it -> it.setMedia(mediaService.getReviewMedia(it.getId())));
    }

    return reviews;
  }
}
