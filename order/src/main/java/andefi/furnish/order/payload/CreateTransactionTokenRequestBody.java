package andefi.furnish.order.payload;

import andefi.furnish.account.model.Account;
import andefi.furnish.order.model.Order;
import andefi.furnish.order.model.ShippingAddress;

public class CreateTransactionTokenRequestBody {
  private Account account;
  private Order order;
  private ShippingAddress shippingAddress;

  public CreateTransactionTokenRequestBody() {}

  public CreateTransactionTokenRequestBody(
      Account account, Order order, ShippingAddress shippingAddress) {
    this.account = account;
    this.order = order;
    this.shippingAddress = shippingAddress;
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

  public ShippingAddress getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(ShippingAddress shippingAddress) {
    this.shippingAddress = shippingAddress;
  }
}
