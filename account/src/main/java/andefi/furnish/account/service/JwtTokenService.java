package andefi.furnish.account.service;

import andefi.furnish.account.model.Account;
import andefi.furnish.account.properties.JwtProperties;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import org.eclipse.microprofile.jwt.Claims;

@Singleton
public class JwtTokenService {
  private final ValueCommands<String, String> valueCommands;
  private final ReactiveKeyCommands<String> keyCommands;

  @Inject AccountService accountService;

  @Inject JwtProperties properties;

  public JwtTokenService(RedisDataSource ds, ReactiveRedisDataSource reactive) {
    this.valueCommands = ds.value(String.class);
    this.keyCommands = reactive.key();
  }

  public String generateToken(String email, String username, String role) {
    Account account = accountService.getAccountByEmail(email);

    Instant now = Instant.now();

    JwtClaimsBuilder jwtClaimsBuilder =
        Jwt.issuer(properties.issuer)
            .subject(account.getId().toString())
            .issuedAt(now)
            .expiresAt(now.plus(properties.expirationDuration, properties.expirationTimeUnit))
            .claim(Claims.email.name(), email)
            .claim(Claims.preferred_username.name(), username);

    if (role != null && !role.isEmpty()) {
      jwtClaimsBuilder.groups(new HashSet<>(Collections.singletonList(role)));
    }

    return jwtClaimsBuilder.sign();
  }

  public void storeToken(String jti, long ttlInSeconds) {
    String key = properties.tokenUsedPrefix + jti;

    valueCommands.setex(key, ttlInSeconds, "");
  }

  public boolean isTokenExist(String jti) {
    String key = properties.tokenUsedPrefix + jti;

    return keyCommands.exists(key).await().indefinitely();
  }
}
