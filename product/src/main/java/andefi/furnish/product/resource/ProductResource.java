package andefi.furnish.product.resource;

import andefi.furnish.common.utility.CursorCodec;
import andefi.furnish.product.model.Category;
import andefi.furnish.product.model.Media;
import andefi.furnish.product.model.Product;
import andefi.furnish.product.model.Review;
import andefi.furnish.product.payload.*;
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
  public Response getProductCatalog(
      @QueryParam("limit") @DefaultValue("10") int limit, @QueryParam("cursor") String cursor) {
    List<Product> products = productService.getProductCatalog(limit, cursor);
    boolean hasMore = products.size() > limit;
    String nextCursor = hasMore ? CursorCodec.encode(products.getLast().getId().toString()) : null;

    List<ProductSummaryResponse> productSummaries =
        products.stream()
            .limit(limit)
            .map(
                it -> {
                  String media = null;
                  Set<String> categories =
                      it.getCategories().stream()
                          .map(Category::getName)
                          .collect(Collectors.toSet());

                  if (!it.getMedia().isEmpty()) {
                    media = it.getMedia().getFirst().getUrl();
                  }

                  ProductSummaryResponse product = new ProductSummaryResponse();
                  product.setId(it.getId());
                  product.setName(it.getName());
                  product.setPrice(it.getPrice());
                  product.setDiscountPercentage(it.getDiscountPercentage());
                  product.setCategories(categories);
                  product.setMedia(media);

                  return product;
                })
            .toList();

    ProductCatalogResponse response = new ProductCatalogResponse();
    response.setProductSummaries(productSummaries);
    response.setHasMore(hasMore);
    response.setNextCursor(nextCursor);

    return Response.ok(response).build();
  }

  @GET
  @Path("/{product_id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authenticated
  public Response getProductDetails(@PathParam("product_id") UUID id) {
    Product product = productService.getProductById(id);

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
  public Response getProductReviews(
      @PathParam("product_id") UUID id,
      @QueryParam("limit") @DefaultValue("10") int limit,
      @QueryParam("cursor") String cursor) {
    List<Review> reviews = productService.getProductReviews(id, limit, cursor);
    boolean hasMore = reviews.size() > limit;
    String nextCursor = hasMore ? CursorCodec.encode(reviews.getLast().getId().toString()) : null;

    List<ProductReviewResponse> productReviews =
        reviews.stream()
            .limit(limit)
            .map(
                it -> {
                  List<String> media = it.getMedia().stream().map(Media::getUrl).toList();

                  ProductReviewResponse review = new ProductReviewResponse();
                  review.setId(it.getId());
                  review.setAuthor(it.getAccount().getUsername());
                  review.setContent(it.getContent());
                  review.setRating(it.getRating());
                  review.setMedia(media);
                  review.setCreatedAt(it.getCreatedAt());

                  return review;
                })
            .toList();

    ProductReviewsResponse response = new ProductReviewsResponse();
    response.setProductReviews(productReviews);
    response.setHasMore(hasMore);
    response.setNextCursor(nextCursor);

    return Response.ok(response).build();
  }
}
