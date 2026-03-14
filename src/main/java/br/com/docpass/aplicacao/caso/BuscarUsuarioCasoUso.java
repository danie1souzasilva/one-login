package br.com.docpass.aplicacao.caso;

import br.com.docpass.adaptador.entrada.dto.resposta.UsuarioResposta;
import br.com.docpass.adaptador.entrada.mapeador.UsuarioMapeador;
import br.com.docpass.aplicacao.erro.ErroDominio;
import br.com.docpass.aplicacao.erro.UsuarioNaoEncontradoErro;
import br.com.docpass.aplicacao.servico.UsuarioRepositorio;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class BuscarUsuarioCasoUso {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapeador usuarioMapeador;

    public BuscarUsuarioCasoUso(UsuarioRepositorio usuarioRepositorio, UsuarioMapeador usuarioMapeador) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapeador = usuarioMapeador;
    }

    public Either<ErroDominio, UsuarioResposta> executar(UUID id) {
        return usuarioRepositorio.buscarPorId(id)
                .toEither(() -> (ErroDominio) new UsuarioNaoEncontradoErro("Usuario nao encontrado"))
                .map(usuarioMapeador::paraResposta);
    }
}
