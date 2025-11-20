package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record RegistrationDocumentResponseDto(
        @JsonPropertyDescription("Nome completo do titular do documento de registro") @JsonProperty(required = false) String name,

        @JsonPropertyDescription("Número do CPF do titular do documento de registro no formato ###.###.###-##") @JsonProperty(required = false) String cpf,

        @JsonPropertyDescription("Indica se o documento de registro foi encontrado") @JsonProperty(required = true) boolean documentExists) {
}
