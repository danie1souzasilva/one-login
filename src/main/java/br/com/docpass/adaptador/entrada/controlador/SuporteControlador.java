package br.com.docpass.adaptador.entrada.controlador;

import br.com.docpass.aplicacao.erro.ErroDominio;
import br.com.docpass.aplicacao.erro.ErroDominioExcecao;
import io.vavr.control.Either;

import jakarta.ws.rs.core.Response;

import java.util.function.Function;

final class SuporteControlador {
    private SuporteControlador() {
    }

    static <T> Response paraResposta(Either<ErroDominio, T> resultado, Function<T, Response> sucesso) {
        return resultado.fold(erro -> {
            throw new ErroDominioExcecao(erro);
        }, sucesso);
    }
}
