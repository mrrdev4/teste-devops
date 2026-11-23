# ☕ API Java Template
![workload](https://img.shields.io/badge/type-api-blue)
![language](https://img.shields.io/badge/language-java21-orange)
![framework](https://img.shields.io/badge/springboot-3.2.4-6DB33F)
![observability](https://img.shields.io/badge/otel-enabled-brightgreen)
![rest](https://img.shields.io/badge/api-restful-green)
![template](https://img.shields.io/badge/template-ready-yellow)

Este projeto é um **template base para APIs Java** com Spring Boot 3.2.4 e Java 21, pronto para observabilidade com OpenTelemetry, estrutura limpa com DDD, rastreamento com `traceId`, e logs estruturados em JSON.

---

## 📦 Features

- API REST seguindo boas práticas com Spring Web
- Estrutura baseada em DDD (Domain-Driven Design)
- Log estruturado com suporte a `traceId`
- Exceções globais tratadas com `@ControllerAdvice`
- Observabilidade nativa com OpenTelemetry (OTLP)
- Health check customizado (`/healthz`)
- Documentação automática com Swagger/OpenAPI (Springdoc)
- Exemplo de testes unitários com JUnit
- Pronto para deployment com Docker e GitHub Actions

## Estrutura

```text
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── vtal/
│   │           └── app/
│   │               ├── advice/
│   │               ├── config/
│   │               ├── controller/
│   │               ├── domain/
│   │               ├── infrastructure/
│   │               └── services/
│   └── resources/
│       ├── application.yml
│       └── logback-spring.xml
└── test/
```

---

## ⚙️ Requisitos
- Java 21
- Maven 3.9+
- OTEL Collector local (opcional, recomendado)

### Pre-commit

**1. Instale o pre-commit (caso ainda não tenha):**

```bash
pip install pre-commit
```

**2. Execute os hooks de pre-commit:**

```bash
pre-commit run --all-files > .pre-commit.log
```

O resultado da validação estará disponível no arquivo `.pre-commit.log`.

> **Dica:**
> - Sempre rode o pre-commit antes de abrir um pull request.
> - Consulte o log para detalhes sobre eventuais falhas ou sugestões.

---

## 📡 Endpoints REST

```http
POST /api/v1/mensagens
Content-Type: application/json
```

```json
{
  "mensagem": "Olá mundo"
}
```

Envia uma nova mensagem.

```http
GET /api/v1/mensagens/{id}
```

Retorna a mensagem associada a um ID (mockado por padrão).

---

## 🚀 Como executar

Configure as variáveis de ambiente para o runtime local:

```powershell
$env:SERVER_PORT = "8080"
$env:ENVIRONMENT = "local"
$env:API_NAME = "minha-api-java"
$env:OTEL_ENDPOINT = "http://localhost:4317"

mvn clean spring-boot:run
```

---

## 🐳 Docker + Observabilidade
O projeto acompanha:
- `Dockerfile` para build da aplicação
- scripts OTEL em `scripts/otel/`
- integração com OpenTelemetry (OTLP)
- logging estruturado via Logback + MDC

---

## 🧪 Testes

### Como executar os testes

Execute os comandos a partir da raiz do projeto.

1. Rodar todos os testes:

```sh
mvn test
```

2. Gerar pacote da aplicação:

```sh
mvn -DskipTests package
```

3. Rodar uma classe de teste específica:

```sh
mvn -Dtest=MensagemControllerTest test
```

---

## 📑 Documentação de API (Swagger/OpenAPI)

O template já está pronto para integração com o Springdoc OpenAPI.

Após rodar a aplicação, acesse:
- Swagger UI (via Browser): `http://localhost:8080/api/v1/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/api/v1/v3/api-docs`

Você pode customizar a documentação usando anotações como `@Operation`, `@Parameter`, `@Schema` nos controllers e DTOs.

---



## Conjur Secret Manager (segurança)

Sua aplicação suporta integração com o Conjur para recuperação de segredos usando token de sidecar.

### Configuração

```dotenv
CONJUR_PATH_TOKEN=/var/run/secrets/tokens/access-token
```

Também é possível configurar via propriedade Spring:

```yaml
conjur:
  tokenPath: /var/run/secrets/tokens/access-token
```

### Comportamento

- A aplicação não autentica no Conjur diretamente.
- A aplicação não mantém cache de token local.
- O token é lido do arquivo configurado em `conjur.tokenPath` (ex.: token injetado por sidecar).
- O serviço usa `Token.fromFile(...)` e `new Conjur(token)` para buscar segredo.
- O método disponível para consumo é `getSecret(String identifier)`.

### Módulo reutilizável (conjur)

- Serviço: `src/main/java/enterprise/command/publico/api/java/conjur/services/ConjurService.java`
- Testes: `src/test/java/enterprise/command/publico/api/java/conjur/services/ConjurServiceTest.java`
- Objetivo: centralizar a recuperação de segredos via token provido por sidecar.

### Como usar os exemplos do serviço

Com a API em execução (`mvn clean spring-boot:run`) e Conjur acessível:

1. Teste consulta de segredo (`getSecret`):

```java
String identifier = "ORG-CONJ-EXAMPLE/EXAMPLE-APP-DEV/username";
String secret = conjurService.getSecret(identifier);
```

Resposta esperada:
- retorna o valor do segredo para o `identifier` informado.

2. Teste recuperação de senha (`getSecret`):

```java
String identifier = "ORG-CONJ-EXAMPLE/EXAMPLE-APP-DEV/password";
String secret = conjurService.getSecret(identifier);
```

Resposta esperada:
- retorna o valor do segredo de senha para o `identifier` informado.

3. Validação de configuração ausente:

Se `conjur.tokenPath` / `CONJUR_PATH_TOKEN` não estiver configurado, o serviço retorna erro `500` com mensagem:

`CONJUR_PATH_TOKEN environment variable not set`

### Exemplo em serviço de negócio

```java
...
  private final ConjurService conjurService;

  public YourConstructor(ConjurService conjurService) {
    this.conjurService = conjurService;
  }

  public YourClass yourMethod() {
    String username = this.conjurService.getSecret("your-secret-user-name");
    String password = this.conjurService.getSecret("your-secret-user-password");
    System.out.println(username + " / " + password);
  }
```

Comportamento esperado:
- o serviço lê o token do arquivo configurado e consulta os segredos.
- autenticação e rotação/cache de token ficam sob responsabilidade do sidecar.

---

## 🤖 Copilot Skills

### create-endpoint

Este repositório possui uma skill do GitHub Copilot para acelerar a criação de novos endpoints seguindo o padrão do projeto.

- Skill: `.github/skills/create-endpoint.md`
- Objetivo: gerar endpoint completo em Java (controller + service + DTOs + validação + mapeamento de erro)

#### Como usar no Copilot Chat

Use um prompt como:

```text
Use a skill `create-endpoint` para criar o endpoint `POST /api/v1/mensagens`.

Detalhes:
- controllerName: `MensagemController`
- serviceName: `MensagemService`
- requestDto: { "conteudo": "string", "autor": "string" }
- responseDto: { "id": "string", "status": "string" }
- businessRules: conteudo obrigatório; autor obrigatório; rejeitar conteúdo vazio
- errorCases: 400 para payload inválido; 422 para regra de negócio; 500 para inesperado
```

#### Validação recomendada

```bash
mvn -DskipTests package
mvn test
```

### documentation-sync

Skill para sincronizar documentação após mudanças de código, reduzindo drift entre implementação e README.

- Skill: `.github/skills/documentation-sync.md`
- Objetivo: atualizar documentação de endpoints, contratos, erros, comandos e evidências.

#### Como usar no Copilot Chat

```text
Use a skill `documentation-sync` com:

- changeSummary: `Novo endpoint POST /my_endpoint com validação, regra de negócio e mapeamento 400/422/500.`
- scope: `api, errors, tests`
- targetFiles: `README.md`
- includeExamples: `true`
```

### architecture-orchestrator

Este repositório agora inclui um agente de arquitetura que:
- recebe um HLD (high-level definition),
- cruza com ADRs do projeto (fonte remota),
- e orquestra skills (como `create-endpoint`) com rastreabilidade.

#### Artefatos da skill
- Skill orquestradora: `.github/skills/architecture-orchestrator.md`
- Template de HLD: `docs/architecture/high-level-definition-template.md`
- ADRs remotas (fonte de verdade): `https://github.com/mrrdev4/vtal-architecture-playbook/blob/main/docs/04-decisions`

#### Uso rápido (você faz só isso)
1. Preencher HLD com objetivo, escopo, requisitos e critérios de aceite. Duplique o template.
2. Executar prompt com a skill `architecture-orchestrator`.
3. Revisar a saída com rastreabilidade: `HLD -> ADR -> mudanças -> evidências`.

#### O que a skill faz automaticamente
- Executa `./scripts/architecture/fetch-remote-adrs.ps1`.
- Navega recursivamente em `docs/04-decisions` e coleta ADRs `.md`.
- Mapeia ADRs aplicadas e descartadas para o caso.
- Decide e orquestra skills especializadas (ex.: `create-endpoint`) e monta evidências finais.

#### Prompt pronto (copiar e colar)

```text
Use a skill `architecture-orchestrator` com:

- highLevelDefinition: conteúdo de `docs/architecture/hld-my-endpoint.md`
- targetArea: `api`
- nonFunctionalRequirements: `latência p95 < 150ms; logs com traceId; tratamento de erro padronizado`

Retorne:
1) plano de execução,
2) mapeamento ADR,
3) delegação para skills,
4) evidências no formato `evidence`.
```

#### Script utilitário para ADR remota

Uso opcional para troubleshooting, inspeção manual ou auditoria local:

```powershell
./scripts/architecture/fetch-remote-adrs.ps1
```

Exemplos úteis:

```powershell
# manter clone temporário para inspeção local
./scripts/architecture/fetch-remote-adrs.ps1 -KeepClone

# saída JSON + export de manifesto
./scripts/architecture/fetch-remote-adrs.ps1 -OutputFormat json -ExportPath .tmp/adr-manifest.json
```

#### Formato de evidências esperado (orquestrador)

Use este formato de saída ao executar a skill `architecture-orchestrator`:

```text
evidence:
  adrsFetch:
    command: ./scripts/architecture/fetch-remote-adrs.ps1
    status: success|failed
    totalMarkdownFiles: <numero>
  adrsApplied:
    - id: ADR-XXXX
      link: <url>
      rationale: <motivo de aplicação>
  adrsDiscarded:
    - id: ADR-YYYY
      link: <url>
      reason: <motivo de não aplicação>
  traceability:
    hld: <resumo curto>
    changeset:
      - <arquivo>
      - <arquivo>
    tests:
      - <comando>: <resultado>
```
