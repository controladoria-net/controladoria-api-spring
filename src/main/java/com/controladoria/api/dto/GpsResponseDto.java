package com.controladoria.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;
import java.util.List;

public record GpsResponseDto(
                @JsonPropertyDescription("Nome completo do contribuinte") @JsonProperty(required = true) String name,

                @JsonPropertyDescription("Número do CPF do contribuinte no formato ###.###.###-##") @JsonProperty(required = true) String cpf,

                @JsonPropertyDescription("Mês e ano de competência da GPS no formato MM/YYYY") @JsonProperty(required = true) String competence,

                @JsonPropertyDescription("Data de vencimento da GPS no formato AAAA-MM-DD") @JsonProperty(required = true) LocalDate dueDate,

                @JsonPropertyDescription("Número do documento da GPS") @JsonProperty(required = true) String documentNumber,

                @JsonPropertyDescription("Observações adicionais presentes na GPS, se houver") @JsonProperty(required = false) String observations,

                @JsonPropertyDescription("Valor total a ser pago") @JsonProperty(required = true) Double totalValue,

                @JsonPropertyDescription("Lista de composições do documento") @JsonProperty(required = true) List<GpsCompositionDto> composition,

                @JsonPropertyDescription("Indica se há um comprovante de pagamento anexado à GPS") @JsonProperty(required = false) Boolean hasPaymentProof) {
        public record GpsCompositionDto(
                        @JsonPropertyDescription("Código da composição do documento") @JsonProperty(required = true) String code,

                        @JsonPropertyDescription("Denominação da composição do documento") @JsonProperty(required = true) String denomination,

                        @JsonPropertyDescription("Valor principal da composição do documento") @JsonProperty(required = true) Double principal,

                        @JsonPropertyDescription("Valor da multa da composição do documento") @JsonProperty(required = true) Double fine,

                        @JsonPropertyDescription("Valor dos juros da composição do documento") @JsonProperty(required = true) Double interest,

                        @JsonPropertyDescription("Valor total da composição do documento") @JsonProperty(required = true) Double total) {
        }
}
