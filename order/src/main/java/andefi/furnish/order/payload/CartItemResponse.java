package andefi.furnish.order.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class CartItemResponse {
  @JsonProperty private UUID id;

  @JsonProperty("product_id")
  private UUID productId;

  @JsonProperty("product_name")
  private String productName;

  @JsonProperty("product_categories")
  private Set<String> productCategories;

  @JsonProperty("product_price")
  private BigDecimal productPrice;

  @JsonProperty private Long quantity;

  @JsonProperty("discount_percentage")
  private BigDecimal discountPercentage;

  @JsonProperty private String media = null;

  public CartItemResponse() {}

  public CartItemResponse(
      UUID id,
      UUID productId,
      String productName,
      Set<String> productCategories,
      BigDecimal productPrice,
      Long quantity,
      BigDecimal discountPercentage,
      String media) {
    this.id = id;
    this.productId = productId;
    this.productName = productName;
    this.productCategories = productCategories;
    this.productPrice = productPrice;
    this.quantity = quantity;
    this.discountPercentage = discountPercentage;
    this.media = media;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setProductId(UUID productId) {
    this.productId = productId;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public void setProductCategories(Set<String> productCategories) {
    this.productCategories = productCategories;
  }

  public void setProductPrice(BigDecimal productPrice) {
    this.productPrice = productPrice;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public void setDiscountPercentage(BigDecimal discountPercentage) {
    this.discountPercentage = discountPercentage;
  }

  public void setMedia(String media) {
    this.media = media;
  }
}
