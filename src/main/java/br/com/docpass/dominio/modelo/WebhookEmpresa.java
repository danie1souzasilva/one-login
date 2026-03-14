package br.com.docpass.dominio.modelo;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class WebhookEmpresa {
    private UUID id;
    private UUID empresaId;
    private String url;
    private List<String> eventosAssinados;
    private Instant criadoEm;

    public WebhookEmpresa() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(UUID empresaId) {
        this.empresaId = empresaId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getEventosAssinados() {
        return eventosAssinados;
    }

    public void setEventosAssinados(List<String> eventosAssinados) {
        this.eventosAssinados = eventosAssinados;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }
}
