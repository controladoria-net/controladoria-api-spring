package com.controladoria.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_cliente")
public class Client {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "nome_completo", nullable = false, length = 255)
    private String fullName;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate birthDate;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "client")
    private List<Solicitation> requests;
}