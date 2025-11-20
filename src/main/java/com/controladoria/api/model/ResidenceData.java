package com.controladoria.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_dados_residencia")
public class ResidenceData {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "documento_id", unique = true)
    private Document document;

    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    @Column(name = "data_emissao")
    private LocalDate issueDate;

    @Column(name = "entidade_emissora")
    private String issuingEntity;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}

