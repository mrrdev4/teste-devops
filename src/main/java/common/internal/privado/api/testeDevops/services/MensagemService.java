package common.internal.privado.api.testeDevops.services;

import common.internal.privado.api.testeDevops.domain.MensagemRequest;
import common.internal.privado.api.testeDevops.domain.MensagemResponse;
import org.springframework.stereotype.Service;

@Service
public class MensagemService {

  public MensagemResponse processar(MensagemRequest request) {
    // TODO your code here
    String resposta = String.format("Você falou '%s'", request.getMensagem());
    return new MensagemResponse(resposta);
  }
}
