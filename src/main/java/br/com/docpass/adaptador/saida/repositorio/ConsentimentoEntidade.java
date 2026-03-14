package br.com.docpass.adaptador.saida.repositorio;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "consents")
public class ConsentimentoEntidade {
    @Id
    public UUID id;

    @Column(name = "user_id", nullable = false)
    public UUID usuarioId;

    @Column(name = "company_id", nullable = false)
    public UUID empresaId;

    @Column(name = "scopes", nullable = false)
    public String escopos;

    @Column(name = "created_at", nullable = false)
    public Instant criadoEm;

    @Column(name = "expires_at")
    public Instant expiraEm;

    @PrePersist
    void prePersist() {
        if (criadoEm == null) {
            criadoEm = Instant.now();
        }
    }
}
