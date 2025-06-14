package andefi.furnish.product.model;

public enum MediaType {
  IMAGE("image"),
  VIDEO("video"),
  _3D_MODEL("3d_model");

  private final String value;

  MediaType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
