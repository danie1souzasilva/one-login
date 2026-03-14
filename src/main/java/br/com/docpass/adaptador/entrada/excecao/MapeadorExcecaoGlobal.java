package br.com.docpass.adaptador.entrada.excecao;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;
import java.util.NoSuchElementException;

@Provider
public class MapeadorExcecaoGlobal implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable excecao) {
        RespostaErroAPI resposta = new RespostaErroAPI();
        resposta.timestamp = Instant.now();
        resposta.mensagem = excecao.getMessage();
        resposta.detalhes = null;
        resposta.codigo = "ERRO_INTERNO";

        if (excecao instanceof ConstraintViolationException) {
            resposta.codigo = "ERRO_VALIDACAO";
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(resposta)
                    .build();
        }

        if (excecao instanceof NoSuchElementException) {
            resposta.codigo = "NAO_ENCONTRADO";
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(resposta)
                    .build();
        }

        if (excecao instanceof SecurityException) {
            resposta.codigo = "ACESSO_NEGADO";
            return Response.status(Response.Status.FORBIDDEN)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(resposta)
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(resposta)
                .build();
    }
}
