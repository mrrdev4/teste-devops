package common.internal.privado.api.testeDevops.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

  private String environment;
  private String otelEndpoint;

  public String getEnvironment() {
    return environment;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }

  public String getOtelEndpoint() {
    return otelEndpoint;
  }

  public void setOtelEndpoint(String otelEndpoint) {
    this.otelEndpoint = otelEndpoint;
  }
}
