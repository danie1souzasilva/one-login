package br.com.docpass.observabilidade;

import org.eclipse.microprofile.health.Readiness;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Readiness
@ApplicationScoped
public class SaudeKafkaCheck implements HealthCheck {
    @ConfigProperty(name = "kafka.bootstrap.servers")
    String bootstrapServers;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.named("kafka");
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        try (AdminClient admin = AdminClient.create(props)) {
            admin.listTopics().names().get(2, TimeUnit.SECONDS);
            return builder.up().build();
        } catch (Exception ex) {
            return builder.down().withData("erro", ex.getMessage()).build();
        }
    }
}
