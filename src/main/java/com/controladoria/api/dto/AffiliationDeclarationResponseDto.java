package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;

public record AffiliationDeclarationResponseDto(
        @JsonPropertyDescription("Nome completo do titular da declaração de filiação") @JsonProperty(required = true) String name,

        @JsonPropertyDescription("Número do CPF do titular da declaração de filiação no formato ###.###.###-##") @JsonProperty(required = true) String cpf,

        @JsonPropertyDescription("Endereço completo do titular da declaração de filiação") @JsonProperty(required = true) AddressDto address,

        @JsonPropertyDescription("Nome da entidade à qual o titular está filiado") @JsonProperty(required = true) String entityName,

        @JsonPropertyDescription("CNPJ da entidade à qual o titular está filiado, no formato ##.###.###/####-??") @JsonProperty(required = false) String entityCnpj,

        @JsonPropertyDescription("Nome completo do representante legal da entidade") @JsonProperty(required = true) String representativeName,

        @JsonPropertyDescription("Número do CPF do representante legal da entidade no formato ###.###.###-##") @JsonProperty(required = false) String representativeCpf,

        @JsonPropertyDescription("Endereço completo da entidade à qual o titular está filiado") @JsonProperty(required = true) AddressDto entityAddress,

        @JsonPropertyDescription("Município onde a declaração de filiação foi emitida") @JsonProperty(required = true) String issueMunicipality,

        @JsonPropertyDescription("Delegacia onde a declaração de filiação foi emitida, se aplicável") @JsonProperty(required = false) String issuePoliceStation,

        @JsonPropertyDescription("Data de filiação do titular à entidade no formato AAAA-MM-DD") @JsonProperty(required = true) LocalDate affiliationDate,

        @JsonPropertyDescription("Data de emissão da declaração de filiação no formato AAAA-MM-DD") @JsonProperty(required = true) LocalDate issueDate) {
}
