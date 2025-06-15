package andefi.furnish.product.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ProductCatalogResponse {
  @JsonProperty("product_summaries")
  List<ProductSummaryResponse> productSummaries;

  @JsonProperty("has_more")
  private boolean hasMore;

  @JsonProperty("next_cursor")
  private String nextCursor;

  public ProductCatalogResponse() {}

  public ProductCatalogResponse(
      List<ProductSummaryResponse> productSummaries, boolean hasMore, String nextCursor) {
    this.productSummaries = productSummaries;
    this.hasMore = hasMore;
    this.nextCursor = nextCursor;
  }

  public List<ProductSummaryResponse> getProductSummaries() {
    return productSummaries;
  }

  public void setProductSummaries(List<ProductSummaryResponse> productSummaries) {
    this.productSummaries = productSummaries;
  }

  public boolean isHasMore() {
    return hasMore;
  }

  public void setHasMore(boolean hasMore) {
    this.hasMore = hasMore;
  }

  public String getNextCursor() {
    return nextCursor;
  }

  public void setNextCursor(String nextCursor) {
    this.nextCursor = nextCursor;
  }
}
