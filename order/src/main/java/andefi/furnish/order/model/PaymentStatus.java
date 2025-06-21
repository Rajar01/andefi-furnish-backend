package andefi.furnish.order.model;

public enum PaymentStatus {
  UNPAID("unpaid"),
  PAID("paid");

  private final String value;

  PaymentStatus(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
