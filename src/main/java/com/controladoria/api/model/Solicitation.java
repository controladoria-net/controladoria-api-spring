package com.controladoria.api.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_solicitacao")
public class Solicitation {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Client client;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SolicitationStatus status = SolicitationStatus.NOVA_SOLICITACAO;

    @Column(name = "score_elegibilidade")
    private Integer eligibilityScore;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "request")
    private List<AnalysisCriterion> analysisCriteria;

    @OneToMany(mappedBy = "request")
    private List<Document> documents;
}
