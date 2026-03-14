package br.com.docpass.adaptador.entrada.dto.requisicao;

import br.com.docpass.dominio.modelo.TipoSanguineo;
import org.hibernate.validator.constraints.URL;

public class DadosAdicionaisRequisicao {
    public TipoSanguineo tipoSanguineo;
    public String timeQueTorce;

    @URL(message = "fotoPerfilUrl deve ser uma URL valida")
    public String fotoPerfilUrl;

    public String biografia;
}
