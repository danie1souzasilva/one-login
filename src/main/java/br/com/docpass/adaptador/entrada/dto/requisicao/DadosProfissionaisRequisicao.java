package br.com.docpass.adaptador.entrada.dto.requisicao;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class DadosProfissionaisRequisicao {
    public String profissao;
    public String empresaAtual;

    @Positive(message = "rendaMensal deve ser positiva")
    public BigDecimal rendaMensal;
}
