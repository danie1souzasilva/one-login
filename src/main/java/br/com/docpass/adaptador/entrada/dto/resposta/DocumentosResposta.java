package br.com.docpass.adaptador.entrada.dto.resposta;

import br.com.docpass.dominio.modelo.CategoriaCnh;

import java.time.LocalDate;

public class DocumentosResposta {
    public String rgNumero;
    public String rgOrgaoEmissor;
    public String rgEstadoEmissor;
    public LocalDate rgDataEmissao;

    public String cnhNumero;
    public CategoriaCnh cnhCategoria;
    public LocalDate cnhValidade;

    public String tituloEleitorNumero;
    public String passaporteNumero;
}
