package br.com.docpass.adaptador.saida.armazenamento;

import br.com.docpass.aplicacao.servico.ArmazenamentoArquivo;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;

@ApplicationScoped
public class ArmazenamentoS3 implements ArmazenamentoArquivo {
    private final S3Client s3Client;
    private final String bucket;

    public ArmazenamentoS3(S3Client s3Client, @ConfigProperty(name = "docpass.storage.bucket") String bucket) {
        this.s3Client = s3Client;
        this.bucket = bucket;
    }

    @Override
    public String enviar(String chaveObjeto, InputStream conteudo, String tipoConteudo) {
        try {
            byte[] bytes = conteudo.readAllBytes();
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(chaveObjeto)
                    .contentType(tipoConteudo)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(bytes));
            return "s3://" + bucket + "/" + chaveObjeto;
        } catch (Exception ex) {
            throw new IllegalStateException("Falha ao enviar arquivo para o storage", ex);
        }
    }
}
