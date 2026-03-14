package br.com.docpass.adaptador.entrada.dto.requisicao;

import jakarta.validation.constraints.NotBlank;

public class CriarEmpresaRequisicao {
    @NotBlank
    public String nome;
}
