package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.dominio.modelo.CategoriaCnh;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

@Embeddable
public class DocumentosPersistencia {
    @Column(name = "rg_numero")
    public String rgNumero;

    @Column(name = "rg_orgao_emissor")
    public String rgOrgaoEmissor;

    @Column(name = "rg_estado_emissor")
    public String rgEstadoEmissor;

    @Column(name = "rg_data_emissao")
    public LocalDate rgDataEmissao;

    @Column(name = "cnh_numero")
    public String cnhNumero;

    @Enumerated(EnumType.STRING)
    @Column(name = "cnh_categoria")
    public CategoriaCnh cnhCategoria;

    @Column(name = "cnh_validade")
    public LocalDate cnhValidade;

    @Column(name = "titulo_eleitor_numero")
    public String tituloEleitorNumero;

    @Column(name = "passaporte_numero")
    public String passaporteNumero;
}
