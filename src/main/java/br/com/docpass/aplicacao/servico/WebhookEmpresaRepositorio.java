package br.com.docpass.aplicacao.servico;

import br.com.docpass.dominio.modelo.WebhookEmpresa;
import io.vavr.collection.List;

import java.util.UUID;

public interface WebhookEmpresaRepositorio {
    WebhookEmpresa salvar(WebhookEmpresa webhook);

    List<WebhookEmpresa> buscarPorEmpresaId(UUID empresaId);

    List<WebhookEmpresa> buscarPorEvento(String evento);
}
