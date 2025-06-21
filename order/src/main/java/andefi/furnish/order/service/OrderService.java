package andefi.furnish.order.service;

import andefi.furnish.account.model.Account;
import andefi.furnish.account.service.AccountService;
import andefi.furnish.order.model.Order;
import andefi.furnish.order.model.OrderItem;
import andefi.furnish.order.model.Payment;
import andefi.furnish.order.model.ShippingAddress;
import andefi.furnish.order.payload.CreateOrderRequestBody;
import andefi.furnish.order.payload.GetOrderItemResponse;
import andefi.furnish.order.payload.GetOrderResponse;
import andefi.furnish.order.repository.OrderRepository;
import andefi.furnish.product.model.Product;
import andefi.furnish.product.service.ProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderService {
  @Inject AccountService accountService;
  @Inject ProductService productService;
  @Inject ShippingAddressService shippingAddressService;

  @Inject OrderRepository orderRepository;

  public void createOrder(CreateOrderRequestBody payload) {
    Order order = new Order();

    Account account = accountService.getAccountById(payload.getAccountId());
    order.setAccount(account);

    List<OrderItem> orderItems =
        payload.getOrderItems().stream()
            .map(
                it -> {
                  Product product = productService.getProductById(it.getProductId());

                  OrderItem orderItem = new OrderItem();
                  orderItem.setOrder(order);
                  orderItem.setProduct(product);
                  orderItem.setCurrentUnitPrice(product.getPrice());
                  orderItem.setCurrentDiscountPercentage(
                      product.getDiscountPercentage().doubleValue());
                  orderItem.setQuantity(it.getQuantity());

                  return orderItem;
                })
            .toList();

    order.setOrderItems(orderItems);

    BigDecimal amount =
        orderItems.stream()
            .map(
                it ->
                    BigDecimal.valueOf(1 - it.getCurrentDiscountPercentage())
                        .multiply(it.getCurrentUnitPrice())
                        .multiply(BigDecimal.valueOf(it.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    order.setAmount(amount);

    Payment payment = new Payment();
    payment.setOrder(order);

    order.setPayment(payment);

    ShippingAddress shippingAddress =
        shippingAddressService
            .getShippingAddressById(payload.getShippingAddressId())
            .orElseThrow(NotFoundException::new);

    order.setShippingAddress(shippingAddress);

    orderRepository.persist(order);
  }

  public List<GetOrderResponse> getOrdersByAccountId(UUID id) {
    Account account = accountService.getAccountById(id);

    List<Order> orders = orderRepository.find("account.id", account.getId()).list();

    return orders.stream()
        .map(
            o -> {
              List<GetOrderItemResponse> orderItems =
                  o.getOrderItems().stream()
                      .map(
                          oi -> {
                            GetOrderItemResponse orderItem = new GetOrderItemResponse();

                            orderItem.setId(oi.getId());
                            orderItem.setProductId(oi.getProduct().getId());
                            orderItem.setProductName(oi.getProduct().getName());
                            orderItem.setProductCurrentUnitPrice(oi.getCurrentUnitPrice());
                            orderItem.setProductCurrentDiscountPercentage(
                                oi.getCurrentDiscountPercentage());
                            orderItem.setQuantity(oi.getQuantity());

                            return orderItem;
                          })
                      .toList();

              GetOrderResponse order = new GetOrderResponse();

              order.setId(o.getId());
              order.setOrderItems(orderItems);
              order.setAmount(o.getAmount());
              order.setShippingAddress(o.getShippingAddress().getAddress());
              order.setCreatedAt(o.getCreatedAt());
              order.setPaidAt(o.getPaidAt());
              order.setShippingAt(o.getShippingAt());
              order.setCompletedAt(o.getCompletedAt());
              order.setOrderStatus(o.getStatus().toString());

              return order;
            })
        .toList();
  }
}
