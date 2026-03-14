package br.com.docpass.adaptador.saida.repositorio;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class DadosProfissionaisPersistencia {
    @Column(name = "profissao")
    public String profissao;

    @Column(name = "empresa_atual")
    public String empresaAtual;

    @Column(name = "renda_mensal")
    public BigDecimal rendaMensal;
}
