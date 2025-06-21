package andefi.furnish.order.model;

public enum OrderStatus {
  UNPAID("unpaid"),
  PACKED("packed"),
  SHIPPED("shipped"),
  COMPLETED("completed"),
  CANCELED("canceled");

  private final String value;

  OrderStatus(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
