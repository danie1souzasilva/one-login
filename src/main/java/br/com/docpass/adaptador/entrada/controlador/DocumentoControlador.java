package br.com.docpass.adaptador.entrada.controlador;

import br.com.docpass.adaptador.entrada.dto.requisicao.EnviarDocumentoFormulario;
import br.com.docpass.adaptador.entrada.dto.requisicao.EnviarDocumentoRequisicao;
import br.com.docpass.aplicacao.caso.ListarDocumentosUsuarioCasoUso;
import br.com.docpass.aplicacao.caso.EnviarDocumentoCasoUso;
import br.com.docpass.aplicacao.erro.DocumentoInvalidoErro;
import br.com.docpass.aplicacao.erro.ErroDominioExcecao;
import br.com.docpass.aplicacao.servico.ServicoAuditoria;
import br.com.docpass.dominio.modelo.RegistroAuditoria;
import br.com.docpass.dominio.modelo.TipoAcessoAuditoria;

import io.quarkus.security.Authenticated;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.MultipartForm;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;
import java.time.Instant;

@Path("/api/v1/documentos")
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
@Tag(name = "Documentos", description = "Operacoes de envio e consulta de documentos")
public class DocumentoControlador {
    private final EnviarDocumentoCasoUso enviarDocumentoCasoUso;
    private final ListarDocumentosUsuarioCasoUso listarDocumentosUsuarioCasoUso;
    private final ServicoAuditoria servicoAuditoria;

    public DocumentoControlador(EnviarDocumentoCasoUso enviarDocumentoCasoUso,
                                ListarDocumentosUsuarioCasoUso listarDocumentosUsuarioCasoUso,
                                ServicoAuditoria servicoAuditoria) {
        this.enviarDocumentoCasoUso = enviarDocumentoCasoUso;
        this.listarDocumentosUsuarioCasoUso = listarDocumentosUsuarioCasoUso;
        this.servicoAuditoria = servicoAuditoria;
    }

    @POST
    @Path("/enviar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Operation(summary = "Enviar documento", description = "Envia documento do usuario para armazenamento.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Documento enviado", content = @Content(schema = @Schema(implementation = br.com.docpass.adaptador.entrada.dto.resposta.DocumentoResposta.class))),
            @APIResponse(responseCode = "400", description = "Documento invalido")
    })
    public Response enviar(@Valid @MultipartForm EnviarDocumentoFormulario formulario,
                           @Context ContainerRequestContext contexto) {
        try (InputStream inputStream = Files.newInputStream(formulario.arquivo.uploadedFile())) {
            EnviarDocumentoRequisicao requisicao = new EnviarDocumentoRequisicao();
            requisicao.usuarioId = formulario.usuarioId;
            requisicao.tipoDocumento = formulario.tipoDocumento;
            requisicao.conteudo = inputStream;
            requisicao.tipoConteudo = formulario.arquivo.contentType();
            var resultado = enviarDocumentoCasoUso.executar(requisicao);
            resultado.peek(resposta -> registrarAuditoria(formulario.usuarioId, null, TipoAcessoAuditoria.UPLOAD_DOCUMENTO, contexto));
            return SuporteControlador.paraResposta(resultado,
                    valor -> Response.ok(valor).build());
        } catch (Exception ex) {
            throw new ErroDominioExcecao(new DocumentoInvalidoErro("Falha ao ler arquivo"));
        }
    }

    @GET
    @Path("/usuario/{usuarioId}")
    @Operation(summary = "Listar documentos", description = "Lista documentos de um usuario.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de documentos", content = @Content(schema = @Schema(implementation = br.com.docpass.adaptador.entrada.dto.resposta.DocumentoResposta.class))),
            @APIResponse(responseCode = "404", description = "Usuario nao encontrado")
    })
    public Response listarPorUsuario(@PathParam("usuarioId") UUID usuarioId) {
        return SuporteControlador.paraResposta(listarDocumentosUsuarioCasoUso.executar(usuarioId),
                valor -> Response.ok(valor).build());
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
