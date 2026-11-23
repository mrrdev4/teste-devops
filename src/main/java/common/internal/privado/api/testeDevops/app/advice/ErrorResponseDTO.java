package common.internal.privado.api.testeDevops.app.advice;

import java.time.Instant;

public record ErrorResponseDTO(
    Instant timestamp, int status, String error, String message, String path) {}
