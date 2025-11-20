package com.controladoria.api.dto;

import com.controladoria.api.model.DocumentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record DocumentClassificationResponseDto(
        @JsonPropertyDescription("Classificação do documento") @JsonProperty(required = true) DocumentType classification) {
}
