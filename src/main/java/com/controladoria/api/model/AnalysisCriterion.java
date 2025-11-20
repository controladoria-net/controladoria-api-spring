package com.controladoria.api.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_analise_criterio")
public class AnalysisCriterion {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "solicitacao_id")
    private Solicitation request;

    @Column(name = "criterio", nullable = false, length = 100)
    private String criterion;

    @Column(name = "aprovado", nullable = false)
    private Boolean approved;

    @Column(name = "descricao")
    private String description;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}

