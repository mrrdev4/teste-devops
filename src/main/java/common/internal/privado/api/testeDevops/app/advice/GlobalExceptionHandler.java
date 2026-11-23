package common.internal.privado.api.testeDevops.app.advice;

import common.internal.privado.api.testeDevops.infrastructure.log.LoggerProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerProvider.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, Object>> handleResponseStatusException(
      ResponseStatusException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", ex.getReason());
    response.put("status", ex.getStatusCode().value());

    return ResponseEntity.status(ex.getStatusCode()).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
    String errorId = UUID.randomUUID().toString();
    logger.error("Erro interno [{}]: {}", errorId, ex.getMessage(), ex);

    Map<String, Object> response = new HashMap<>();
    response.put("errorId", errorId);
    response.put("message", "Erro interno. Contate o suporte com o código de erro.");
    response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Endpoint não encontrado");
    response.put("status", HttpStatus.NOT_FOUND.value());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }
}
