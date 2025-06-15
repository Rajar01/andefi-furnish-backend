package andefi.furnish.product.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ProductReviewsResponse {
  @JsonProperty("product_reviews")
  List<ProductReviewResponse> productReviews;

  @JsonProperty("has_more")
  private boolean hasMore;

  @JsonProperty("next_cursor")
  private String nextCursor;

  public ProductReviewsResponse() {}

  public ProductReviewsResponse(
      List<ProductReviewResponse> productReviews, boolean hasMore, String nextCursor) {
    this.productReviews = productReviews;
    this.hasMore = hasMore;
    this.nextCursor = nextCursor;
  }

  public void setProductReviews(List<ProductReviewResponse> productReviews) {
    this.productReviews = productReviews;
  }

  public void setHasMore(boolean hasMore) {
    this.hasMore = hasMore;
  }

  public void setNextCursor(String nextCursor) {
    this.nextCursor = nextCursor;
  }
}
