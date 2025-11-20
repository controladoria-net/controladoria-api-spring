package com.controladoria.api.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_dados_identidade")
public class IdentityData {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "documento_id", unique = true)
    private Document document;

    @Column(name = "numero_rg")
    private String rgNumber;

    @Column(name = "orgao_expedidor")
    private String issuingAuthority;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate birthDate;

    @Column(name = "nome_mae")
    private String motherName;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}

