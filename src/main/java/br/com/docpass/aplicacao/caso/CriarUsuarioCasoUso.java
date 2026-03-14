package br.com.docpass.aplicacao.caso;

import br.com.docpass.adaptador.entrada.dto.requisicao.CriarUsuarioRequisicao;
import br.com.docpass.adaptador.entrada.dto.resposta.UsuarioResposta;
import br.com.docpass.adaptador.entrada.mapeador.UsuarioMapeador;
import br.com.docpass.aplicacao.erro.ErroDominio;
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
public class CriarUsuarioCasoUso {
    private final UsuarioRepositorio usuarioRepositorio;
    private final PublicadorEvento publicadorEvento;
    private final UsuarioMapeador usuarioMapeador;

    public CriarUsuarioCasoUso(UsuarioRepositorio usuarioRepositorio,
                               PublicadorEvento publicadorEvento,
                               UsuarioMapeador usuarioMapeador) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.publicadorEvento = publicadorEvento;
        this.usuarioMapeador = usuarioMapeador;
    }

    @Transactional
    public Either<ErroDominio, UsuarioResposta> executar(CriarUsuarioRequisicao requisicao) {
        if (requisicao == null || requisicao.dadosBasicos == null) {
            return Either.left(new ErroValidacao("Dados basicos obrigatorios"));
        }
        return validarUnicidade(requisicao.dadosBasicos.email, requisicao.dadosBasicos.cpf)
                .flatMap(valido -> Try.of(() -> persistirUsuario(requisicao))
                        .toEither()
                        .mapLeft(ex -> new ErroValidacao("Falha ao salvar usuario")));
    }

    private Either<ErroDominio, Boolean> validarUnicidade(String email, String cpf) {
        Option<Usuario> emailExistente = usuarioRepositorio.buscarPorEmail(email);
        if (emailExistente.isDefined()) {
            return Either.left(new ErroValidacao("Email ja cadastrado"));
        }
        Option<Usuario> cpfExistente = usuarioRepositorio.buscarPorCpf(cpf);
        if (cpfExistente.isDefined()) {
            return Either.left(new ErroValidacao("CPF ja cadastrado"));
        }
        return Either.right(true);
    }

    private UsuarioResposta persistirUsuario(CriarUsuarioRequisicao requisicao) {
        Usuario usuario = usuarioMapeador.paraEntidade(requisicao);
        usuario.setId(UUID.randomUUID());
        usuario.setCriadoEm(Instant.now());
        Usuario salvo = usuarioRepositorio.salvar(usuario);
        publicadorEvento.publicar(new PerfilAtualizadoEvento(salvo.getId(), Instant.now()));
        return usuarioMapeador.paraResposta(salvo);
    }
}
