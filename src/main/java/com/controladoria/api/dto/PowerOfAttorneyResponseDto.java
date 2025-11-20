package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.util.List;

public record PowerOfAttorneyResponseDto(
        @JsonPropertyDescription("Dados do outorgante da procuração") @JsonProperty(required = true) PowerOfAttorneyGrantorDto grantor,

        @JsonPropertyDescription("Dados do outorgado da procuração") @JsonProperty(required = true) List<PowerOfAttorneyGranteeDto> grantees) {
    public record PowerOfAttorneyGrantorDto(
            @JsonPropertyDescription("Nome completo do outorgante da procuração") @JsonProperty(required = false) String name,

            @JsonPropertyDescription("Número do CPF do outorgante no formato ###.###.###-##") @JsonProperty(required = false) String cpf,

            @JsonPropertyDescription("Profissão do outorgante da procuração") @JsonProperty(required = false) String profession,

            @JsonPropertyDescription("Estado civil do outorgante da procuração") @JsonProperty(required = false) String maritalStatus,

            @JsonPropertyDescription("Endereço completo do outorgante da procuração") @JsonProperty(required = true) AddressDto address,

            @JsonPropertyDescription("Número de telefone do outorgante da procuração") @JsonProperty(required = false) String phoneNumber) {
    }

    public record PowerOfAttorneyGranteeDto(
            @JsonPropertyDescription("Nome completo do outorgado da procuração") @JsonProperty(required = false) String name,

            @JsonPropertyDescription("Número do CPF do outorgado no formato ###.###.###-##") @JsonProperty(required = false) String cpf,

            @JsonPropertyDescription("Número da OAB do outorgado da procuração") @JsonProperty(required = false) String oabNumber) {
    }
}
