package common.internal.privado.api.testeDevops.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

class CustomHealthControllerTest {

  private final CustomHealthController controller = new CustomHealthController();

  @Test
  void deveRetornarHealthUp() {
    Health health = controller.health();

    assertEquals(Status.UP, health.getStatus());
    assertEquals("ok", health.getDetails().get("custom"));
  }
}
