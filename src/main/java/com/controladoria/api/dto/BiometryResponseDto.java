package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;

public record BiometryResponseDto(
                @JsonPropertyDescription("Nome completo do titular da biometria") @JsonProperty(required = true) String name,

                @JsonPropertyDescription("Número do CPF do titular da biometria no formato ###.###.###-##") @JsonProperty(required = true) String cpf,

                @JsonPropertyDescription("Número do título eleitoral do titular da biometria") @JsonProperty(required = true) String voterTitle,

                @JsonPropertyDescription("Município onde a biometria foi registrada") @JsonProperty(required = false) String municipality,

                @JsonPropertyDescription("Estado onde a biometria foi registrada") @JsonProperty(required = false) String state,

                @JsonPropertyDescription("Data de emissão da biometria no formato AAAA-MM-DD") @JsonProperty(required = false) LocalDate issueDate,

                @JsonPropertyDescription("Zona eleitoral associada à biometria") @JsonProperty(required = false) String zone,

                @JsonPropertyDescription("Seção eleitoral associada à biometria") @JsonProperty(required = false) String section,

                @JsonPropertyDescription("Situação da biometria, geralmente vem acompanhada da frase 'Seu título eleitorral está ...'") @JsonProperty(required = true) String status) {
}
