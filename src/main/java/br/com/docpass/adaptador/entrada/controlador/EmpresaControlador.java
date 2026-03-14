package br.com.docpass.adaptador.entrada.controlador;

import br.com.docpass.adaptador.entrada.dto.requisicao.CriarEmpresaRequisicao;
import br.com.docpass.aplicacao.caso.CriarEmpresaCasoUso;

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

@Path("/api/v1/empresas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
@Tag(name = "API Empresarial", description = "Cadastro de empresas parceiras")
public class EmpresaControlador {
    private final CriarEmpresaCasoUso criarEmpresaCasoUso;

    public EmpresaControlador(CriarEmpresaCasoUso criarEmpresaCasoUso) {
        this.criarEmpresaCasoUso = criarEmpresaCasoUso;
    }

    @POST
    @Operation(summary = "Registrar empresa", description = "Cadastra uma empresa e gera chave API.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Empresa criada", content = @Content(schema = @Schema(implementation = br.com.docpass.adaptador.entrada.dto.resposta.EmpresaResposta.class))),
            @APIResponse(responseCode = "422", description = "Erro de validacao")
    })
    public Response criar(@Valid CriarEmpresaRequisicao requisicao) {
        return SuporteControlador.paraResposta(criarEmpresaCasoUso.executar(requisicao),
                resposta -> Response.status(Response.Status.CREATED).entity(resposta).build());
    }
}
