package andefi.furnish.product.service;

import andefi.furnish.common.utility.CursorCodec;
import andefi.furnish.product.model.Product;
import andefi.furnish.product.model.Review;
import andefi.furnish.product.repository.ProductRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProductService {
  @Inject ReviewService reviewService;
  @Inject MediaService mediaService;

  @Inject ProductRepository productRepository;

  public List<Product> getProductCatalog(int limit, String cursor) {
    List<Product> products;

    if (limit < 1 || limit > 100) {
      throw new BadRequestException();
    }

    if (cursor == null || cursor.isBlank()) {
      products =
          productRepository
              .findAll(Sort.by("id", Sort.Direction.Ascending))
              .page(0, limit + 1)
              .list();
    } else {
      UUID c = UUID.fromString(CursorCodec.decode(cursor));

      products =
          productRepository
              .find("id >= ?1", Sort.by("id", Sort.Direction.Ascending), c)
              .page(0, limit + 1)
              .list();
    }

    if (!products.isEmpty()) {
      products.forEach(it -> it.setMedia(mediaService.getProductMedia(it.getId())));
    }

    return products;
  }

  public Product getProductById(UUID id) {
    Product product = productRepository.findByIdOptional(id).orElseThrow(NotFoundException::new);
    product.setMedia(mediaService.getProductMedia(product.getId()));

    return product;
  }

  public List<Review> getProductReviews(UUID id, int limit, String cursor) {
    return reviewService.getProductReviews(id, limit, cursor);
  }
}
