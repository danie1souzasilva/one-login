package br.com.docpass.observabilidade;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.MDC;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Provider
@Priority(Priorities.AUTHENTICATION + 1)
@ApplicationScoped
public class FiltroLogRequisicao implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F\\-]{36}");

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        MDC.put("servico", "docpass");
        MDC.put("endpoint", path);

        Object empresaId = requestContext.getProperty("empresaId");
        if (empresaId != null) {
            MDC.put("empresaId", empresaId.toString());
        }

        Matcher matcher = UUID_PATTERN.matcher(path);
        if (matcher.find()) {
            MDC.put("usuarioId", matcher.group());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        MDC.clear();
    }
}
