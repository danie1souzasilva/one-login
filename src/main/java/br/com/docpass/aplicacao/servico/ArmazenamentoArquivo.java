package br.com.docpass.aplicacao.servico;

import java.io.InputStream;

public interface ArmazenamentoArquivo {
    String enviar(String chaveObjeto, InputStream conteudo, String tipoConteudo);
}
