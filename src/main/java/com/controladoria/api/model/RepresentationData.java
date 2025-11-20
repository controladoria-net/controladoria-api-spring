package com.controladoria.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_dados_representacao")
public class RepresentationData {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "documento_id", unique = true)
    private Document document;

    @Column(name = "nome_outorgado")
    private String granteeName;

    @Column(name = "cpf_outorgado")
    private String granteeCpf;

    @Column(name = "oab_outorgado")
    private String granteeOab;

    @Column(name = "data_assinatura")
    private LocalDate signatureDate;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}

