package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;

public record RgpResponseDto(
        @JsonPropertyDescription("Tipo de licença do RGP (PROVISÓRIA, DEFINITIVA)") @JsonProperty(required = true) String licenseType,

        @JsonPropertyDescription("Nome completo do titular do RG") @JsonProperty(required = true) String name,

        @JsonPropertyDescription("Número do CPF do titular do RG no formato ###.###.###-##") @JsonProperty(required = false) String cpf,

        @JsonPropertyDescription("Número do Registro Geral de Pesca (RGP),") @JsonProperty(required = true) String rgpNumber,

        @JsonPropertyDescription("Categoria da atividade pesqueira autorizada pelo RGP") @JsonProperty(required = true) String category,

        @JsonPropertyDescription("Data de nascimento do titular do RG no formato AAAA-MM-DD") @JsonProperty(required = true) LocalDate birthDate,

        @JsonPropertyDescription("Data de emissão do RGP no formato AAAA-MM-DD") @JsonProperty(required = false) LocalDate issueDate,

        @JsonPropertyDescription("Data de validade do RGP no formato AAAA-MM-DD") @JsonProperty(required = false) LocalDate expirationDate,

        @JsonPropertyDescription("Órgão emissor do RGP") @JsonProperty(required = false) String issuingBody,

        @JsonPropertyDescription("Filiação do titular do RGP") @JsonProperty(required = false) String affiliation,

        @JsonPropertyDescription("Município de residência do titular do RGP") @JsonProperty(required = false) String residenceMunicipality) {
}
