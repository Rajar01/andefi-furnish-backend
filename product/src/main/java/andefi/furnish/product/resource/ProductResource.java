package andefi.furnish.product.resource;

import andefi.furnish.product.model.Category;
import andefi.furnish.product.model.Media;
import andefi.furnish.product.model.Product;
import andefi.furnish.product.model.Review;
import andefi.furnish.product.payload.ProductDetailsResponse;
import andefi.furnish.product.payload.ProductReviewsResponse;
import andefi.furnish.product.payload.ProductSummaryResponse;
import andefi.furnish.product.service.ProductService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Path("/api/products")
public class ProductResource {

  @Inject ProductService productService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authenticated
  public Response getProductCatalog() {
    List<Product> products = productService.getProductCatalog();

    List<ProductSummaryResponse> responses = new ArrayList<>();
    for (Product product : products) {
      String media = null;

      if (!product.getMedia().isEmpty()) {
        media = product.getMedia().getFirst().getUrl();
      }

      ProductSummaryResponse response = new ProductSummaryResponse();
      response.setId(product.getId());
      response.setName(product.getName());
      response.setPrice(product.getPrice());
      response.setDiscountPercentage(product.getDiscountPercentage());
      response.setMedia(media);

      responses.add(response);
    }

    return Response.ok(responses).build();
  }

  @GET
  @Path("/{product_id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authenticated
  public Response getProductDetails(@PathParam("product_id") UUID id) {
    Product product = productService.getProductDetails(id);

    Set<String> categories =
        product.getCategories().stream().map(Category::getName).collect(Collectors.toSet());
    List<String> media = product.getMedia().stream().map(Media::getUrl).toList();

    ProductDetailsResponse response = new ProductDetailsResponse();
    response.setId(product.getId());
    response.setName(product.getName());
    response.setDescription(product.getDescription());
    response.setStockQuantity(product.getStockQuantity());
    response.setPrice(product.getPrice());
    response.setDiscountPercentage(product.getDiscountPercentage());
    response.setCategories(categories);
    response.setAttributes(product.getAttributes());
    response.setMedia(media);

    return Response.ok(response).build();
  }

  @GET
  @Path("/{product_id}/reviews")
  @Produces(MediaType.APPLICATION_JSON)
  @Authenticated
  public Response getProductReviews(@PathParam("product_id") UUID id) {
    List<Review> reviews = productService.getProductReviews(id);

    List<ProductReviewsResponse> responses = new ArrayList<>();

    for (Review review : reviews) {
      List<String> media = review.getMedia().stream().map(Media::getUrl).toList();

      ProductReviewsResponse response = new ProductReviewsResponse();
      response.setId(review.getId());
      response.setAuthor(review.getAccount().getUsername());
      response.setContent(review.getContent());
      response.setRating(review.getRating());
      response.setMedia(media);

      responses.add(response);
    }

    return Response.ok(responses).build();
  }
}
