package common.internal.privado.api.testeDevops.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import common.internal.privado.api.testeDevops.domain.MensagemRequest;
import common.internal.privado.api.testeDevops.domain.MensagemResponse;
import org.junit.jupiter.api.Test;

class MensagemServiceTest {

  private final MensagemService service = new MensagemService();

  @Test
  void deveProcessarMensagemComSucesso() {
    MensagemRequest request = new MensagemRequest("Olá mundo");

    MensagemResponse response = service.processar(request);

    assertEquals("Você falou 'Olá mundo'", response.getResposta());
  }
}
