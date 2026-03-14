package br.com.docpass.aplicacao.caso;

import br.com.docpass.adaptador.entrada.dto.requisicao.CriarConsentimentoRequisicao;
import br.com.docpass.adaptador.entrada.dto.resposta.ConsentimentoResposta;
import br.com.docpass.adaptador.entrada.mapeador.ConsentimentoMapeador;
import br.com.docpass.aplicacao.erro.ConsentimentoNegadoErro;
import br.com.docpass.aplicacao.erro.ErroDominio;
import br.com.docpass.aplicacao.erro.ErroValidacao;
import br.com.docpass.aplicacao.evento.ConsentimentoConcedidoEvento;
import br.com.docpass.aplicacao.servico.ConsentimentoRepositorio;
import br.com.docpass.aplicacao.servico.PublicadorEvento;
import br.com.docpass.dominio.modelo.Consentimento;

import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import io.micrometer.core.instrument.MeterRegistry;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class ConcederConsentimentoCasoUso {
    private final ConsentimentoRepositorio consentimentoRepositorio;
    private final PublicadorEvento publicadorEvento;
    private final ConsentimentoMapeador consentimentoMapeador;
    private final MeterRegistry meterRegistry;

    public ConcederConsentimentoCasoUso(ConsentimentoRepositorio consentimentoRepositorio,
                                        PublicadorEvento publicadorEvento,
                                        ConsentimentoMapeador consentimentoMapeador,
                                        MeterRegistry meterRegistry) {
        this.consentimentoRepositorio = consentimentoRepositorio;
        this.publicadorEvento = publicadorEvento;
        this.consentimentoMapeador = consentimentoMapeador;
        this.meterRegistry = meterRegistry;
    }

    @Transactional
    public Either<ErroDominio, ConsentimentoResposta> executar(CriarConsentimentoRequisicao requisicao) {
        return validarConsentimento(requisicao)
                .flatMap(valido -> Try.of(() -> persistirConsentimento(requisicao))
                        .toEither()
                        .mapLeft(ex -> new ErroValidacao("Falha ao registrar consentimento")));
    }

    private Either<ErroDominio, Boolean> validarConsentimento(CriarConsentimentoRequisicao requisicao) {
        if (requisicao.expiraEm != null && requisicao.expiraEm.isBefore(Instant.now())) {
            return Either.left(new ConsentimentoNegadoErro("Consentimento expirado"));
        }
        Option<Consentimento> existente = consentimentoRepositorio.buscarPorUsuarioIdEEmpresaId(requisicao.usuarioId, requisicao.empresaId);
        if (existente.isDefined() && (existente.get().getExpiraEm() == null || existente.get().getExpiraEm().isAfter(Instant.now()))) {
            return Either.left(new ConsentimentoNegadoErro("Consentimento ja concedido"));
        }
        return Either.right(true);
    }

    private ConsentimentoResposta persistirConsentimento(CriarConsentimentoRequisicao requisicao) {
        Consentimento consentimento = consentimentoMapeador.paraEntidade(requisicao);
        consentimento.setId(UUID.randomUUID());
        consentimento.setCriadoEm(Instant.now());
        Consentimento salvo = consentimentoRepositorio.salvar(consentimento);
        publicadorEvento.publicar(new ConsentimentoConcedidoEvento(salvo.getId(), salvo.getUsuarioId(), salvo.getEmpresaId(), Instant.now()));
        meterRegistry.counter("consentimentos_concedidos_total").increment();
        return consentimentoMapeador.paraResposta(salvo);
    }
}
