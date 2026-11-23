package common.internal.privado.api.testeDevops.infrastructure.telemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelemetryConfig {

  @Bean
  public OpenTelemetry openTelemetry() {
    OtlpGrpcSpanExporter otlpExporter =
        OtlpGrpcSpanExporter.builder()
            .setEndpoint(System.getenv().getOrDefault("OTEL_ENDPOINT", "http://localhost:4317"))
            .build();

    SdkTracerProvider tracerProvider =
        SdkTracerProvider.builder()
            .addSpanProcessor(BatchSpanProcessor.builder(otlpExporter).build())
            .build();

    return OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).build();
  }

  @Bean
  public Tracer tracer(OpenTelemetry openTelemetry) {
    return openTelemetry.getTracer("common.internal.privado.api.testeDevops");
  }
}
