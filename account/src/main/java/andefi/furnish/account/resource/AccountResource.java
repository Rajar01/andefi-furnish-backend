package andefi.furnish.account.resource;

import andefi.furnish.account.payload.AuthenticationRequestBody;
import andefi.furnish.account.payload.CreateNewAccountRequestBody;
import andefi.furnish.account.payload.PasswordResetRequestBody;
import andefi.furnish.account.payload.RequestPasswordResetRequestBody;
import andefi.furnish.account.service.AccountService;
import andefi.furnish.common.exception.DuplicationException;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/accounts")
public class AccountResource {
  @Inject AccountService accountService;

  @POST
  @Path("/register")
  @PermitAll
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response register(@Valid CreateNewAccountRequestBody payload) {
    try {
      accountService.createNewAccount(
          payload.getEmail(), payload.getUsername(), payload.getPassword(), null);

      return Response.status(Response.Status.CREATED).build();
    } catch (DuplicationException e) {
      return Response.status(Response.Status.CONFLICT).build();
    }
  }

  @POST
  @Path("/login")
  @PermitAll
  @Consumes(MediaType.APPLICATION_JSON)
  public Response authentication(@Valid AuthenticationRequestBody payload) {
    String token = accountService.authenticate(payload.getEmail(), payload.getPassword());

    return Response.ok(token).build();
  }

  @GET
  @Path("/verify")
  @PermitAll
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response emailVerification(@QueryParam("token") String token) {
    try {
      accountService.verifyEmail(token);

      return Response.ok().build();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  @POST
  @Path("/reset-password/request")
  @PermitAll
  @Consumes(MediaType.APPLICATION_JSON)
  public Response requestPasswordReset(@Valid RequestPasswordResetRequestBody payload) {
    accountService.requestPasswordReset(payload.getEmail());

    return Response.ok().build();
  }

  @GET
  @Path("/reset-password/verify")
  @PermitAll
  @Consumes(MediaType.APPLICATION_JSON)
  public Response validatePasswordResetLink(@QueryParam("token") String token) {
    try {
      accountService.validatePasswordResetLink(token);

      return Response.ok().build();
    } catch (ParseException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @POST
  @Path("/reset-password")
  @PermitAll
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response resetPassword(
      @QueryParam("token") String token, @Valid PasswordResetRequestBody payload) {
    try {
      accountService.resetPassword(token, payload.getPassword());

      return Response.ok().build();
    } catch (ParseException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }
}
