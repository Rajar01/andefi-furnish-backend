package andefi.furnish.order.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateCartItemQuantityInCartRequestBody {
  @JsonProperty("cart_item_id")
  @NotNull
  private UUID cartItemId;

  @NotNull private Long quantity;

  public UUID getCartItemId() {
    return cartItemId;
  }

  public Long getQuantity() {
    return quantity;
  }
}
