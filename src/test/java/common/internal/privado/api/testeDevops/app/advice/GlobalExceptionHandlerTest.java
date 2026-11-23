package common.internal.privado.api.testeDevops.app.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.NoHandlerFoundException;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  void deveRetornarErroInternoComErrorId() {
    RuntimeException ex = new RuntimeException("falha inesperada");

    ResponseEntity<Map<String, Object>> response = handler.handleGeneric(ex);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals("Erro interno. Contate o suporte com o código de erro.", body.get("message"));
    assertEquals(500, body.get("status"));

    String errorId = (String) body.get("errorId");
    assertNotNull(errorId);
    UUID.fromString(errorId);
  }

  @Test
  void deveRetornarNotFoundParaEndpointInexistente() {
    NoHandlerFoundException ex =
        new NoHandlerFoundException("GET", "/nao-existe", new HttpHeaders());

    ResponseEntity<Map<String, Object>> response = handler.handleNotFound(ex);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals("Endpoint não encontrado", body.get("message"));
    assertEquals(404, body.get("status"));
  }
}
