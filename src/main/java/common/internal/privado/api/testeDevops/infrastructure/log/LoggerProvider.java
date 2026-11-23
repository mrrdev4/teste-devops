package common.internal.privado.api.testeDevops.infrastructure.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LoggerProvider {

  public static Logger getLogger(Class<?> clazz) {
    // Adiciona traceId se disponível
    String traceId = MDC.get("traceId");
    if (traceId != null) {
      MDC.put("traceId", traceId);
    }
    return LoggerFactory.getLogger(clazz);
  }
}
