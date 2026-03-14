package br.com.docpass.aplicacao.caso;

import br.com.docpass.adaptador.entrada.dto.resposta.DocumentoResposta;
import br.com.docpass.adaptador.entrada.mapeador.DocumentoMapeador;
import br.com.docpass.aplicacao.erro.ErroDominio;
import br.com.docpass.aplicacao.servico.DocumentoRepositorio;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class ListarDocumentosUsuarioCasoUso {
    private final DocumentoRepositorio documentoRepositorio;
    private final DocumentoMapeador documentoMapeador;

    public ListarDocumentosUsuarioCasoUso(DocumentoRepositorio documentoRepositorio, DocumentoMapeador documentoMapeador) {
        this.documentoRepositorio = documentoRepositorio;
        this.documentoMapeador = documentoMapeador;
    }

    public Either<ErroDominio, java.util.List<DocumentoResposta>> executar(UUID usuarioId) {
        return Either.right(documentoRepositorio.buscarPorUsuarioId(usuarioId)
                .map(documentoMapeador::paraResposta)
                .asJava());
    }
}
