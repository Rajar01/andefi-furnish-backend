package andefi.furnish.account.service;

import andefi.furnish.account.model.Account;
import andefi.furnish.account.repository.AccountRepository;
import andefi.furnish.common.exception.DuplicationException;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.AuthenticationFailedException;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.vertx.redis.client.Redis;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class AccountService {
  @Inject JwtTokenService jwtTokenService;

  @Inject EmailService emailService;

  @Inject AccountRepository accountRepository;

  @Inject JWTParser jwtParser;

  @Inject Redis redis;

  public Account getAccountByEmail(String email) {
    return accountRepository.findByEmail(email).orElseThrow(NotFoundException::new);
  }

  public void createNewAccount(String email, String username, String password, String role) {
    Account account = new Account();

    // Check if email or username present, if present throw error
    if (accountRepository.findByEmail(email).isPresent()
        || accountRepository.findByUsername(username).isPresent()) {
      throw new DuplicationException(null);
    }

    account.setEmail(email);
    account.setUsername(username);
    account.setPassword(BcryptUtil.bcryptHash(password));

    if (role != null && !role.isBlank()) {
      account.setRole(role);
    }

    accountRepository.persist(account);

    emailService.sendVerificationLink(email, username);
  }

  public String authenticate(String email, String password) {
    AtomicReference<String> token = new AtomicReference<>("");

    Optional<Account> account = accountRepository.findByEmail(email);

    if (account.isEmpty()) {
      throw new NotFoundException();
    }

    account.ifPresent(
        it -> {
          if (BcryptUtil.matches(password, it.getPassword())) {
            token.set(jwtTokenService.generateToken(it.getEmail(), it.getUsername(), it.getRole()));
          } else {
            throw new AuthenticationFailedException();
          }
        });

    return token.get();
  }

  public void verifyEmail(String token) throws ParseException {
    JsonWebToken jwt = jwtParser.parse(token);

    if (jwtTokenService.isTokenExist(jwt.getTokenID())) {
      throw new ForbiddenException();
    }

    Duration ttl = Duration.between(Instant.now(), Instant.ofEpochSecond(jwt.getExpirationTime()));

    if (!ttl.isNegative() || !ttl.isZero()) {
      jwtTokenService.storeToken(jwt.getTokenID(), ttl.getSeconds());
    }

    Optional<Account> account =
        accountRepository.findByEmail(jwt.getClaim(Claims.email).toString());

    if (account.isEmpty()) {
      throw new NotFoundException();
    }

    account.get().setVerifiedAt(Instant.now());

    accountRepository.persist(account.get());
  }

  public void requestPasswordReset(String email) {
    Optional<Account> account = accountRepository.findByEmail(email);

    if (account.isEmpty()) {
      throw new NotFoundException();
    }

    account.ifPresent(it -> emailService.sendPasswordResetLink(it.getEmail(), it.getUsername()));
  }

  public void validatePasswordResetLink(String token) throws ParseException {
    JsonWebToken jwt = jwtParser.parse(token);

    if (jwtTokenService.isTokenExist(jwt.getTokenID())) {
      throw new ForbiddenException();
    }

    Duration ttl = Duration.between(Instant.now(), Instant.ofEpochSecond(jwt.getExpirationTime()));

    if (!ttl.isNegative() || !ttl.isZero()) {
      jwtTokenService.storeToken(jwt.getTokenID(), ttl.getSeconds());
    }
  }

  public void resetPassword(String token, String password) throws ParseException {
    JsonWebToken jwt = jwtParser.parse(token);

    if (!jwtTokenService.isTokenExist(jwt.getTokenID())) {
      throw new ForbiddenException();
    }

    Optional<Account> account =
        accountRepository.findByEmail(jwt.getClaim(Claims.email).toString());

    if (account.isEmpty()) {
      throw new NotFoundException();
    }

    account.ifPresent(
        it -> {
          it.setPassword(BcryptUtil.bcryptHash(password));

          accountRepository.persist(account.get());
        });
  }
}
