package br.com.docpass.adaptador.saida.repositorio;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "webhooks_empresa")
public class WebhookEmpresaEntidade {
    @Id
    public UUID id;

    @Column(name = "empresa_id", nullable = false)
    public UUID empresaId;

    @Column(nullable = false)
    public String url;

    @Column(name = "eventos_assinados", nullable = false)
    public String eventosAssinados;

    @Column(name = "criado_em", nullable = false)
    public Instant criadoEm;

    @PrePersist
    void prePersist() {
        if (criadoEm == null) {
            criadoEm = Instant.now();
        }
    }
}
