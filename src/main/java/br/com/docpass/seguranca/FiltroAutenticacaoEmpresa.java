package br.com.docpass.seguranca;

import br.com.docpass.aplicacao.servico.EmpresaRepositorio;
import br.com.docpass.dominio.modelo.Empresa;
import io.vavr.control.Option;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
@Priority(Priorities.AUTHENTICATION)
@ApplicationScoped
public class FiltroAutenticacaoEmpresa implements ContainerRequestFilter {
    @Inject
    EmpresaRepositorio empresaRepositorio;

    @Inject
    LimitadorRequisicoesEmpresa limitadorRequisicoesEmpresa;

    @ConfigProperty(name = "docpass.seguranca.api-empresas.ativo", defaultValue = "true")
    boolean ativo;

    @Override
    public void filter(ContainerRequestContext contexto) {
        String caminho = contexto.getUriInfo().getPath();
        if (!caminho.startsWith("api-empresas")) {
            return;
        }

        if (!ativo) {
            return;
        }

        String chaveApi = contexto.getHeaderString("X-API-KEY");
        if (chaveApi == null || chaveApi.isBlank()) {
            contexto.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        Option<Empresa> empresa = empresaRepositorio.buscarPorChaveApi(chaveApi);
        if (empresa.isEmpty()) {
            contexto.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        if (!limitadorRequisicoesEmpresa.permitir(empresa.get().getId())) {
            contexto.abortWith(Response.status(429).build());
            return;
        }

        contexto.setProperty("empresaId", empresa.get().getId());
    }
}
