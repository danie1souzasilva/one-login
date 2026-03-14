package br.com.docpass.dominio.modelo;

import java.time.Instant;
import java.util.UUID;

public class RegistroAuditoria {
    private UUID id;
    private UUID usuarioId;
    private UUID empresaId;
    private TipoAcessoAuditoria tipoAcesso;
    private String endpoint;
    private Instant timestamp;
    private String ipRequisicao;

    public RegistroAuditoria() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UUID getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(UUID empresaId) {
        this.empresaId = empresaId;
    }

    public TipoAcessoAuditoria getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(TipoAcessoAuditoria tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getIpRequisicao() {
        return ipRequisicao;
    }

    public void setIpRequisicao(String ipRequisicao) {
        this.ipRequisicao = ipRequisicao;
    }
}
