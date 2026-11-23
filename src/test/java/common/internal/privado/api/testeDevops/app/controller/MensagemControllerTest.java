package common.internal.privado.api.testeDevops.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import common.internal.privado.api.testeDevops.domain.MensagemRequest;
import common.internal.privado.api.testeDevops.domain.MensagemResponse;
import common.internal.privado.api.testeDevops.services.MensagemService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class MensagemControllerTest {

  @Mock private MensagemService service;

  @InjectMocks private MensagemController controller;

  @Test
  void deveCriarMensagemComStatusOk() {
    MensagemRequest payload = new MensagemRequest("Teste");
    MensagemResponse expected = new MensagemResponse("Você falou 'Teste'");
    when(service.processar(payload)).thenReturn(expected);

    ResponseEntity<MensagemResponse> response = controller.criar(payload);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    MensagemResponse body = response.getBody();
    assertNotNull(body);
    assertEquals("Você falou 'Teste'", body.getResposta());
    verify(service).processar(payload);
  }

  @Test
  void deveBuscarMensagemMockPeloId() {
    UUID id = UUID.randomUUID();

    ResponseEntity<MensagemResponse> response = controller.buscar(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    MensagemResponse body = response.getBody();
    assertNotNull(body);
    assertEquals("Mock: você buscou o ID " + id, body.getResposta());
  }
}
