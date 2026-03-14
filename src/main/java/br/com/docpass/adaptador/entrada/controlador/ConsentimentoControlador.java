package br.com.docpass.adaptador.entrada.controlador;

import br.com.docpass.adaptador.entrada.dto.requisicao.CriarConsentimentoRequisicao;
import br.com.docpass.aplicacao.caso.ConcederConsentimentoCasoUso;
import br.com.docpass.aplicacao.caso.ListarConsentimentosCasoUso;

import io.quarkus.security.Authenticated;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

@Path("/api/v1/consentimentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
@Tag(name = "Consentimentos", description = "Operacoes de consentimento do usuario")
public class ConsentimentoControlador {
    private final ConcederConsentimentoCasoUso concederConsentimentoCasoUso;
    private final ListarConsentimentosCasoUso listarConsentimentosCasoUso;

    public ConsentimentoControlador(ConcederConsentimentoCasoUso concederConsentimentoCasoUso,
                                    ListarConsentimentosCasoUso listarConsentimentosCasoUso) {
        this.concederConsentimentoCasoUso = concederConsentimentoCasoUso;
        this.listarConsentimentosCasoUso = listarConsentimentosCasoUso;
    }

    @POST
    @Operation(summary = "Conceder consentimento", description = "Usuario autoriza empresa a acessar dados.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Consentimento criado", content = @Content(schema = @Schema(implementation = br.com.docpass.adaptador.entrada.dto.resposta.ConsentimentoResposta.class))),
            @APIResponse(responseCode = "403", description = "Consentimento negado"),
            @APIResponse(responseCode = "422", description = "Erro de validacao")
    })
    public Response criar(@Valid CriarConsentimentoRequisicao requisicao) {
        return SuporteControlador.paraResposta(concederConsentimentoCasoUso.executar(requisicao),
                resposta -> Response.status(Response.Status.CREATED).entity(resposta).build());
    }

    @GET
    @Path("/usuario/{usuarioId}")
    @Operation(summary = "Listar consentimentos", description = "Lista consentimentos de um usuario.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de consentimentos", content = @Content(schema = @Schema(implementation = br.com.docpass.adaptador.entrada.dto.resposta.ConsentimentoResposta.class)))
    })
    public Response listarPorUsuario(@PathParam("usuarioId") UUID usuarioId) {
        return SuporteControlador.paraResposta(listarConsentimentosCasoUso.executar(usuarioId),
                valor -> Response.ok(valor).build());
    }
}
