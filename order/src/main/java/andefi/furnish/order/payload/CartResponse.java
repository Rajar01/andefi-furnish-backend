package andefi.furnish.order.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartResponse {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("cart_items")
  private List<CartItemResponse> cartItems = new ArrayList<>();

  public CartResponse() {}

  public CartResponse(UUID id, List<CartItemResponse> cartItems) {
    this.id = id;
    this.cartItems = cartItems;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setCartItems(List<CartItemResponse> cartItems) {
    this.cartItems = cartItems;
  }
}
