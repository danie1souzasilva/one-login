package br.com.docpass.aplicacao.caso;

import br.com.docpass.adaptador.entrada.dto.resposta.DocumentoResposta;
import br.com.docpass.adaptador.entrada.mapeador.DocumentoMapeador;
import br.com.docpass.aplicacao.erro.ConsentimentoNegadoErro;
import br.com.docpass.aplicacao.erro.ErroDominio;
import br.com.docpass.aplicacao.servico.ConsentimentoRepositorio;
import br.com.docpass.aplicacao.servico.DocumentoRepositorio;
import br.com.docpass.configuracao.seguranca.EscoposSeguranca;
import br.com.docpass.dominio.modelo.Consentimento;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class BuscarDocumentosAutorizadosCasoUso {
    private final ConsentimentoRepositorio consentimentoRepositorio;
    private final DocumentoRepositorio documentoRepositorio;
    private final DocumentoMapeador documentoMapeador;

    public BuscarDocumentosAutorizadosCasoUso(ConsentimentoRepositorio consentimentoRepositorio,
                                              DocumentoRepositorio documentoRepositorio,
                                              DocumentoMapeador documentoMapeador) {
        this.consentimentoRepositorio = consentimentoRepositorio;
        this.documentoRepositorio = documentoRepositorio;
        this.documentoMapeador = documentoMapeador;
    }

    public Either<ErroDominio, java.util.List<DocumentoResposta>> executar(UUID usuarioId, UUID empresaId) {
        return consentimentoRepositorio.buscarPorUsuarioIdEEmpresaId(usuarioId, empresaId)
                .toEither(() -> (ErroDominio) new ConsentimentoNegadoErro("Consentimento inexistente"))
                .flatMap(this::validarConsentimento)
                .map(consentimento -> documentoRepositorio.buscarPorUsuarioId(usuarioId)
                        .map(documentoMapeador::paraResposta)
                        .asJava());
    }

    private Either<ErroDominio, Consentimento> validarConsentimento(Consentimento consentimento) {
        if (consentimento.getExpiraEm() != null && consentimento.getExpiraEm().isBefore(Instant.now())) {
            return Either.left(new ConsentimentoNegadoErro("Consentimento expirado"));
        }
        if (!contemEscopo(consentimento, EscoposSeguranca.DOCUMENTOS)) {
            return Either.left(new ConsentimentoNegadoErro("Escopo documentos nao autorizado"));
        }
        return Either.right(consentimento);
    }

    private boolean contemEscopo(Consentimento consentimento, String escopo) {
        if (consentimento.getEscopos() == null) {
            return false;
        }
        return consentimento.getEscopos().stream()
                .filter(valor -> valor != null && !valor.isBlank())
                .map(String::trim)
                .map(String::toUpperCase)
                .anyMatch(valor -> valor.equals(escopo));
    }
}
