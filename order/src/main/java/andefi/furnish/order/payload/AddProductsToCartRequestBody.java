package andefi.furnish.order.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class AddProductsToCartRequestBody {
  @JsonProperty("product_id")
  @NotNull
  private UUID productId;

  @NotNull private Long quantity;

  public UUID getProductId() {
    return productId;
  }

  public Long getQuantity() {
    return quantity;
  }
}
