package andefi.furnish.product.service;

import andefi.furnish.product.model.Product;
import andefi.furnish.product.model.Review;
import andefi.furnish.product.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProductService {
  @Inject ReviewService reviewService;
  @Inject MediaService mediaService;

  @Inject ProductRepository productRepository;

  public List<Product> getProductCatalog() {
    List<Product> products = productRepository.findAll().stream().toList();

    for (Product product : products) {
      product.setMedia(mediaService.getProductMedia(product.getId()));
    }

    return products;
  }

  public Product getProductDetails(UUID id) {
    Product product = productRepository.findByIdOptional(id).orElseThrow(NotFoundException::new);
    product.setMedia(mediaService.getProductMedia(product.getId()));

    return product;
  }

  public List<Review> getProductReviews(UUID id) {
    return reviewService.getProductReviews(id);
  }
}
