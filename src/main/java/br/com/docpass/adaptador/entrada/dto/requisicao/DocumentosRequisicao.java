package br.com.docpass.adaptador.entrada.dto.requisicao;

import br.com.docpass.dominio.modelo.CategoriaCnh;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class DocumentosRequisicao {
    public String rgNumero;
    public String rgOrgaoEmissor;

    @Pattern(regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$",
            message = "rgEstadoEmissor deve ser UF valida")
    public String rgEstadoEmissor;

    public LocalDate rgDataEmissao;

    public String cnhNumero;
    public CategoriaCnh cnhCategoria;
    public LocalDate cnhValidade;

    public String tituloEleitorNumero;
    public String passaporteNumero;
}
