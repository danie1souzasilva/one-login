package br.com.docpass.adaptador.entrada.dto.requisicao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

public class CriarUsuarioRequisicao {
    @NotNull
    @Valid
    public DadosBasicosRequisicao dadosBasicos;

    @Valid
    public DadosComplementaresRequisicao dadosComplementares;

    @Valid
    public DocumentosRequisicao documentos;

    @Valid
    public EnderecoRequisicao endereco;

    @Valid
    public DadosProfissionaisRequisicao dadosProfissionais;

    @Valid
    public DadosAdicionaisRequisicao dadosAdicionais;

    @Valid
    public RedesSociaisRequisicao redesSociais;
}
