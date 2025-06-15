package andefi.furnish.product.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ProductDetailsResponse {
  @JsonProperty private UUID id;
  @JsonProperty private String name;
  @JsonProperty private String description;

  @JsonProperty("stock_quantity")
  private Long stockQuantity;

  @JsonProperty private BigDecimal price;

  @JsonProperty("discount_percentage")
  private BigDecimal discountPercentage;

  @JsonProperty private Set<String> categories;
  private String attributes;

  @JsonProperty private List<String> media = new ArrayList<>();

  public ProductDetailsResponse() {}

  public ProductDetailsResponse(
      UUID id,
      String name,
      String description,
      Long stockQuantity,
      BigDecimal price,
      BigDecimal discountPercentage,
      Set<String> categories,
      String attributes) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.stockQuantity = stockQuantity;
    this.price = price;
    this.discountPercentage = discountPercentage;
    this.categories = categories;
    this.attributes = attributes;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStockQuantity(Long stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setDiscountPercentage(BigDecimal discountPercentage) {
    this.discountPercentage = discountPercentage;
  }

  public void setCategories(Set<String> categories) {
    this.categories = categories;
  }

  public void setAttributes(String attributes) {
    this.attributes = attributes;
  }

  public void setMedia(List<String> media) {
    this.media = media;
  }
}
