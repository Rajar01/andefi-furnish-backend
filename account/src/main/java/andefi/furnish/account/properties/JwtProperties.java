package andefi.furnish.account.properties;

import java.time.temporal.ChronoUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public class JwtProperties {
  public String issuer = "self";
  public int expirationDuration = 10;
  public ChronoUnit expirationTimeUnit = ChronoUnit.MINUTES;
  public String tokenUsedPrefix = "used_token:";
}
