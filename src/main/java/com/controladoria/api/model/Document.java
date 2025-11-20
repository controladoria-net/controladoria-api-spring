package com.controladoria.api.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
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
@Table(name = "tb_documento")
public class Document {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "solicitacao_id")
    private Solicitation request;

    @Column(name = "nome_arquivo", nullable = false, length = 255)
    private String fileName;

    @Column(name = "caminho_storage", length = 500)
    private String storagePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao")
    private DocumentType classification;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status_processamento", nullable = false)
    private DocumentProcessingStatus processingStatus = DocumentProcessingStatus.PENDENTE;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    // One-to-one extracted data sections
    @OneToOne(mappedBy = "document")
    private CnisData cnisData;

    @OneToOne(mappedBy = "document")
    private RgpData rgpData;

    @OneToOne(mappedBy = "document")
    private ResidenceData residenceData;

    @OneToOne(mappedBy = "document")
    private GpsData gpsData;

    @OneToOne(mappedBy = "document")
    private BiometryData biometryData;

    @OneToOne(mappedBy = "document")
    private RepresentationData representationData;

    @OneToOne(mappedBy = "document")
    private IdentityData identityData;
}
