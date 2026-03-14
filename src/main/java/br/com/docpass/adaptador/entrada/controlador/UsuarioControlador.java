package br.com.docpass.adaptador.entrada.controlador;

import br.com.docpass.adaptador.entrada.dto.requisicao.CriarUsuarioRequisicao;
import br.com.docpass.adaptador.entrada.dto.requisicao.AtualizarUsuarioRequisicao;
import br.com.docpass.adaptador.entrada.dto.resposta.UsuarioResposta;
import br.com.docpass.aplicacao.caso.CriarUsuarioCasoUso;
import br.com.docpass.aplicacao.caso.BuscarUsuarioCasoUso;
import br.com.docpass.aplicacao.caso.AtualizarUsuarioCasoUso;
import br.com.docpass.aplicacao.servico.ServicoAuditoria;
import br.com.docpass.dominio.modelo.RegistroAuditoria;
import br.com.docpass.dominio.modelo.TipoAcessoAuditoria;

import io.quarkus.security.Authenticated;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.UUID;
import java.time.Instant;

@Path("/api/v1/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
@Tag(name = "Usuarios", description = "Operacoes de cadastro e consulta de usuarios")
public class UsuarioControlador {
    private final CriarUsuarioCasoUso criarUsuarioCasoUso;
    private final BuscarUsuarioCasoUso buscarUsuarioCasoUso;
    private final AtualizarUsuarioCasoUso atualizarUsuarioCasoUso;
    private final ServicoAuditoria servicoAuditoria;

    public UsuarioControlador(CriarUsuarioCasoUso criarUsuarioCasoUso,
                              BuscarUsuarioCasoUso buscarUsuarioCasoUso,
                              AtualizarUsuarioCasoUso atualizarUsuarioCasoUso,
                              ServicoAuditoria servicoAuditoria) {
        this.criarUsuarioCasoUso = criarUsuarioCasoUso;
        this.buscarUsuarioCasoUso = buscarUsuarioCasoUso;
        this.atualizarUsuarioCasoUso = atualizarUsuarioCasoUso;
        this.servicoAuditoria = servicoAuditoria;
    }

    @POST
    @Operation(summary = "Criar usuario", description = "Cria um novo usuario com dados pessoais.")
    @RequestBody(content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = CriarUsuarioRequisicao.class),
            examples = @ExampleObject(name = "Usuario", value = "{ \"dadosBasicos\": { \"nomeCompleto\": \"Maria\", \"email\": \"maria@example.com\", \"cpf\": \"11144477735\", \"telefone\": \"11999998888\", \"dataNascimento\": \"1995-02-10\" } }")
    ))
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Usuario criado", content = @Content(schema = @Schema(implementation = UsuarioResposta.class))),
            @APIResponse(responseCode = "422", description = "Erro de validacao")
    })
    public Response criar(@Valid CriarUsuarioRequisicao requisicao) {
        return SuporteControlador.paraResposta(criarUsuarioCasoUso.executar(requisicao),
                resposta -> Response.status(Response.Status.CREATED).entity(resposta).build());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar usuario", description = "Busca um usuario pelo ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = UsuarioResposta.class))),
            @APIResponse(responseCode = "404", description = "Usuario nao encontrado")
    })
    public Response buscar(@PathParam("id") UUID id) {
        return SuporteControlador.paraResposta(buscarUsuarioCasoUso.executar(id),
                valor -> Response.ok(valor).build());
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar usuario", description = "Atualiza dados cadastrais do usuario.")
    @RequestBody(content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = AtualizarUsuarioRequisicao.class),
            examples = @ExampleObject(name = "Atualizacao", value = "{ \"dadosBasicos\": { \"nomeCompleto\": \"Maria\", \"email\": \"maria@novo.com\", \"cpf\": \"11144477735\", \"telefone\": \"11999998888\", \"dataNascimento\": \"1995-02-10\" } }")
    ))
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Usuario atualizado", content = @Content(schema = @Schema(implementation = UsuarioResposta.class))),
            @APIResponse(responseCode = "404", description = "Usuario nao encontrado"),
            @APIResponse(responseCode = "422", description = "Erro de validacao")
    })
    public Response atualizar(@PathParam("id") UUID id,
                              @Valid AtualizarUsuarioRequisicao requisicao,
                              @Context ContainerRequestContext contexto) {
        var resultado = atualizarUsuarioCasoUso.executar(id, requisicao);
        resultado.peek(resposta -> registrarAuditoria(id, null, TipoAcessoAuditoria.ATUALIZACAO_PERFIL, contexto));
        return SuporteControlador.paraResposta(resultado, valor -> Response.ok(valor).build());
    }

    private void registrarAuditoria(UUID usuarioId, UUID empresaId, TipoAcessoAuditoria tipo, ContainerRequestContext contexto) {
        RegistroAuditoria registro = new RegistroAuditoria();
        registro.setId(UUID.randomUUID());
        registro.setUsuarioId(usuarioId);
        registro.setEmpresaId(empresaId);
        registro.setTipoAcesso(tipo);
        registro.setEndpoint(contexto.getUriInfo().getPath());
        registro.setTimestamp(Instant.now());
        registro.setIpRequisicao(contexto.getHeaderString("X-Forwarded-For"));
        servicoAuditoria.registrar(registro);
    }
}
