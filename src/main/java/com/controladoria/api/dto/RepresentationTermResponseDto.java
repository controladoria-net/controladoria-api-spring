package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.util.List;

public record RepresentationTermResponseDto(
        @JsonPropertyDescription("Nome completo do titular do termo de representação, quem está sendo representado") @JsonProperty(required = true) String name,

        @JsonPropertyDescription("Estado civil do titular do termo de representação") @JsonProperty(required = true) String maritalStatus,

        @JsonPropertyDescription("Profissão do titular do termo de representação") @JsonProperty(required = true) String profession,

        @JsonPropertyDescription("Número do RG do titular do termo de representação") @JsonProperty(required = true) String rg,

        @JsonPropertyDescription("Número do CPF do titular do termo de representação no formato ###.###.###-##") @JsonProperty(required = true) String cpf,

        @JsonPropertyDescription("Endereço completo do titular do termo de representação") @JsonProperty(required = true) AddressDto address,

        @JsonPropertyDescription("Número de telefone do titular do termo de representação") @JsonProperty(required = false) String phoneNumber,

        @JsonPropertyDescription("Endereço de e-mail do titular do termo de representação") @JsonProperty(required = false) String email,

        @JsonPropertyDescription("Indica se o termo de representação está corretamente assinado pelo titular") @JsonProperty(required = true) boolean hasSignature,

        @JsonPropertyDescription("Lista de representantes legais autorizados no termo de representação") @JsonProperty(required = true) List<LegalRepresentativeDto> legalRepresentatives) {
    public record LegalRepresentativeDto(
            @JsonPropertyDescription("Nome completo do representante legal") @JsonProperty(required = true) String name,

            @JsonPropertyDescription("Órgão emissor da OAB do representante legal, ex: OAB/SP, OAB/MA") @JsonProperty(required = false) String oabIssuingBody,

            @JsonPropertyDescription("Número da OAB do representante legal") @JsonProperty(required = false) String oabNumber,

            @JsonPropertyDescription("Número do CPF do representante legal no formato ###.###.###-##") @JsonProperty(required = false) String cpf) {
    }
}
