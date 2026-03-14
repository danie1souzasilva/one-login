package br.com.docpass.aplicacao.caso;

import br.com.docpass.adaptador.entrada.dto.resposta.UsuarioResposta;
import br.com.docpass.adaptador.entrada.mapeador.UsuarioMapeador;
import br.com.docpass.aplicacao.erro.ConsentimentoNegadoErro;
import br.com.docpass.aplicacao.erro.ErroDominio;
import br.com.docpass.aplicacao.erro.UsuarioNaoEncontradoErro;
import br.com.docpass.aplicacao.servico.ConsentimentoRepositorio;
import br.com.docpass.aplicacao.servico.UsuarioRepositorio;
import br.com.docpass.configuracao.seguranca.EscoposSeguranca;
import br.com.docpass.dominio.modelo.Consentimento;

import io.vavr.Tuple;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class BuscarPerfilAutorizadoCasoUso {
    private final ConsentimentoRepositorio consentimentoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapeador usuarioMapeador;

    public BuscarPerfilAutorizadoCasoUso(ConsentimentoRepositorio consentimentoRepositorio,
                                         UsuarioRepositorio usuarioRepositorio,
                                         UsuarioMapeador usuarioMapeador) {
        this.consentimentoRepositorio = consentimentoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapeador = usuarioMapeador;
    }

    public Either<ErroDominio, UsuarioResposta> executar(UUID usuarioId, UUID empresaId) {
        return consentimentoRepositorio.buscarPorUsuarioIdEEmpresaId(usuarioId, empresaId)
                .toEither(() -> (ErroDominio) new ConsentimentoNegadoErro("Consentimento inexistente"))
                .flatMap(this::validarConsentimento)
                .flatMap(consentimento -> usuarioRepositorio.buscarPorId(usuarioId)
                        .toEither(() -> (ErroDominio) new UsuarioNaoEncontradoErro("Usuario nao encontrado"))
                        .map(usuario -> Tuple.of(consentimento, usuario)))
                .map(tuple -> aplicarEscopos(usuarioMapeador.paraResposta(tuple._2), tuple._1));
    }

    private Either<ErroDominio, Consentimento> validarConsentimento(Consentimento consentimento) {
        if (consentimento.getExpiraEm() != null && consentimento.getExpiraEm().isBefore(Instant.now())) {
            return Either.left(new ConsentimentoNegadoErro("Consentimento expirado"));
        }
        if (!contemEscopo(consentimento, EscoposSeguranca.PERFIL_BASICO)) {
            return Either.left(new ConsentimentoNegadoErro("Escopo perfil nao autorizado"));
        }
        return Either.right(consentimento);
    }

    private UsuarioResposta aplicarEscopos(UsuarioResposta resposta, Consentimento consentimento) {
        if (consentimento == null || resposta == null) {
            return resposta;
        }
        boolean enderecoLiberado = contemEscopo(consentimento, EscoposSeguranca.ENDERECO);
        boolean dadosFinanceirosLiberados = contemEscopo(consentimento, EscoposSeguranca.DADOS_FINANCEIROS);
        boolean dadosSensiveisLiberados = contemEscopo(consentimento, EscoposSeguranca.DADOS_SENSIVEIS);

        if (!enderecoLiberado) {
            resposta.endereco = null;
        }

        if (!dadosFinanceirosLiberados && resposta.dadosProfissionais != null) {
            resposta.dadosProfissionais.rendaMensal = null;
        }

        if (!dadosSensiveisLiberados) {
            if (resposta.dadosBasicos != null) {
                resposta.dadosBasicos.dataNascimento = null;
            }
            if (resposta.dadosComplementares != null) {
                resposta.dadosComplementares.nomeMae = null;
            }
            resposta.documentos = null;
            if (resposta.dadosAdicionais != null) {
                resposta.dadosAdicionais.tipoSanguineo = null;
            }
        }

        return resposta;
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
