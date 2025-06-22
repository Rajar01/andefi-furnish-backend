package andefi.furnish.order.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("midtrans")
public class MidtransProperties {
  public String serverKey;
  public boolean isProduction = false;
}
