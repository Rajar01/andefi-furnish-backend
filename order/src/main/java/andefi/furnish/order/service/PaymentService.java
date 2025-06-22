package andefi.furnish.order.service;

import andefi.furnish.order.model.Order;
import andefi.furnish.order.model.OrderStatus;
import andefi.furnish.order.model.Payment;
import andefi.furnish.order.model.PaymentStatus;
import andefi.furnish.order.payload.CreateTransactionTokenRequestBody;
import andefi.furnish.order.payload.FraudStatus;
import andefi.furnish.order.payload.PaymentNotificationRequestBody;
import andefi.furnish.order.properties.MidtransProperties;
import andefi.furnish.order.repository.PaymentRepository;
import com.midtrans.Midtrans;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class PaymentService {
  @Inject OrderService orderService;
  @Inject MidtransProperties properties;

  @Inject PaymentRepository paymentRepository;

  public String createTransactionToken(CreateTransactionTokenRequestBody payload)
      throws MidtransError {
    Midtrans.serverKey = properties.serverKey;
    Midtrans.isProduction = properties.isProduction;

    Map<String, Object> params = new HashMap<>();

    // Transaction details
    Map<String, String> transactionDetails = new HashMap<>();
    transactionDetails.put("order_id", payload.getOrder().getId().toString());
    transactionDetails.put(
        "gross_amount", String.valueOf(payload.getOrder().getAmount().longValue()));

    // Credit card
    Map<String, String> creditCard = new HashMap<>();
    creditCard.put("secure", "true");

    // Item details
    List<Map<String, String>> itemDetails =
        payload.getOrder().getOrderItems().stream()
            .map(
                it -> {
                  return Map.of(
                      "name",
                      it.getProduct().getName(),
                      "price",
                      String.valueOf(
                          BigDecimal.valueOf(1 - it.getCurrentDiscountPercentage())
                              .multiply(it.getCurrentUnitPrice())
                              .longValue()),
                      "quantity",
                      it.getQuantity().toString(),
                      "id",
                      it.getProduct().getId().toString());
                })
            .toList();

    // Customer details
    Map<String, Object> customerDetails = new HashMap<>();
    customerDetails.put("email", payload.getAccount().getEmail());
    customerDetails.put(
        "shipping_address", Map.of("address", payload.getShippingAddress().getAddress()));

    params.put("transaction_details", transactionDetails);
    params.put("credit_card", creditCard);
    params.put("item_details", itemDetails);
    params.put("customer_details", customerDetails);

    return SnapApi.createTransactionToken(params);
  }

  @Transactional
  public Response notificationHandler(PaymentNotificationRequestBody payload) {
    // TODO check signature key to make sure the notification was actually sent by payment gateway

    // Check if notification already processed to avoid processing it as duplicate entries
    Order order =
        orderService.getOrderById(payload.getOrderId()).orElseThrow(NotFoundException::new);

    Payment payment = order.getPayment();
    PaymentStatus paymentStatus = PaymentStatus.fromString(payload.getTransactionStatus());

    if (order.getPayment().getStatus()
        == PaymentStatus.fromString(payload.getTransactionStatus())) {
      return Response.ok().build();
    }

    // Process payment notification
    FraudStatus fraudStatus = FraudStatus.fromString(payload.getFraudStatus());

    if ((paymentStatus == PaymentStatus.SETTLEMENT || paymentStatus == PaymentStatus.CAPTURE)
        && fraudStatus == FraudStatus.ACCEPT) {
      payment.setMethod(payload.getPaymentType());
      payment.setCurrency(Currency.getInstance(payload.getCurrency()));
      payment.setAmount((long) Double.parseDouble(payload.getGrossAmount()));
      payment.setPaidAt(Instant.now());
      payment.setStatus(paymentStatus);
      payment.setTransactionId(payload.getTransactionId());

      orderService.updateOrderStatus(order.getId(), OrderStatus.PAID);

      paymentRepository.persist(payment);
    } else {
      payment.setStatus(paymentStatus);
      paymentRepository.persist(payment);
    }

    return Response.ok().build();
  }
}
