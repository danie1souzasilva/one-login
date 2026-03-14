package br.com.docpass.adaptador.entrada.dto.resposta;

import br.com.docpass.dominio.modelo.TipoDocumento;
import br.com.docpass.dominio.modelo.StatusVerificacao;

import java.time.Instant;
import java.util.UUID;

public class DocumentoResposta {
    public UUID id;
    public UUID usuarioId;
    public TipoDocumento tipoDocumento;
    public String urlArquivo;
    public StatusVerificacao statusVerificacao;
    public Instant criadoEm;
}
