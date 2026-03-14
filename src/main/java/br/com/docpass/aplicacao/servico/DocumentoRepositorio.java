package br.com.docpass.aplicacao.servico;

import br.com.docpass.dominio.modelo.Documento;
import io.vavr.collection.List;

import java.util.UUID;

public interface DocumentoRepositorio {
    Documento salvar(Documento documento);

    List<Documento> buscarPorUsuarioId(UUID usuarioId);
}
