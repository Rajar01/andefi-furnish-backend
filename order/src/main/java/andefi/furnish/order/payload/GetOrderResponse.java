package andefi.furnish.order.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class GetOrderResponse {
  @JsonProperty UUID id;

  @JsonProperty("order_items")
  private List<GetOrderItemResponse> orderItems;

  @JsonProperty private BigDecimal amount;

  @JsonProperty("shipping_address")
  private String shippingAddress;

  @JsonProperty("created_at")
  private Instant createdAt;

  @JsonProperty("paid_at")
  private Instant paidAt;

  @JsonProperty("shipping_at")
  private Instant shippingAt;

  @JsonProperty("completed_at")
  private Instant completedAt;

  @JsonProperty("order_status")
  private String orderStatus;

  public void setId(UUID id) {
    this.id = id;
  }

  public void setOrderItems(List<GetOrderItemResponse> orderItems) {
    this.orderItems = orderItems;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public void setPaidAt(Instant paidAt) {
    this.paidAt = paidAt;
  }

  public void setShippingAt(Instant shippingAt) {
    this.shippingAt = shippingAt;
  }

  public void setCompletedAt(Instant completedAt) {
    this.completedAt = completedAt;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }
}
