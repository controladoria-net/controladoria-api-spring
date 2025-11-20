package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;
import java.util.List;

public record CaepfResponseDto(
                @JsonPropertyDescription("Nome completo do titular do CAEPF") @JsonProperty(required = true) String name,

                @JsonPropertyDescription("Número do CPF do titular do CAEPF no formato ###.###.###-##") @JsonProperty(required = true) String cpf,

                @JsonPropertyDescription("Número do CAEPF") @JsonProperty(required = true) String caepfNumber,

                @JsonPropertyDescription("Código(s) CNAE associado(s) ao CAEPF") @JsonProperty(required = true) List<String> cnae, // Python
                                                                                                                                   // says
                                                                                                                                   // str
                                                                                                                                   // |
                                                                                                                                   // list[str],
                                                                                                                                   // mapping
                                                                                                                                   // to
                                                                                                                                   // List<String>
                                                                                                                                   // for
                                                                                                                                   // safety

                @JsonPropertyDescription("Data de inscrição no CAEPF no formato AAAA-MM-DD") @JsonProperty(required = true) LocalDate registrationDate,

                @JsonPropertyDescription("Descrição da atividade econômica exercida") @JsonProperty(required = true) String economicActivity,

                @JsonPropertyDescription("Qualificação do titular do CAEPF") @JsonProperty(required = true) String qualification,

                @JsonPropertyDescription("Data de início da atividade econômica no formato AAAA-MM-DD") @JsonProperty(required = true) LocalDate activityStartDate,

                @JsonPropertyDescription("Situação cadastral do CAEPF") @JsonProperty(required = true) String registrationStatus, // Literal["ATIVO",
                                                                                                                                  // "INATIVO",
                                                                                                                                  // "SUSPENSO",
                                                                                                                                  // "CANCELADO",
                                                                                                                                  // "BAIXADO",
                                                                                                                                  // "OUTRO"]

                @JsonPropertyDescription("Endereço do titular do CAEPF") @JsonProperty(required = true) AddressDto address) {
}
