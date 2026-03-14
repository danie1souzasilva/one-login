package br.com.docpass.observabilidade;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.USER)
@ApplicationScoped
public class FiltroMetricasApi implements ContainerRequestFilter, ContainerResponseFilter {
    private static final String ATRIBUTO_INICIO = "inicioRequisicao";

    @Inject
    MeterRegistry meterRegistry;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        if (path.startsWith("api/") || path.startsWith("api-empresas/")) {
            requestContext.setProperty(ATRIBUTO_INICIO, System.nanoTime());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        Object inicio = requestContext.getProperty(ATRIBUTO_INICIO);
        if (inicio instanceof Long inicioNano) {
            String endpoint = requestContext.getUriInfo().getPath();
            meterRegistry.counter("quantidade_requisicoes_api", "endpoint", endpoint).increment();
            Timer timer = meterRegistry.timer("tempo_resposta_api", "endpoint", endpoint);
            timer.record(System.nanoTime() - inicioNano, java.util.concurrent.TimeUnit.NANOSECONDS);
        }
    }
}
