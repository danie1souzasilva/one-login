package br.com.docpass.aplicacao.caso;

import br.com.docpass.adaptador.entrada.dto.requisicao.EnviarDocumentoRequisicao;
import br.com.docpass.adaptador.entrada.dto.resposta.DocumentoResposta;
import br.com.docpass.adaptador.entrada.mapeador.DocumentoMapeador;
import br.com.docpass.aplicacao.erro.ErroDominio;
import br.com.docpass.aplicacao.erro.DocumentoInvalidoErro;
import br.com.docpass.aplicacao.erro.ErroValidacao;
import br.com.docpass.aplicacao.evento.DocumentoEnviadoEvento;
import br.com.docpass.aplicacao.servico.DocumentoRepositorio;
import br.com.docpass.aplicacao.servico.PublicadorEvento;
import br.com.docpass.aplicacao.servico.ArmazenamentoArquivo;
import br.com.docpass.dominio.modelo.Documento;
import br.com.docpass.dominio.modelo.StatusVerificacao;

import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import io.micrometer.core.instrument.MeterRegistry;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class EnviarDocumentoCasoUso {
    private final DocumentoRepositorio documentoRepositorio;
    private final ArmazenamentoArquivo armazenamentoArquivo;
    private final PublicadorEvento publicadorEvento;
    private final DocumentoMapeador documentoMapeador;
    private final MeterRegistry meterRegistry;

    public EnviarDocumentoCasoUso(DocumentoRepositorio documentoRepositorio,
                                  ArmazenamentoArquivo armazenamentoArquivo,
                                  PublicadorEvento publicadorEvento,
                                  DocumentoMapeador documentoMapeador,
                                  MeterRegistry meterRegistry) {
        this.documentoRepositorio = documentoRepositorio;
        this.armazenamentoArquivo = armazenamentoArquivo;
        this.publicadorEvento = publicadorEvento;
        this.documentoMapeador = documentoMapeador;
        this.meterRegistry = meterRegistry;
    }

    @Transactional
    public Either<ErroDominio, DocumentoResposta> executar(EnviarDocumentoRequisicao requisicao) {
        return validarRequisicao(requisicao)
                .flatMap(valido -> Try.of(() -> persistirDocumento(requisicao))
                        .toEither()
                        .mapLeft(ex -> new DocumentoInvalidoErro("Falha ao armazenar documento")));
    }

    private Either<ErroDominio, Boolean> validarRequisicao(EnviarDocumentoRequisicao requisicao) {
        if (requisicao.conteudo == null || requisicao.tipoConteudo == null || requisicao.tipoConteudo.isBlank()) {
            return Either.left(new DocumentoInvalidoErro("Conteudo do documento invalido"));
        }
        if (requisicao.usuarioId == null || requisicao.tipoDocumento == null) {
            return Either.left(new ErroValidacao("Dados obrigatorios ausentes"));
        }
        return Either.right(true);
    }

    private DocumentoResposta persistirDocumento(EnviarDocumentoRequisicao requisicao) {
        Documento documento = documentoMapeador.paraEntidade(requisicao);
        documento.setId(UUID.randomUUID());
        documento.setCriadoEm(Instant.now());
        documento.setStatusVerificacao(StatusVerificacao.PENDENTE);
        String chaveObjeto = requisicao.usuarioId + "/" + documento.getId();
        String urlArquivo = armazenamentoArquivo.enviar(chaveObjeto, requisicao.conteudo, requisicao.tipoConteudo);
        documento.setUrlArquivo(urlArquivo);
        Documento salvo = documentoRepositorio.salvar(documento);
        publicadorEvento.publicar(new DocumentoEnviadoEvento(salvo.getId(), salvo.getUsuarioId(), Instant.now()));
        meterRegistry.counter("documentos_enviados_total").increment();
        return documentoMapeador.paraResposta(salvo);
    }
}
