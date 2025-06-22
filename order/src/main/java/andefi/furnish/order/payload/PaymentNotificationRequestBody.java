package andefi.furnish.order.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class PaymentNotificationRequestBody {
  @JsonProperty("signature_key")
  private String signatureKey;

  @JsonProperty("transaction_id")
  private UUID transactionId;

  @JsonProperty("transaction_status")
  private String transactionStatus;

  @JsonProperty("transaction_time")
  private String transactionTime;

  @JsonProperty("order_id")
  private UUID orderId;

  @JsonProperty("payment_type")
  private String paymentType;

  private String currency;

  @JsonProperty("gross_amount")
  private String grossAmount;

  @JsonProperty("fraud_status")
  private String fraudStatus;

  public String getSignatureKey() {
    return signatureKey;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public String getTransactionStatus() {
    return transactionStatus;
  }

  public String getTransactionTime() {
    return transactionTime;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public String getCurrency() {
    return currency;
  }

  public String getGrossAmount() {
    return grossAmount;
  }

  public String getFraudStatus() {
    return fraudStatus;
  }
}
