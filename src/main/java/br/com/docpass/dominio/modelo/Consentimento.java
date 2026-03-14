package br.com.docpass.dominio.modelo;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Consentimento {
    private UUID id;
    private UUID usuarioId;
    private UUID empresaId;
    private List<String> escopos;
    private Instant criadoEm;
    private Instant expiraEm;

    public Consentimento() {
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

    public List<String> getEscopos() {
        return escopos;
    }

    public void setEscopos(List<String> escopos) {
        this.escopos = escopos;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getExpiraEm() {
        return expiraEm;
    }

    public void setExpiraEm(Instant expiraEm) {
        this.expiraEm = expiraEm;
    }
}
