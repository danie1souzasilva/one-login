package br.com.docpass.integracao;

import br.com.docpass.aplicacao.evento.ConsentimentoConcedidoEvento;
import br.com.docpass.aplicacao.evento.DocumentoEnviadoEvento;
import br.com.docpass.aplicacao.evento.PerfilAtualizadoEvento;
import br.com.docpass.aplicacao.servico.PublicadorEvento;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

@Alternative
@Priority(1)
@ApplicationScoped
public class PublicadorEventoFalso implements PublicadorEvento {
    @Override
    public void publicar(PerfilAtualizadoEvento evento) {
    }

    @Override
    public void publicar(DocumentoEnviadoEvento evento) {
    }

    @Override
    public void publicar(ConsentimentoConcedidoEvento evento) {
    }
}
