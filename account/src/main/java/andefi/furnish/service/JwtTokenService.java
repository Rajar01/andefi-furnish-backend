package andefi.furnish.service;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;

@Singleton
public class JwtTokenService {
    private final ValueCommands<String, String> valueCommands;
    private final ReactiveKeyCommands<String> keyCommands;

    public JwtTokenService(RedisDataSource ds, ReactiveRedisDataSource reactive) {
        this.valueCommands = ds.value(String.class);
        this.keyCommands = reactive.key();
    }

    public String generateAuthenticationToken(String email, String username, String role) {
        Instant now = Instant.now();

        return Jwt.issuer("self")
                .subject(username)
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .groups(new HashSet<>(Collections.singletonList(role)))
                .claim(Claims.email.name(), email)
                .sign();
    }

    public String generateEmailVerificationToken(String email, String username) {
        Instant now = Instant.now();

        return Jwt.issuer("self")
                .subject(username)
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))
                .claim(Claims.email.name(), email)
                .sign();
    }

    public String generatePasswordResetToken(String email, String username) {
        Instant now = Instant.now();

        return Jwt.issuer("self")
                .issuedAt(now)
                .subject(username)
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))
                .claim(Claims.email.name(), email)
                .sign();
    }

    public void markTokenAsUsed(String jti, long ttlInSeconds) {
        String key = "used_jwt:" + jti;
        valueCommands.setex(key, ttlInSeconds, "used");
    }

    public boolean isTokenUsed(String jti) {
        String key = "used_jwt:" + jti;

        return keyCommands.exists(key).await().indefinitely();
    }
}
