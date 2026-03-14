package br.com.docpass.webhook;

import br.com.docpass.aplicacao.servico.WebhookEmpresaRepositorio;
import br.com.docpass.dominio.modelo.WebhookEmpresa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ServicoWebhook {

    private final WebhookEmpresaRepositorio webhookEmpresaRepositorio;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public ServicoWebhook(WebhookEmpresaRepositorio webhookEmpresaRepositorio, ObjectMapper objectMapper) {
        this.webhookEmpresaRepositorio = webhookEmpresaRepositorio;
        this.objectMapper = objectMapper;
    }

    @Incoming("perfil_atualizado_in")
    @Blocking
    public void consumirPerfilAtualizado(String payload) {
        processarEvento("perfil_atualizado", payload);
    }

    @Incoming("documento_enviado_in")
    @Blocking
    public void consumirDocumentoEnviado(String payload) {
        processarEvento("documento_enviado", payload);
    }

    @Incoming("consentimento_concedido_in")
    @Blocking
    public void consumirConsentimentoConcedido(String payload) {
        processarEvento("consentimento_concedido", payload);
    }

    @Transactional
    void processarEvento(String evento, String payload) {
        String usuarioId = extrairUsuarioId(payload);

        for (WebhookEmpresa webhook : webhookEmpresaRepositorio.buscarPorEvento(evento)) {
            enviarWebhook(webhook.getUrl(), evento, usuarioId);
        }
    }

    private String extrairUsuarioId(String payload) {
        try {
            Map<String, Object> envelope = objectMapper.readValue(payload, new TypeReference<>() {});
            Object dados = envelope.get("dados");

            if (dados instanceof Map<?, ?> mapa) {
                Object usuarioId = mapa.get("usuarioId");
                return usuarioId != null ? usuarioId.toString() : null;
            }

            return null;

        } catch (Exception ex) {
            System.err.println("Erro ao extrair usuarioId do payload: " + ex.getMessage());
            return null;
        }
    }

    private void enviarWebhook(String url, String evento, String usuarioId) {
        try {

            Map<String, Object> body = new HashMap<>();
            body.put("evento", evento);
            body.put("usuarioId", usuarioId);
            body.put("timestamp", Instant.now().toString());

            String json = objectMapper.writeValueAsString(body);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .build();

            httpClient
                    .sendAsync(request, HttpResponse.BodyHandlers.discarding())
                    .thenAccept(response -> {
                        if (response.statusCode() >= 400) {
                            System.err.println("Webhook retornou erro HTTP " + response.statusCode() + " para " + url);
                        }
                    })
                    .exceptionally(ex -> {
                        System.err.println("Falha ao enviar webhook para " + url + ": " + ex.getMessage());
                        return null;
                    });

        } catch (Exception ex) {
            System.err.println("Erro ao montar requisição de webhook: " + ex.getMessage());
        }
    }
}