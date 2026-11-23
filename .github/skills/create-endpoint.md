---
name: create-endpoint
description: Cria um novo endpoint Spring Boot seguindo o padrão deste repositório (controller, service, Domains, validação e tratamento de erro)
---

# Skill: create-endpoint

## Objetivo
Criar um endpoint HTTP novo no padrão do projeto, com mudanças mínimas e sem inventar arquitetura.

## Entradas obrigatórias
- `method`: GET | POST | PUT | PATCH | DELETE
- `path`: rota HTTP (ex.: `/mensagens`)
- `controllerName`: nome do controller alvo (ou criar novo)
- `serviceName`: nome do serviço alvo (ou criar novo)
- `requestDomain`: estrutura do request (campos e tipos)
- `responseDomain`: estrutura do response (campos e tipos)
- `businessRules`: regras de negócio essenciais
- `errorCases`: lista de erros esperados (400/404/409/422/500)

## Entradas opcionais
- `headers`: headers obrigatórios
- `queryParams`: query params
- `pathParams`: path params
- `idempotency`: se exige comportamento idempotente
- `auth`: requisitos de autenticação/autorização

## Regras obrigatórias deste repositório
1. Não criar camadas ou padrões novos além dos já existentes.
2. Reutilizar tratamento global em `app/advice/GlobalExceptionHandler`.
3. Posicionar arquivos nestes pacotes:
   - Controller: `src/main/java/${{ARTIFACT_ID_SLASH}}/app/controller`
   - Service: `src/main/java/${{ARTIFACT_ID_SLASH}}/services`
   - Domains: `src/main/java/${{ARTIFACT_ID_SLASH}}/domain`
   - Advice/erros: `src/main/java/${{ARTIFACT_ID_SLASH}}/app/advice`
4. Preservar logging/telemetria/configuração já adotados no projeto.
5. Não alterar endpoints existentes sem necessidade explícita.

## Procedimento
1. Verificar se já existe controller/service compatível para reaproveitar.
2. Criar/atualizar Domains de request/response em `domain`.
3. Implementar método no service com regras de negócio informadas.
4. Implementar endpoint no controller com mapeamento correto (`@GetMapping`, `@PostMapping`, etc.).
5. Aplicar validações de entrada (Bean Validation quando aplicável).
6. Mapear falhas para exceções tratadas pelo `GlobalExceptionHandler`.
7. Garantir respostas HTTP e payloads aderentes ao contrato solicitado.
8. Atualizar documentação do endpoint (README/OpenAPI, se houver no projeto).
9. Criar/atualizar specs unitárias em `src/test/java` cobrindo cenários de sucesso e erro.

## Saída esperada
- Lista objetiva de arquivos criados/alterados.
- Resumo curto do endpoint criado.
- Comandos de validação executáveis.

## Checklist de validação
- Build compila sem erro.
- Endpoint responde status correto para sucesso e erros mapeados.
- Domains e validações refletem o contrato solicitado.
- Tratamento de erro segue padrão global.

## Comandos de validação
- `mvn -DskipTests package`
- `mvn test`

## Prompt de uso (exemplo)
Use a skill `create-endpoint` para criar o endpoint `POST /mensagens`.

Detalhes:
- controllerName: `MensagemController`
- serviceName: `MensagemService`
- requestDomain: `{ "conteudo": "string", "autor": "string" }`
- responseDomain: `{ "id": "string", "status": "string" }`
- businessRules: `conteudo obrigatório; autor obrigatório; rejeitar conteúdo vazio`
- errorCases: `400 para payload inválido; 422 para regra de negócio; 500 para inesperado`
