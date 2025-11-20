package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record AddressDto(
        @JsonPropertyDescription("CEP do endereço contido no documento, ex: 01310-100") @JsonProperty(required = false) String zipCode,

        @JsonPropertyDescription("Nome da rua contida no documento, ex: Avenida Paulista") @JsonProperty(required = true) String street,

        @JsonPropertyDescription("Número do endereço contido no documento, ex: 1000") @JsonProperty(required = false) String number,

        @JsonPropertyDescription("Complemento do endereço, ex: bloco A, apartamento 101") @JsonProperty(required = false) String complement,

        @JsonPropertyDescription("Bairro do endereço contido no documento, ex: Bela Vista") @JsonProperty(required = true) String neighborhood,

        @JsonPropertyDescription("Cidade do endereço contido no documento, ex: São Paulo") @JsonProperty(required = true) String city,

        @JsonPropertyDescription("Sigla do estado do endereço contido no documento (ex: SP, RJ)") @JsonProperty(required = true) String state) {
}
