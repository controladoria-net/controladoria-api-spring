package com.controladoria.api.dto;

import com.controladoria.api.model.DocumentType;

public record ClassificationDto(
        DocumentType classification,
        Double confidence,
        String reasoning) {
}
