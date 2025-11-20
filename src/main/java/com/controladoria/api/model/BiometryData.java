package com.controladoria.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_dados_biometria")
public class BiometryData {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "documento_id", unique = true)
    private Document document;

    @Column(name = "situacao_biometria")
    private String biometryStatus;

    private String titulo_eleitor;
    private String municipio;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
