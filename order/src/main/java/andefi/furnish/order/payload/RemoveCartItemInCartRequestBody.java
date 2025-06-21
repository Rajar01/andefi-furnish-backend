package andefi.furnish.order.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class RemoveCartItemInCartRequestBody {
  @JsonProperty("cart_item_id")
  @NotNull
  private UUID cartItemId;

  public UUID getCartItemId() {
    return cartItemId;
  }
}
