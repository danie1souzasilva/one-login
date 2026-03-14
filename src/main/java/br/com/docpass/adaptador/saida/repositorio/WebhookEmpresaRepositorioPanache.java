package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.aplicacao.servico.WebhookEmpresaRepositorio;
import br.com.docpass.dominio.modelo.WebhookEmpresa;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.vavr.collection.List;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class WebhookEmpresaRepositorioPanache implements WebhookEmpresaRepositorio, PanacheRepositoryBase<WebhookEmpresaEntidade, UUID> {
    @Override
    public WebhookEmpresa salvar(WebhookEmpresa webhook) {
        WebhookEmpresaEntidade entidade = MapeadorDominioPersistencia.paraEntidade(webhook);
        persist(entidade);
        return MapeadorDominioPersistencia.paraDominio(entidade);
    }

    @Override
    public List<WebhookEmpresa> buscarPorEmpresaId(UUID empresaId) {
        java.util.List<WebhookEmpresa> resultados = find("empresaId", empresaId).list().stream()
                .map(MapeadorDominioPersistencia::paraDominio)
                .collect(Collectors.toList());
        return List.ofAll(resultados);
    }

    @Override
    public List<WebhookEmpresa> buscarPorEvento(String evento) {
        java.util.List<WebhookEmpresa> resultados = listAll().stream()
                .map(MapeadorDominioPersistencia::paraDominio)
                .filter(webhook -> webhook.getEventosAssinados() != null && webhook.getEventosAssinados().contains(evento))
                .collect(Collectors.toList());
        return List.ofAll(resultados);
    }
}
