package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;
import java.util.List;

public record CnisResponseDto(
        @JsonPropertyDescription("Nome completo do trabalhador") @JsonProperty(required = true) String name,

        @JsonPropertyDescription("Número do CPF do trabalhador no formato ###.###.###-##") @JsonProperty(required = true) String cpf,

        @JsonPropertyDescription("Data de nascimento do trabalhador no formato AAAA-MM-DD") @JsonProperty(required = true) LocalDate birthDate,

        @JsonPropertyDescription("Número de inscrição do trabalhador no CNIS") @JsonProperty(required = true) String registrationNumber,

        @JsonPropertyDescription("Tipo de inscrição do trabalhador no CNIS (NIS, PIS, PASEP)") @JsonProperty(required = true) String registrationType, // Literal["NIS",
                                                                                                                                                       // "PIS",
                                                                                                                                                       // "PASEP"]

        @JsonPropertyDescription("Nome completo da mãe do trabalhador") @JsonProperty(required = false) String motherName,

        @JsonPropertyDescription("Indica se o trabalhador está ativo no CNIS, é baseado se há algum vínculo empregatício vigente") @JsonProperty(required = true) boolean active,

        @JsonPropertyDescription("Lista de vínculos empregatícios do trabalhador") @JsonProperty(required = true) List<CnisBondDto> bonds) {
    public record CnisBondDto(
            @JsonPropertyDescription("CNPJ da empresa vínculada ao trabalhador, no formato ##.###.###/####-??") @JsonProperty(required = true) String cnpj,

            @JsonPropertyDescription("Razão social da empresa vínculada ao trabalhador") @JsonProperty(required = true) String companyName,

            @JsonPropertyDescription("Data de início do vínculo empregatício, no formato AAAA-MM-DD") @JsonProperty(required = true) LocalDate admissionDate,

            @JsonPropertyDescription("Data de término do vínculo empregatício, no formato AAAA-MM-DD. Pode ser nulo se o vínculo ainda estiver ativo.") @JsonProperty(required = false) LocalDate dismissalDate,

            @JsonPropertyDescription("Categoria do trabalhador no vínculo empregatício, ex: Empregado, Estagiário, etc.") @JsonProperty(required = true) String category,

            @JsonPropertyDescription("Remuneração média mensal recebida pelo trabalhador no vínculo empregatício. Pode ser nulo se a informação não estiver disponível.") @JsonProperty(required = false) Double averageRemuneration,

            @JsonPropertyDescription("Situação atual do vínculo empregatício do trabalhador (ATIVO, INATIVO, SUSPENSO, OUTRO)") @JsonProperty(required = true) String status // Literal["ATIVO",
                                                                                                                                                                             // "INATIVO",
                                                                                                                                                                             // "SUSPENSO",
                                                                                                                                                                             // "OUTRO"]
    ) {
    }
}
