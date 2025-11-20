# Project Specification: ControladorIA (Backend API)

## 1. System Overview
**ControladorIA** is a document analysis system for a law firm specializing in "Seguro Defeso" (Fisherman Insurance). The system automates the classification of documents, extraction of specific data using Generative AI (Google Gemini), and eligibility analysis.

### Key Architectural Decisions
- **Language:** Java 21
- **Framework:** Spring Boot 3.3+ (WebFlux for SSE, Data JPA, Security)
- **AI Integration:** Spring AI (Google Gemini Starter)
- **Database:** PostgreSQL
- **Authentication:** AWS Cognito (JWT)
- **Document Storage:** AWS S3
- **Concurrency:** Virtual Threads / Async Events
- **Naming Convention:** - **Code (Classes/Methods):** English (e.g., `Client`, `Solicitation`).
  - **Database (Tables/Columns):** Portuguese (e.g., `tb_cliente`, `tb_solicitacao`).

## 2. Tech Stack & Dependencies

```xml
<java.version>21</java.version>
<dependency>spring-boot-starter-data-jpa</dependency>
<dependency>spring-boot-starter-webflux</dependency> <dependency>spring-boot-starter-security</dependency>
<dependency>spring-boot-starter-oauth2-resource-server</dependency>
<dependency>spring-ai-google-gemini-spring-boot-starter</dependency>
<dependency>postgresql</dependency>
<dependency>lombok</dependency>
```

## 3\. Database Schema (PostgreSQL)

**Important:** All UUIDs are generated automatically. `created_by` stores the Cognito Subject ID (`sub`).

```sql
-- ============================
-- EXTENSÕES E TIPOS AUXILIARES
-- ============================

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Enum para Tipos de Documentos
CREATE TYPE tipo_documento_enum AS ENUM (
    'CERTIFICADO_DE_REGULARIDADE', -- RGP
    'CAEPF',
    'CNIS',
    'TERMO_DE_REPRESENTACAO',
    'PROCURACAO',
    'GPS_E_COMPROVANTE',
    'BIOMETRIA',
    'DOCUMENTO_IDENTIDADE',
    'COMPROVANTE_RESIDENCIA',
    'REAP',
    'OUTRO'
);

-- Status da Solicitação
CREATE TYPE status_solicitacao_enum AS ENUM (
    'NOVA_SOLICITACAO',
    'PROCESSANDO',
    'ANALISADA'
);

-- Status de Processamento do Documento
CREATE TYPE status_processamento_documento_enum AS ENUM (
    'PENDENTE',
    'CLASSIFICADO',
    'EXTRAIDO',
    'ERRO'
);

-- ============================
-- 1. Tabela de Clientes
-- ============================

CREATE TABLE tb_cliente (
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome_completo   VARCHAR(255) NOT NULL,
    cpf             VARCHAR(11) UNIQUE NOT NULL,
    data_nascimento DATE,
    created_by      VARCHAR(255) NOT NULL, -- ID do usuário (advogado)
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================
-- 2. Tabela de Solicitações
-- ============================

CREATE TABLE tb_solicitacao (
    id                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    cliente_id        UUID NOT NULL REFERENCES tb_cliente(id),
    status            status_solicitacao_enum DEFAULT 'NOVA_SOLICITACAO',
    score_elegibilidade INTEGER, -- 0 a 100
    created_by        VARCHAR(255) NOT NULL,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================
-- 3. Histórico de Análise (Critérios)
-- ============================

CREATE TABLE tb_analise_criterio (
    id             UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    solicitacao_id UUID NOT NULL REFERENCES tb_solicitacao(id),
    criterio       VARCHAR(100) NOT NULL, -- "Validade RGP", "Vínculo CNIS", etc.
    aprovado       BOOLEAN NOT NULL,
    descricao      TEXT, -- "RGP válido até 2025", "Falta GPS Jan/23"
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================
-- 4. Documentos
-- ============================

CREATE TABLE tb_documento (
    id                      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    solicitacao_id          UUID NOT NULL REFERENCES tb_solicitacao(id),
    nome_arquivo            VARCHAR(255) NOT NULL,
    caminho_storage         VARCHAR(500), -- Caminho no S3/local
    classificacao           tipo_documento_enum, -- Preenchido pela IA
    status_processamento    status_processamento_documento_enum DEFAULT 'PENDENTE',
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===============================================================
-- TABELAS ESPECÍFICAS (OPÇÃO A - DADOS EXTRAÍDOS PELA IA)
-- Cada tabela referencia 1 documento (1:1) e guarda campos específicos
-- ===============================================================

-- CNIS
CREATE TABLE tb_dados_cnis (
    id                        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    documento_id              UUID NOT NULL REFERENCES tb_documento(id),
    nit_pis_pasep             VARCHAR(50),
    segurado_especial_ativo   BOOLEAN,
    periodo_defeso_completo   BOOLEAN,
    data_inicio_segurado      DATE,
    data_fim_segurado         DATE,
    outros_vinculos_encontrados TEXT,
    created_at                TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (documento_id)
);

-- Certificado de Regularidade / RGP
CREATE TABLE tb_dados_rgp (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    documento_id        UUID NOT NULL REFERENCES tb_documento(id),
    numero_rgp          VARCHAR(50),
    data_emissao        DATE,
    data_primeiro_registro DATE,
    situacao            VARCHAR(50),
    categoria           VARCHAR(100),
    municipio           VARCHAR(100),
    uf                  CHAR(2),
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (documento_id)
);

-- Comprovante de Residência
CREATE TABLE tb_dados_residencia (
    id                 UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    documento_id       UUID NOT NULL REFERENCES tb_documento(id),
    logradouro         VARCHAR(255),
    bairro             VARCHAR(100),
    cidade             VARCHAR(100),
    uf                 CHAR(2),
    cep                VARCHAR(20),
    data_emissao       DATE,
    entidade_emissora  VARCHAR(100),
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (documento_id)
);

-- GPS / Comprovante de Pagamento
CREATE TABLE tb_dados_gps (
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    documento_id     UUID NOT NULL REFERENCES tb_documento(id),
    codigo_pagamento VARCHAR(20),
    competencia      VARCHAR(20), -- "01/2024"
    valor            DECIMAL(10, 2),
    data_pagamento   DATE,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (documento_id)
);

-- Biometria
CREATE TABLE tb_dados_biometria (
    id                 UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    documento_id       UUID NOT NULL REFERENCES tb_documento(id),
    situacao_biometria VARCHAR(20), -- "REGULAR" ou "IRREGULAR"
    titulo_eleitor     VARCHAR(50),
    municipio          VARCHAR(100),
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (documento_id)
);

-- Procuração / Termo de Representação (estrutura genérica)
CREATE TABLE tb_dados_representacao (
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    documento_id     UUID NOT NULL REFERENCES tb_documento(id),
    nome_outorgado   VARCHAR(255),
    cpf_outorgado    VARCHAR(11),
    oab_outorgado    VARCHAR(20),
    data_assinatura  DATE,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (documento_id)
);

-- Documento de Identidade
CREATE TABLE tb_dados_identidade (
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    documento_id     UUID NOT NULL REFERENCES tb_documento(id),
    numero_rg        VARCHAR(30),
    orgao_expedidor  VARCHAR(50),
    cpf              VARCHAR(11),
    data_nascimento  DATE,
    nome_mae         VARCHAR(255),
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (documento_id)
);
```

## 5\. AI Prompts Configuration

Prompts are defined in `src/main/resources/prompts.yml` and loaded via `PromptConfig`.

**Class Structure:**

```java
@Configuration
@ConfigurationProperties(prefix = "")
public class PromptConfig {
    private List<PromptDefinition> prompts;
    // ... logic to convert List to Map<String, PromptDefinition> ...
}
```

**Application Config (`application.yml`):**

```yaml
spring:
  config:
    import: "classpath:prompts.yml"
  ai:
    google:
      gemini:
        api-key: ${GEMINI_API_KEY}
```

**Prompt Keys (must match `prompts.yml`):**
`DOCUMENT_CLASSIFIER`, `CNIS`, `CERTIFICADO_DE_REGULARIDADE`, `CAEPF`, `COMPROVANTE_RESIDENCIA`, `TERMO_DE_REPRESENTACAO`, `GPS_E_COMPROVANTE`, `BIOMETRIA`, `REAP`, `DOCUMENTO_IDENTIDADE`, `PROCURACAO`.

-----

## 6\. Logic & Workflow

### A. Upload & Async Orchestration

1.  **Endpoint:** `POST /api/solicitations`

      * Receives `MultipartFile[]` and Client ID.
      * Uploads files to Storage S3.
      * Saves `Document` entities with status `PENDING`.
      * Publishes `SolicitationCreatedEvent`.
      * **Returns 202 Accepted** with `solicitationId`.

2.  **Async Processor (`DocumentProcessor`):**

      * Listens to `SolicitationCreatedEvent`.
      * Iterates through documents.
      * **Step 1: Classify:** Call Gemini with `DOCUMENT_CLASSIFIER`. Update `Document.classification`.
      * **Step 2: Extract:** Based on classification (e.g., if CNIS), call Gemini with specific prompt key (`CNIS`). Convert JSON to DTO (e.g., `CnisDataDto`) using `BeanOutputConverter`.
      * **Step 3: Persist:** Save extracted data to specific table (`tb_dados_cnis`).
      * **Step 4: Notify:** Send SSE event.

### B. Real-time Feedback (SSE)

  * **Endpoint:** `GET /api/solicitations/{id}/stream`
  * **Events:**
      * `CLASSIFIED`: When a doc type is identified.
      * `EXTRACTED`: When data is saved.
      * `COMPLETED`: When all docs are processed.

-----

## 7\. AI Service Implementation Requirements

  * Must use `spring-ai-google-gemini-spring-boot-starter`.
  * Must use `BeanOutputConverter` to force structured JSON output from Gemini into Java Records (DTOs).
  * Must support Multimodal input (sending the PDF/Image to Gemini).

## 8\. Security

  * Validate JWT from Cognito.
  * Extract `sub` claim from JWT to populate `created_by` fields automatically (use a `AuditorAware` or explicit setting in Service layer).

## 9\. General Response DTO

- every endpoint in this application should follow the response bellow.

```java
public record ErrorDTO(String code, String message) {}

public interface GeneralResponseDTO<T> {
    Optional<ErrorDTO> error;
    Optional<T> data;
}
```

```