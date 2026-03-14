package br.com.docpass.adaptador.entrada.dto.requisicao;

import br.com.docpass.dominio.modelo.TipoDocumento;

import jakarta.validation.constraints.NotNull;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.UUID;

public class EnviarDocumentoFormulario {
    @RestForm("usuarioId")
    @NotNull
    public UUID usuarioId;

    @RestForm("tipoDocumento")
    @NotNull
    public TipoDocumento tipoDocumento;

    @RestForm("arquivo")
    @NotNull
    public FileUpload arquivo;
}
