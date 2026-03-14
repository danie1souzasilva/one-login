package br.com.docpass.aplicacao.caso;

import br.com.docpass.adaptador.entrada.dto.requisicao.AtualizarUsuarioRequisicao;
import br.com.docpass.adaptador.entrada.dto.resposta.UsuarioResposta;
import br.com.docpass.adaptador.entrada.mapeador.UsuarioMapeador;
import br.com.docpass.aplicacao.erro.ErroDominio;
import br.com.docpass.aplicacao.erro.UsuarioNaoEncontradoErro;
import br.com.docpass.aplicacao.erro.ErroValidacao;
import br.com.docpass.aplicacao.evento.PerfilAtualizadoEvento;
import br.com.docpass.aplicacao.servico.PublicadorEvento;
import br.com.docpass.aplicacao.servico.UsuarioRepositorio;
import br.com.docpass.dominio.modelo.Usuario;

import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class AtualizarUsuarioCasoUso {
    private final UsuarioRepositorio usuarioRepositorio;
    private final PublicadorEvento publicadorEvento;
    private final UsuarioMapeador usuarioMapeador;

    public AtualizarUsuarioCasoUso(UsuarioRepositorio usuarioRepositorio,
                                   PublicadorEvento publicadorEvento,
                                   UsuarioMapeador usuarioMapeador) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.publicadorEvento = publicadorEvento;
        this.usuarioMapeador = usuarioMapeador;
    }

    @Transactional
    public Either<ErroDominio, UsuarioResposta> executar(UUID id, AtualizarUsuarioRequisicao requisicao) {
        if (requisicao == null || requisicao.dadosBasicos == null) {
            return Either.left(new ErroValidacao("Dados basicos obrigatorios"));
        }
        return usuarioRepositorio.buscarPorId(id)
                .toEither(() -> (ErroDominio) new UsuarioNaoEncontradoErro("Usuario nao encontrado"))
                .flatMap(usuario -> validarUnicidadeParaAtualizacao(id, requisicao.dadosBasicos.email, requisicao.dadosBasicos.cpf))
                .flatMap(valido -> Try.of(() -> persistirAtualizacao(id, requisicao))
                        .toEither()
                        .mapLeft(ex -> (ErroDominio) new ErroValidacao("Falha ao atualizar usuario")));
    }

    private Either<ErroDominio, Boolean> validarUnicidadeParaAtualizacao(UUID id, String email, String cpf) {
        Option<Usuario> emailDono = usuarioRepositorio.buscarPorEmail(email);
        if (emailDono.isDefined() && !emailDono.get().getId().equals(id)) {
            return Either.left(new ErroValidacao("Email ja cadastrado"));
        }
        Option<Usuario> cpfDono = usuarioRepositorio.buscarPorCpf(cpf);
        if (cpfDono.isDefined() && !cpfDono.get().getId().equals(id)) {
            return Either.left(new ErroValidacao("CPF ja cadastrado"));
        }
        return Either.right(true);
    }

    private UsuarioResposta persistirAtualizacao(UUID id, AtualizarUsuarioRequisicao requisicao) {
        Usuario atualizado = usuarioMapeador.paraEntidade(id, requisicao);
        atualizado.setCriadoEm(usuarioRepositorio.buscarPorId(id).map(Usuario::getCriadoEm).getOrNull());
        Usuario salvo = usuarioRepositorio.atualizar(atualizado);
        publicadorEvento.publicar(new PerfilAtualizadoEvento(salvo.getId(), Instant.now()));
        return usuarioMapeador.paraResposta(salvo);
    }
}
