package br.com.docpass.adaptador.saida.repositorio;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "companies")
public class EmpresaEntidade {
    @Id
    public UUID id;

    @Column(nullable = false)
    public String nome;

    @Column(name = "api_key", nullable = false, unique = true)
    public String chaveApi;

    @Column(name = "created_at", nullable = false)
    public Instant criadoEm;

    @PrePersist
    void prePersist() {
        if (criadoEm == null) {
            criadoEm = Instant.now();
        }
    }
}
