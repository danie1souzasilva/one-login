package br.com.docpass.adaptador.entrada.excecao;

import br.com.docpass.aplicacao.erro.*;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;

@Provider
public class MapeadorErroDominio implements ExceptionMapper<ErroDominioExcecao> {
    @Override
    public Response toResponse(ErroDominioExcecao excecao) {
        ErroDominio erro = excecao.getErro();
        RespostaErroAPI resposta = new RespostaErroAPI();
        resposta.timestamp = Instant.now();
        resposta.mensagem = erro.mensagem();
        resposta.detalhes = null;

        if (erro instanceof UsuarioNaoEncontradoErro) {
            resposta.codigo = "USUARIO_NAO_ENCONTRADO";
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(resposta).build();
        }
        if (erro instanceof DocumentoInvalidoErro) {
            resposta.codigo = "DOCUMENTO_INVALIDO";
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(resposta).build();
        }
        if (erro instanceof ConsentimentoNegadoErro) {
            resposta.codigo = "CONSENTIMENTO_NEGADO";
            return Response.status(Response.Status.FORBIDDEN).type(MediaType.APPLICATION_JSON).entity(resposta).build();
        }
        if (erro instanceof ErroValidacao) {
            resposta.codigo = "ERRO_VALIDACAO";
            return Response.status(422).type(MediaType.APPLICATION_JSON).entity(resposta).build();
        }

        resposta.codigo = "ERRO_DOMINIO";
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(resposta).build();
    }
}
