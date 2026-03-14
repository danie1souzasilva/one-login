package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.aplicacao.evento.ConsentimentoConcedidoEvento;
import br.com.docpass.aplicacao.evento.DocumentoEnviadoEvento;
import br.com.docpass.aplicacao.evento.PerfilAtualizadoEvento;
import br.com.docpass.aplicacao.servico.PublicadorEvento;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PublicadorEventoKafka implements PublicadorEvento {
    private final Emitter<String> perfilEmitter;
    private final Emitter<String> documentoEmitter;
    private final Emitter<String> consentimentoEmitter;
    private final ObjectMapper objectMapper;

    public PublicadorEventoKafka(@Channel("perfil_atualizado") Emitter<String> perfilEmitter,
                                 @Channel("documento_enviado") Emitter<String> documentoEmitter,
                                 @Channel("consentimento_concedido") Emitter<String> consentimentoEmitter,
                                 ObjectMapper objectMapper) {
        this.perfilEmitter = perfilEmitter;
        this.documentoEmitter = documentoEmitter;
        this.consentimentoEmitter = consentimentoEmitter;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publicar(PerfilAtualizadoEvento evento) {
        perfilEmitter.send(paraJson("perfil_atualizado", Map.of("usuarioId", evento.usuarioId().toString())));
    }

    @Override
    public void publicar(DocumentoEnviadoEvento evento) {
        Map<String, String> payload = new HashMap<>();
        payload.put("documentoId", evento.documentoId().toString());
        payload.put("usuarioId", evento.usuarioId().toString());
        documentoEmitter.send(paraJson("documento_enviado", payload));
    }

    @Override
    public void publicar(ConsentimentoConcedidoEvento evento) {
        Map<String, String> payload = new HashMap<>();
        payload.put("consentimentoId", evento.consentimentoId().toString());
        payload.put("usuarioId", evento.usuarioId().toString());
        payload.put("empresaId", evento.empresaId().toString());
        consentimentoEmitter.send(paraJson("consentimento_concedido", payload));
    }

    private String paraJson(String evento, Map<String, String> dados) {
        Map<String, Object> envelope = new HashMap<>();
        envelope.put("evento", evento);
        envelope.put("timestamp", Instant.now().toString());
        envelope.put("dados", dados);
        try {
            return objectMapper.writeValueAsString(envelope);
        } catch (Exception ex) {
            throw new IllegalStateException("Falha ao serializar evento", ex);
        }
    }
}
