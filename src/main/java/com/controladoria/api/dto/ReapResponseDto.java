package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.util.List;

public record ReapResponseDto(
        @JsonPropertyDescription("Lista de anos de REAP verificados no documento") @JsonProperty(required = true) List<Integer> verifiedYears,

        @JsonPropertyDescription("Lista de anos de REAP que estão faltando no documento") @JsonProperty(required = true) List<Integer> missingYears,

        @JsonPropertyDescription("Indica se o pescador possui REAPs para todos os anos obrigatórios (2021-2024)") @JsonProperty(required = false) Boolean complete) {
}
