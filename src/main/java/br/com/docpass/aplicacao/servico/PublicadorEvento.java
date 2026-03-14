package br.com.docpass.aplicacao.servico;

import br.com.docpass.aplicacao.evento.ConsentimentoConcedidoEvento;
import br.com.docpass.aplicacao.evento.DocumentoEnviadoEvento;
import br.com.docpass.aplicacao.evento.PerfilAtualizadoEvento;

public interface PublicadorEvento {
    void publicar(PerfilAtualizadoEvento evento);

    void publicar(DocumentoEnviadoEvento evento);

    void publicar(ConsentimentoConcedidoEvento evento);
}
