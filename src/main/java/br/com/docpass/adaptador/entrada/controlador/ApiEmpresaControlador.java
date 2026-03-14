package br.com.docpass.adaptador.entrada.controlador;

import br.com.docpass.aplicacao.caso.BuscarDocumentosAutorizadosCasoUso;
import br.com.docpass.aplicacao.caso.BuscarPerfilAutorizadoCasoUso;
import br.com.docpass.aplicacao.servico.EmpresaRepositorio;
import br.com.docpass.aplicacao.servico.ServicoAuditoria;
import br.com.docpass.dominio.modelo.Empresa;
import br.com.docpass.dominio.modelo.RegistroAuditoria;
import br.com.docpass.dominio.modelo.TipoAcessoAuditoria;
import io.vavr.control.Option;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.time.Instant;
import java.util.UUID;

@Path("/api-empresas/v1")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "API Empresarial", description = "Acesso empresarial aos dados autorizados")
public class ApiEmpresaControlador {
    private final BuscarPerfilAutorizadoCasoUso buscarPerfilAutorizadoCasoUso;
    private final BuscarDocumentosAutorizadosCasoUso buscarDocumentosAutorizadosCasoUso;
    private final ServicoAuditoria servicoAuditoria;
    private final EmpresaRepositorio empresaRepositorio;

    public ApiEmpresaControlador(BuscarPerfilAutorizadoCasoUso buscarPerfilAutorizadoCasoUso,
                                 BuscarDocumentosAutorizadosCasoUso buscarDocumentosAutorizadosCasoUso,
                                 ServicoAuditoria servicoAuditoria,
                                 EmpresaRepositorio empresaRepositorio) {
        this.buscarPerfilAutorizadoCasoUso = buscarPerfilAutorizadoCasoUso;
        this.buscarDocumentosAutorizadosCasoUso = buscarDocumentosAutorizadosCasoUso;
        this.servicoAuditoria = servicoAuditoria;
        this.empresaRepositorio = empresaRepositorio;
    }

    @GET
    @Path("/perfil/{usuarioId}")
    @Operation(summary = "Buscar perfil autorizado", description = "Retorna dados do usuario se houver consentimento valido.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Perfil autorizado", content = @Content(schema = @Schema(implementation = br.com.docpass.adaptador.entrada.dto.resposta.UsuarioResposta.class))),
            @APIResponse(responseCode = "401", description = "API Key invalida"),
            @APIResponse(responseCode = "403", description = "Consentimento negado")
    })
    public Response perfil(@PathParam("usuarioId") UUID usuarioId, @Context ContainerRequestContext contexto) {
        UUID empresaId = obterEmpresaId(contexto);
        if (empresaId == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        var resultado = buscarPerfilAutorizadoCasoUso.executar(usuarioId, empresaId);
        resultado.peek(resposta -> registrarAuditoria(usuarioId, empresaId, TipoAcessoAuditoria.ACESSO_PERFIL, contexto));
        return SuporteControlador.paraResposta(resultado, valor -> Response.ok(valor).build());
    }

    @GET
    @Path("/documentos/{usuarioId}")
    @Operation(summary = "Buscar documentos autorizados", description = "Retorna documentos do usuario se houver consentimento valido.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Documentos autorizados", content = @Content(schema = @Schema(implementation = br.com.docpass.adaptador.entrada.dto.resposta.DocumentoResposta.class))),
            @APIResponse(responseCode = "401", description = "API Key invalida"),
            @APIResponse(responseCode = "403", description = "Consentimento negado")
    })
    public Response documentos(@PathParam("usuarioId") UUID usuarioId, @Context ContainerRequestContext contexto) {
        UUID empresaId = obterEmpresaId(contexto);
        if (empresaId == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        var resultado = buscarDocumentosAutorizadosCasoUso.executar(usuarioId, empresaId);
        resultado.peek(resposta -> registrarAuditoria(usuarioId, empresaId, TipoAcessoAuditoria.ACESSO_DOCUMENTO, contexto));
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

    private UUID obterEmpresaId(ContainerRequestContext contexto) {
        Object propriedade = contexto.getProperty("empresaId");
        if (propriedade instanceof UUID uuid) {
            return uuid;
        }
        String chaveApi = contexto.getHeaderString("X-API-KEY");
        if (chaveApi == null || chaveApi.isBlank()) {
            return null;
        }
        Option<Empresa> empresa = empresaRepositorio.buscarPorChaveApi(chaveApi);
        return empresa.map(Empresa::getId).getOrNull();
    }
}
