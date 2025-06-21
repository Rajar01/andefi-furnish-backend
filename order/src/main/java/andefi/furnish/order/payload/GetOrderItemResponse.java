package andefi.furnish.order.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.UUID;

public class GetOrderItemResponse {
  @JsonProperty private UUID id;

  @JsonProperty("product_id")
  private UUID productId;

  @JsonProperty("product_name")
  private String productName;

  @JsonProperty("product_current_unit_price")
  private BigDecimal productCurrentUnitPrice;

  @JsonProperty("product_current_discount_percentage")
  private Double productCurrentDiscountPercentage;

  @JsonProperty private Long quantity;

  public void setId(UUID id) {
    this.id = id;
  }

  public void setProductId(UUID productId) {
    this.productId = productId;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public void setProductCurrentUnitPrice(BigDecimal productCurrentUnitPrice) {
    this.productCurrentUnitPrice = productCurrentUnitPrice;
  }

  public void setProductCurrentDiscountPercentage(Double productCurrentDiscountPercentage) {
    this.productCurrentDiscountPercentage = productCurrentDiscountPercentage;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }
}
