package com.controladoria.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_dados_rgp")
public class RgpData {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "documento_id", unique = true)
    private Document document;

    @Column(name = "numero_rgp")
    private String rgpNumber;

    @Column(name = "data_emissao")
    private LocalDate issueDate;

    @Column(name = "data_primeiro_registro")
    private LocalDate firstRegistrationDate;

    @Column(name = "situacao")
    private String status;

    @Column(name = "categoria")
    private String category;

    @Column(name = "municipio")
    private String city;

    @Column(name = "uf")
    private String state;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}

