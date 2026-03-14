package br.com.docpass.observabilidade;

import org.eclipse.microprofile.health.Readiness;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import software.amazon.awssdk.services.s3.S3Client;

@Readiness
@ApplicationScoped
public class SaudeStorageCheck implements HealthCheck {
    @Inject
    S3Client s3Client;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.named("storage");
        try {
            s3Client.listBuckets();
            return builder.up().build();
        } catch (Exception ex) {
            return builder.down().withData("erro", ex.getMessage()).build();
        }
    }
}
