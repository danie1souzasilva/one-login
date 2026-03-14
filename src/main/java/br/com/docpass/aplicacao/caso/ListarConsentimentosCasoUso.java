package br.com.docpass.aplicacao.caso;

import br.com.docpass.adaptador.entrada.dto.resposta.ConsentimentoResposta;
import br.com.docpass.adaptador.entrada.mapeador.ConsentimentoMapeador;
import br.com.docpass.aplicacao.erro.ErroDominio;
import br.com.docpass.aplicacao.servico.ConsentimentoRepositorio;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class ListarConsentimentosCasoUso {
    private final ConsentimentoRepositorio consentimentoRepositorio;
    private final ConsentimentoMapeador consentimentoMapeador;

    public ListarConsentimentosCasoUso(ConsentimentoRepositorio consentimentoRepositorio,
                                       ConsentimentoMapeador consentimentoMapeador) {
        this.consentimentoRepositorio = consentimentoRepositorio;
        this.consentimentoMapeador = consentimentoMapeador;
    }

    public Either<ErroDominio, java.util.List<ConsentimentoResposta>> executar(UUID usuarioId) {
        return Either.right(consentimentoRepositorio.buscarPorUsuarioId(usuarioId)
                .map(consentimentoMapeador::paraResposta)
                .asJava());
    }
}
