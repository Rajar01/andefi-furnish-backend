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
  @NotNull
  @JoinColumn(name = "order_id")
  private Order order;

  @OneToOne
  @NotNull
  @JoinColumn(name = "product_id")
  private Product product;

  @NotNull private Long quantity;

  @Column(name = "price_per_item")
  @NotNull
  private BigDecimal pricePerItem;

  @NotNull private BigDecimal amount;

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

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getPricePerItem() {
    return pricePerItem;
  }

  public void setPricePerItem(BigDecimal pricePerItem) {
    this.pricePerItem = pricePerItem;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
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
