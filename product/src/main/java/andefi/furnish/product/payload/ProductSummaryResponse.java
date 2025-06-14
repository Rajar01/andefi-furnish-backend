package andefi.furnish.product.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.UUID;

public class ProductSummaryResponse {
  @JsonProperty private UUID id;
  @JsonProperty private String name;

  @JsonProperty private BigDecimal price;

  @JsonProperty("discount_percentage")
  private BigDecimal discountPercentage;

  @JsonProperty private String media = null;

  public ProductSummaryResponse() {}

  public ProductSummaryResponse(
      UUID id, String name, BigDecimal price, BigDecimal discountPercentage, String media) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.discountPercentage = discountPercentage;
    this.media = media;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setDiscountPercentage(BigDecimal discountPercentage) {
    this.discountPercentage = discountPercentage;
  }

  public void setMedia(String media) {
    this.media = media;
  }
}
