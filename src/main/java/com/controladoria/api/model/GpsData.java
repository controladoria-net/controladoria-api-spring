package com.controladoria.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_dados_gps")
public class GpsData {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "documento_id", unique = true)
    private Document document;

    @Column(name = "codigo_pagamento")
    private String paymentCode;

    private String competencia;

    private java.math.BigDecimal valor;

    @Column(name = "data_pagamento")
    private LocalDate paymentDate;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}

