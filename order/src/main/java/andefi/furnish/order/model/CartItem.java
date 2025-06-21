package andefi.furnish.order.model;

import andefi.furnish.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
public class CartItem {
  @Id @GeneratedValue private UUID id;

  @ManyToOne
  @NotNull
  @JoinColumn(name = "cart_id")
  private Cart cart;

  @ManyToOne
  @NotNull
  @JoinColumn(name = "product_id")
  private Product product;

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

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
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
