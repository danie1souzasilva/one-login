package br.com.docpass.observabilidade;

import org.eclipse.microprofile.health.Readiness;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import io.agroal.api.AgroalDataSource;

@Readiness
@ApplicationScoped
public class SaudeBancoDadosCheck implements HealthCheck {
    @Inject
    AgroalDataSource dataSource;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.named("banco_dados");
        try (var conn = dataSource.getConnection()) {
            return builder.up().build();
        } catch (Exception ex) {
            return builder.down().withData("erro", ex.getMessage()).build();
        }
    }
}
