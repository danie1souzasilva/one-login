package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.dominio.modelo.TipoSanguineo;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class DadosAdicionaisPersistencia {
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_sanguineo")
    public TipoSanguineo tipoSanguineo;

    @Column(name = "time_que_torce")
    public String timeQueTorce;

    @Column(name = "foto_perfil_url")
    public String fotoPerfilUrl;

    @Column(name = "biografia")
    public String biografia;
}
