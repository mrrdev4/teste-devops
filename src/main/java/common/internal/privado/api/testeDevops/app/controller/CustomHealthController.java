package common.internal.privado.api.testeDevops.app.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomHealthController implements HealthIndicator {
  @Override
  @GetMapping("/healthz")
  public Health health() {
    // TODO your code here to validate the health of your service
    boolean healthy = true;
    if (healthy) {
      return Health.up().withDetail("custom", "ok2").build();
    } else {
      return Health.down().withDetail("custom", "fail").build();
    }
  }
}
