package andefi.furnish.order.model;

import andefi.furnish.account.model.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
  @Id @GeneratedValue private UUID id;

  @ManyToOne
  @NotNull
  @JoinColumn(name = "account_id")
  private Account account;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems;

  private BigDecimal amount;

  @ManyToOne
  @NotNull
  @JoinColumn(name = "shipping_address_id")
  private ShippingAddress shippingAddress;

  @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private Payment payment;

  private OrderStatus status = OrderStatus.UNPAID;

  @Column(name = "shipping_at")
  private Instant shippingAt = null;

  @Column(name = "completed_at")
  private Instant completedAt = null;

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

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public ShippingAddress getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(ShippingAddress shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public Instant getShippingAt() {
    return shippingAt;
  }

  public void setShippingAt(Instant shippingAt) {
    this.shippingAt = shippingAt;
  }

  public Instant getCompletedAt() {
    return completedAt;
  }

  public void setCompletedAt(Instant completedAt) {
    this.completedAt = completedAt;
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
