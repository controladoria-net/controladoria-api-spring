package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;
import java.util.Map;

public record OtherResponseDto(
        @JsonPropertyDescription("Nome extraído do documento, se disponível") @JsonProperty(required = false) String name,

        @JsonPropertyDescription("Número do CPF extraído do documento no formato ###.###.###-##, se disponível") @JsonProperty(required = false) String cpf,

        @JsonPropertyDescription("Endereço extraído do documento, se disponível") @JsonProperty(required = false) AddressDto address,

        @JsonPropertyDescription("Órgão emissor extraído do documento, se disponível") @JsonProperty(required = false) String issuingBody,

        @JsonPropertyDescription("Data de emissão extraída do documento no formato AAAA-MM-DD, se disponível") @JsonProperty(required = false) LocalDate issueDate,

        @JsonPropertyDescription("Tipo do documento extraído, se identificado") @JsonProperty(required = false) String documentType,

        @JsonPropertyDescription("Outras informações relevantes extraídas do documento") @JsonProperty(required = false) Map<String, Object> additionalInformation) {
}
