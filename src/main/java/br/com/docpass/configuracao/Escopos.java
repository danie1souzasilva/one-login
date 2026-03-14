package br.com.docpass.configuracao;

@Deprecated
public final class Escopos {
    public static final String PERFIL_BASICO = br.com.docpass.configuracao.seguranca.EscoposSeguranca.PERFIL_BASICO;
    public static final String DOCUMENTOS = br.com.docpass.configuracao.seguranca.EscoposSeguranca.DOCUMENTOS;
    public static final String ENDERECO = br.com.docpass.configuracao.seguranca.EscoposSeguranca.ENDERECO;
    public static final String DADOS_FINANCEIROS = br.com.docpass.configuracao.seguranca.EscoposSeguranca.DADOS_FINANCEIROS;
    public static final String DADOS_SENSIVEIS = br.com.docpass.configuracao.seguranca.EscoposSeguranca.DADOS_SENSIVEIS;

    private Escopos() {
    }
}
