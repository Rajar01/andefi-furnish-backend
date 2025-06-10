package andefi.furnish.service;

import andefi.furnish.model.Account;
import andefi.furnish.repository.AccountRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.vertx.redis.client.Redis;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@ApplicationScoped
public class AccountService {
    @Inject
    JwtTokenService jwtTokenService;

    @Inject
    EmailService emailService;

    @Inject
    AccountRepository accountRepository;

    @Inject
    JWTParser jwtParser;

    @Inject
    Redis redis;

    public void createNewAccount(String email, String username, String password, String role) {
        Account account = new Account();

        if (accountRepository.findByEmail(email).isPresent() || accountRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException();
        }

        account.setEmail(email);
        account.setUsername(username);
        account.setPassword(BcryptUtil.bcryptHash(password));

        if (role != null) {
            account.setRole(role);
        }

        accountRepository.persist(account);

        emailService.sendVerificationLink(email, username);
    }

    public String authenticate(String email, String password) {
        String token = "";

        Optional<Account> account = accountRepository.findByEmail(email);

        if (account.isEmpty()) {
            throw new NotFoundException();
        }

        if (BcryptUtil.matches(password, account.get().getPassword())) {
            token = jwtTokenService.generateAuthenticationToken(account.get().getEmail(), account.get().getUsername(), account.get().getRole());
        } else {
            throw new UnauthorizedException();
        }

        return token;
    }

    public void verify(String token) throws ParseException {
        JsonWebToken jwt = jwtParser.parse(token);

        if (jwtTokenService.isTokenUsed(jwt.getTokenID())) {
            throw new ForbiddenException();
        }

        Duration ttl = Duration.between(Instant.now(), Instant.ofEpochSecond(jwt.getExpirationTime()));

        if (!ttl.isNegative() || !ttl.isZero()) {
            jwtTokenService.markTokenAsUsed(jwt.getTokenID(), ttl.getSeconds());
        }

        Optional<Account> account = accountRepository.findByEmail(jwt.getClaim(Claims.email).toString());

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

        emailService.sendPasswordResetLink(email, account.get().getUsername());
    }

    public void validatePasswordResetLink(String token) throws ParseException {
        JsonWebToken jwt = jwtParser.parse(token);

        if (jwtTokenService.isTokenUsed(jwt.getTokenID())) {
            throw new ForbiddenException();
        }

        Duration ttl = Duration.between(Instant.now(), Instant.ofEpochSecond(jwt.getExpirationTime()));

        if (!ttl.isNegative() || !ttl.isZero()) {
            jwtTokenService.markTokenAsUsed(jwt.getTokenID(), ttl.getSeconds());
        }
    }

    public void resetPassword(String token, String password) throws ParseException {
        JsonWebToken jwt = jwtParser.parse(token);

        // Check if token active, if not throw forbidden http status code
        if (!jwtTokenService.isTokenUsed(jwt.getTokenID())) {
            throw new ForbiddenException();
        }

        Optional<Account> account = accountRepository.findByEmail(jwt.getClaim(Claims.email).toString());

        if (account.isEmpty()) {
            throw new NotFoundException();
        }

        account.get().setPassword(BcryptUtil.bcryptHash(password));

        accountRepository.persist(account.get());
    }
}
