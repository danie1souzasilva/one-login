package br.com.docpass.adaptador.entrada.dto.requisicao;

import br.com.docpass.dominio.modelo.TipoDocumento;

import java.io.InputStream;
import java.util.UUID;

public class EnviarDocumentoRequisicao {
    public UUID usuarioId;
    public TipoDocumento tipoDocumento;
    public InputStream conteudo;
    public String tipoConteudo;
}
