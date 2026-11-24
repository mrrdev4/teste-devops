package common.internal.privado.api.testeDevops.app.controller;

import common.internal.privado.api.testeDevops.domain.MensagemRequest;
import common.internal.privado.api.testeDevops.domain.MensagemResponse;
import common.internal.privado.api.testeDevops.services.MensagemService;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

  private final MensagemService service;

  public MensagemController(MensagemService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<MensagemResponse> criar(@RequestBody MensagemRequest payload) {
    MensagemResponse resposta = service.processar(payload);
    return ResponseEntity.ok(resposta);
  }

  @GetMapping
  public String hello() {
    return "Hello World!";
  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello World2!";
  }

  @GetMapping("/{id}")
  public ResponseEntity<MensagemResponse> buscar(@PathVariable UUID id) {
    // Mock temporário até termos persistência
    return ResponseEntity.ok(new MensagemResponse("Mock: você buscou o ID " + id));
  }
}
