package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.dominio.modelo.TipoDocumento;
import br.com.docpass.dominio.modelo.StatusVerificacao;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "documents")
public class DocumentoEntidade {
    @Id
    public UUID id;

    @Column(name = "user_id", nullable = false)
    public UUID usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    public TipoDocumento tipoDocumento;

    @Column(name = "file_url", nullable = false)
    public String urlArquivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_verificacao", nullable = false)
    public StatusVerificacao statusVerificacao;

    @Column(name = "created_at", nullable = false)
    public Instant criadoEm;

    @PrePersist
    void prePersist() {
        if (criadoEm == null) {
            criadoEm = Instant.now();
        }
    }
}
