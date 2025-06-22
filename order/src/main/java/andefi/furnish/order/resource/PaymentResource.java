package andefi.furnish.order.resource;

import andefi.furnish.order.payload.PaymentNotificationRequestBody;
import andefi.furnish.order.service.PaymentService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/payments")
public class PaymentResource {
  @Inject PaymentService paymentService;

  @POST
  @Path("/notification")
  @Transactional
  public Response notificationHandler(PaymentNotificationRequestBody payload) {
    return paymentService.notificationHandler(payload);
  }

  @Path("/recurring/notification")
  public void recurringNotificationHandler() {}
}
