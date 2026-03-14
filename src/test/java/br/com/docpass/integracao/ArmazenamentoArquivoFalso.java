package br.com.docpass.integracao;

import br.com.docpass.aplicacao.servico.ArmazenamentoArquivo;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

import java.io.InputStream;

@Alternative
@Priority(1)
@ApplicationScoped
public class ArmazenamentoArquivoFalso implements ArmazenamentoArquivo {
    @Override
    public String enviar(String chaveObjeto, InputStream conteudo, String tipoConteudo) {
        return "s3://teste/" + chaveObjeto;
    }
}
