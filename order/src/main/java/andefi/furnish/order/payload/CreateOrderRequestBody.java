package andefi.furnish.order.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class CreateOrderRequestBody {
  @JsonProperty("account_id")
  private UUID accountId;

  @JsonProperty("order_items")
  @NotEmpty
  private List<CreateOrderItemRequestBody> orderItems;

  @JsonProperty("shipping_address_id")
  @NotNull
  private UUID shippingAddressId;

  public UUID getAccountId() {
    return accountId;
  }

  public void setAccountId(UUID accountId) {
    this.accountId = accountId;
  }

  public List<CreateOrderItemRequestBody> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<CreateOrderItemRequestBody> orderItems) {
    this.orderItems = orderItems;
  }

  public UUID getShippingAddressId() {
    return shippingAddressId;
  }

  public void setShippingAddressId(UUID shippingAddressId) {
    this.shippingAddressId = shippingAddressId;
  }
}
