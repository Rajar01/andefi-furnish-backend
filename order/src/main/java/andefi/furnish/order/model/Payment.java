package andefi.furnish.order.model;

import andefi.furnish.account.model.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {
  @Id @GeneratedValue private UUID id;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @OneToOne
  @JoinColumn(name = "order_id")
  private Order order;

  private String method;

  private Currency currency;

  private Long amount;

  @Column(name = "paid_at")
  private Instant paidAt = null;

  private PaymentStatus status;

  @Column(name = "transaction_id")
  private UUID transactionId;

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

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public Instant getPaidAt() {
    return paidAt;
  }

  public void setPaidAt(Instant paidAt) {
    this.paidAt = paidAt;
  }

  public PaymentStatus getStatus() {
    return status;
  }

  public void setStatus(PaymentStatus status) {
    this.status = status;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(UUID transactionId) {
    this.transactionId = transactionId;
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
