package andefi.furnish.resource;

import andefi.furnish.payload.AuthenticationRequestBody;
import andefi.furnish.payload.CreateNewAccountRequestBody;
import andefi.furnish.payload.PasswordResetRequestBody;
import andefi.furnish.payload.RequestPasswordResetRequestBody;
import andefi.furnish.service.AccountService;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/api/account")
public class AccountResource {
    @Inject
    AccountService accountService;

    @POST
    @Path("/register")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response register(@Valid CreateNewAccountRequestBody payload) {
        try {
            accountService.createNewAccount(payload.getEmail(), payload.getUsername(), payload.getPassword(), null);
            return Response.status(Response.Status.CREATED).build();
        } catch (RuntimeException e) {
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
            accountService.verify(token);

            return Response.ok().build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Path("/reset-password/request")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public void requestPasswordReset(@Valid RequestPasswordResetRequestBody payload) {
        accountService.requestPasswordReset(payload.getEmail());
    }

    @GET
    @Path("/reset-password/verify")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public void validatePasswordResetLink(@QueryParam("token") String token) {
        try {
            accountService.validatePasswordResetLink(token);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Path("/reset-password")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response resetPassword(@QueryParam("token") String token, @Valid PasswordResetRequestBody payload) {
        try {
            accountService.resetPassword(token, payload.getPassword());

            return Response.ok().build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
