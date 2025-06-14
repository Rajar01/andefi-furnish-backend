package andefi.furnish.product.model;

public enum EntityType {
  PRODUCT("product"),
  REVIEW("review");

  private final String value;

  EntityType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
