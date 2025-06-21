package andefi.furnish.order.resource;

import andefi.furnish.order.payload.CreateOrderRequestBody;
import andefi.furnish.order.payload.GetOrderResponse;
import andefi.furnish.order.service.OrderService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

@Path("/api/orders")
@RequestScoped
public class OrderResource {
  @Inject OrderService orderService;

  @Inject
  @Claim(standard = Claims.sub)
  String accountId;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Authenticated
  @Transactional
  public Response createOrder(@Valid CreateOrderRequestBody payload) {
    payload.setAccountId(UUID.fromString(accountId));
    orderService.createOrder(payload);

    return Response.ok().build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authenticated
  public Response getOrders() {
    List<GetOrderResponse> response = orderService.getOrdersByAccountId(UUID.fromString(accountId));

    return Response.ok(response).build();
  }
}
