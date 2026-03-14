package br.com.docpass.dominio.modelo;

public class DadosAdicionais {
    private TipoSanguineo tipoSanguineo;
    private String timeQueTorce;
    private String fotoPerfilUrl;
    private String biografia;

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public String getTimeQueTorce() {
        return timeQueTorce;
    }

    public void setTimeQueTorce(String timeQueTorce) {
        this.timeQueTorce = timeQueTorce;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
}
