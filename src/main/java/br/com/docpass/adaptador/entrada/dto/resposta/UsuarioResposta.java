package br.com.docpass.adaptador.entrada.dto.resposta;

import java.time.Instant;
import java.util.UUID;

public class UsuarioResposta {
    public UUID id;
    public DadosBasicosResposta dadosBasicos;
    public DadosComplementaresResposta dadosComplementares;
    public DocumentosResposta documentos;
    public EnderecoResposta endereco;
    public DadosProfissionaisResposta dadosProfissionais;
    public DadosAdicionaisResposta dadosAdicionais;
    public RedesSociaisResposta redesSociais;
    public Instant criadoEm;
}
