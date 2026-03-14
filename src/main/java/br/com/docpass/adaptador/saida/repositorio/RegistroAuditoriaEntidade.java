package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.dominio.modelo.TipoAcessoAuditoria;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "auditoria_acesso")
public class RegistroAuditoriaEntidade {
    @Id
    public UUID id;

    @Column(name = "usuario_id")
    public UUID usuarioId;

    @Column(name = "empresa_id")
    public UUID empresaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acesso", nullable = false)
    public TipoAcessoAuditoria tipoAcesso;

    @Column(nullable = false)
    public String endpoint;

    @Column(nullable = false)
    public Instant timestamp;

    @Column(name = "ip_requisicao")
    public String ipRequisicao;
}
