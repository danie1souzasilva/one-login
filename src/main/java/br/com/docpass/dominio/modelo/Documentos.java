package br.com.docpass.dominio.modelo;

import java.time.LocalDate;

public class Documentos {
    private String rgNumero;
    private String rgOrgaoEmissor;
    private String rgEstadoEmissor;
    private LocalDate rgDataEmissao;

    private String cnhNumero;
    private CategoriaCnh cnhCategoria;
    private LocalDate cnhValidade;

    private String tituloEleitorNumero;
    private String passaporteNumero;

    public String getRgNumero() {
        return rgNumero;
    }

    public void setRgNumero(String rgNumero) {
        this.rgNumero = rgNumero;
    }

    public String getRgOrgaoEmissor() {
        return rgOrgaoEmissor;
    }

    public void setRgOrgaoEmissor(String rgOrgaoEmissor) {
        this.rgOrgaoEmissor = rgOrgaoEmissor;
    }

    public String getRgEstadoEmissor() {
        return rgEstadoEmissor;
    }

    public void setRgEstadoEmissor(String rgEstadoEmissor) {
        this.rgEstadoEmissor = rgEstadoEmissor;
    }

    public LocalDate getRgDataEmissao() {
        return rgDataEmissao;
    }

    public void setRgDataEmissao(LocalDate rgDataEmissao) {
        this.rgDataEmissao = rgDataEmissao;
    }

    public String getCnhNumero() {
        return cnhNumero;
    }

    public void setCnhNumero(String cnhNumero) {
        this.cnhNumero = cnhNumero;
    }

    public CategoriaCnh getCnhCategoria() {
        return cnhCategoria;
    }

    public void setCnhCategoria(CategoriaCnh cnhCategoria) {
        this.cnhCategoria = cnhCategoria;
    }

    public LocalDate getCnhValidade() {
        return cnhValidade;
    }

    public void setCnhValidade(LocalDate cnhValidade) {
        this.cnhValidade = cnhValidade;
    }

    public String getTituloEleitorNumero() {
        return tituloEleitorNumero;
    }

    public void setTituloEleitorNumero(String tituloEleitorNumero) {
        this.tituloEleitorNumero = tituloEleitorNumero;
    }

    public String getPassaporteNumero() {
        return passaporteNumero;
    }

    public void setPassaporteNumero(String passaporteNumero) {
        this.passaporteNumero = passaporteNumero;
    }
}
