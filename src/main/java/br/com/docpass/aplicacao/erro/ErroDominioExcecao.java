package br.com.docpass.aplicacao.erro;

public class ErroDominioExcecao extends RuntimeException {
    private final ErroDominio erro;

    public ErroDominioExcecao(ErroDominio erro) {
        super(erro.mensagem());
        this.erro = erro;
    }

    public ErroDominio getErro() {
        return erro;
    }
}
