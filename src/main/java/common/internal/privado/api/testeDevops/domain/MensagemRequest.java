package common.internal.privado.api.testeDevops.domain;

public class MensagemRequest {
  private String mensagem;

  public MensagemRequest() {}

  public MensagemRequest(String mensagem) {
    this.mensagem = mensagem;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }
}
