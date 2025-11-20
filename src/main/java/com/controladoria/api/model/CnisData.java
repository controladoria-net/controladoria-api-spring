package com.controladoria.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_dados_cnis")
public class CnisData {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "documento_id", unique = true)
    private Document document;

    @Column(name = "nit_pis_pasep")
    private String nitPisPasep;

    @Column(name = "segurado_especial_ativo")
    private Boolean isSpecialFisherActive;

    @Column(name = "periodo_defeso_completo")
    private Boolean defesoPeriodCompleted;

    @Column(name = "data_inicio_segurado")
    private LocalDate activityStartDate;

    @Column(name = "data_fim_segurado")
    private LocalDate activityEndDate;

    @Column(name = "outros_vinculos_encontrados")
    private String otherLinksFound;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}

