# DocPass

DocPass e um SaaS para centralizar dados pessoais e documentos. Usuarios cadastram uma unica vez e autorizam empresas a acessar essas informacoes via API segura.

## Tecnologias

- Java 21
- Quarkus 3
- REST API
- Hibernate ORM com Panache
- PostgreSQL
- Maven
- Docker
- Kafka
- MinIO (S3 compativel)
- JWT para usuarios
- API Key para empresas
- Vavr (Either/Option/Try)
- MapStruct
- OpenAPI (Swagger)
- Micrometer + Prometheus
- Health Checks (SmallRye Health)

## Arquitetura

Base do pacote: `br.com.docpass`

Pacotes principais:
- `dominio`
- `aplicacao`
- `adaptador.entrada`
- `adaptador.saida`
- `configuracao`
- `seguranca`
- `webhook`
- `observabilidade`

## Endpoints (v1)

API do usuario:
- `POST /api/v1/usuarios`
- `GET /api/v1/usuarios/{id}`
- `PUT /api/v1/usuarios/{id}`
- `POST /api/v1/documentos/enviar`
- `GET /api/v1/documentos/usuario/{usuarioId}`
- `POST /api/v1/consentimentos`
- `GET /api/v1/consentimentos/usuario/{usuarioId}`
- `POST /api/v1/empresas`

API empresarial:
- `GET /api-empresas/v1/perfil/{usuarioId}`
- `GET /api-empresas/v1/documentos/{usuarioId}`

## Documentacao da API

Swagger UI disponivel em:
- `/q/swagger-ui`

## Seguranca

- Usuarios autenticam via JWT.
- Empresas usam header `X-API-KEY`.
- Rate limiting: 100 requisicoes por minuto por empresa.

## Eventos Kafka

Topicos:
- `perfil_atualizado`
- `documento_enviado`
- `consentimento_concedido`

## Webhooks

- Consome eventos Kafka.
- Envia POST para webhooks cadastrados.
- Payload:
  - `{ "evento": "...", "usuarioId": "...", "timestamp": "..." }`

## Auditoria

Registra acessos a dados sensiveis com:
- usuarioId
- empresaId
- tipoAcesso
- endpoint
- timestamp
- ipRequisicao

## Observabilidade

Metrica:
- `quantidade_requisicoes_api`
- `tempo_resposta_api`
- `documentos_enviados_total`
- `consentimentos_concedidos_total`

Prometheus:
- `/q/metrics`

Health checks:
- `/q/health/live`
- `/q/health/ready`

## Como rodar

1. Suba dependencias:

```bash
docker compose up -d
```

2. Crie o bucket no MinIO (console em http://localhost:9001):
- Bucket: `docpass-docs`

3. Inicie o Quarkus:

```bash
./mvnw quarkus:dev
```

## Testes

```bash
./mvnw test
```
