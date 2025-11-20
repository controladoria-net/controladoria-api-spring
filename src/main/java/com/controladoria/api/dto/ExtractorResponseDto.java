package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record ExtractorResponseDto(
        @JsonPropertyDescription("Metadados extraídos de um documento CNIS") @JsonProperty(required = false) CnisResponseDto cnis,

        @JsonPropertyDescription("Metadados extraídos de um documento RGP") @JsonProperty(required = false) RgpResponseDto rgp,

        @JsonPropertyDescription("Metadados extraídos de um documento CAEPF") @JsonProperty(required = false) CaepfResponseDto caepf,

        @JsonPropertyDescription("Metadados extraídos de um comprovante de residência") @JsonProperty(required = false) ProofOfResidenceResponseDto proofOfResidence,

        @JsonPropertyDescription("Metadados extraídos de um termo de representação") @JsonProperty(required = false) RepresentationTermResponseDto representationTerm,

        @JsonPropertyDescription("Metadados extraídos de um documento GPS") @JsonProperty(required = false) GpsResponseDto gps,

        @JsonPropertyDescription("Metadados extraídos de um documento de biometria") @JsonProperty(required = false) BiometryResponseDto biometry,

        @JsonPropertyDescription("Metadados extraídos de uma declaração de filiação") @JsonProperty(required = false) AffiliationDeclarationResponseDto affiliationDeclaration,

        @JsonPropertyDescription("Metadados extraídos de um documento não classificado") @JsonProperty(required = false) OtherResponseDto other) {
}
