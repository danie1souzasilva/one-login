package br.com.docpass.dominio.modelo;

import java.time.Instant;
import java.util.UUID;

public class Documento {
    private UUID id;
    private UUID usuarioId;
    private TipoDocumento tipoDocumento;
    private String urlArquivo;
    private StatusVerificacao statusVerificacao;
    private Instant criadoEm;

    public Documento() {
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

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getUrlArquivo() {
        return urlArquivo;
    }

    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }

    public StatusVerificacao getStatusVerificacao() {
        return statusVerificacao;
    }

    public void setStatusVerificacao(StatusVerificacao statusVerificacao) {
        this.statusVerificacao = statusVerificacao;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }
}
