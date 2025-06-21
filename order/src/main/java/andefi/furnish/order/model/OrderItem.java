package andefi.furnish.order.model;

import andefi.furnish.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItem {
  @Id @GeneratedValue private UUID id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @OneToOne(cascade = CascadeType.ALL)
  @NotNull
  @JoinColumn(name = "product_id")
  private Product product;

  @NotNull
  @Column(name = "current_unit_price")
  private BigDecimal currentUnitPrice;

  @NotNull
  @Column(name = "current_discount_percentage")
  private Double currentDiscountPercentage;

  @NotNull private Long quantity;

  @NotNull
  @Column(name = "created_at")
  private Instant createdAt;

  @NotNull
  @Column(name = "updated_at")
  private Instant updatedAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = Instant.now();
  }

  public UUID getId() {
    return id;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public BigDecimal getCurrentUnitPrice() {
    return currentUnitPrice;
  }

  public void setCurrentUnitPrice(BigDecimal currentUnitPrice) {
    this.currentUnitPrice = currentUnitPrice;
  }

  public Double getCurrentDiscountPercentage() {
    return currentDiscountPercentage;
  }

  public void setCurrentDiscountPercentage(Double currentDiscountPercentage) {
    this.currentDiscountPercentage = currentDiscountPercentage;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
