package andefi.furnish.account.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class EmailService {
  @Inject JwtTokenService jwtTokenService;

  @Inject Mailer mailer;

  @Context UriInfo uriInfo;

  public void sendVerificationLink(String email, String username) {
    String token = jwtTokenService.generateToken(email, username, null);
    String verificationLink = uriInfo.getBaseUri().toString() + "api/account/verify?token=" + token;

    Mail mail = new Mail();
    mail.setFrom("no-reply@andefi.furnish");
    mail.setTo(List.of(email));
    mail.setSubject("Verify your email address");
    mail.setText(verificationLink);
    mailer.send(mail);
  }

  public void sendPasswordResetLink(String email, String username) {
    String token = jwtTokenService.generateToken(email, username, null);
    String passwordResetLink =
        uriInfo.getBaseUri().toString() + "api/account/reset-password/verify?token=" + token;

    Mail mail = new Mail();
    mail.setFrom("no-reply@andefi.furnish");
    mail.setTo(List.of(email));
    mail.setSubject("Password reset");
    mail.setText(passwordResetLink);
    mailer.send(mail);
  }
}
