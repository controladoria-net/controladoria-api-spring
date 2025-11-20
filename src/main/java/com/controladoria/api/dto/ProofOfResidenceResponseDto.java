package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;

public record ProofOfResidenceResponseDto(
                @JsonPropertyDescription("Nome completo do titular do comprovante de residência") @JsonProperty(required = true) String name,

                @JsonPropertyDescription("Número do CPF do titular do comprovante de residência no formato ###.###.###-##") @JsonProperty(required = false) String cpf,

                @JsonPropertyDescription("Endereço completo do titular do comprovante de residência") @JsonProperty(required = true) AddressDto address,

                @JsonPropertyDescription("Data de emissão do comprovante de residência no formato AAAA-MM-DD") @JsonProperty(required = false) LocalDate issueDate,

                @JsonPropertyDescription("Tipo do documento utilizado como comprovante de residência, ex: Conta de luz, Conta de água, etc.") @JsonProperty(required = false) String documentType,

                @JsonPropertyDescription("Nome da empresa ou entidade emissora do comprovante de residência") @JsonProperty(required = false) String issuer) {
}
