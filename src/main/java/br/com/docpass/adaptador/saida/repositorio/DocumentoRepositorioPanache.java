package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.aplicacao.servico.DocumentoRepositorio;
import br.com.docpass.dominio.modelo.Documento;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.vavr.collection.List;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class DocumentoRepositorioPanache implements DocumentoRepositorio, PanacheRepositoryBase<DocumentoEntidade, UUID> {
    @Override
    public Documento salvar(Documento documento) {
        DocumentoEntidade entidade = MapeadorDominioPersistencia.paraEntidade(documento);
        persist(entidade);
        return MapeadorDominioPersistencia.paraDominio(entidade);
    }

    @Override
    public List<Documento> buscarPorUsuarioId(UUID usuarioId) {
        java.util.List<Documento> resultados = find("usuarioId", usuarioId).list().stream()
                .map(MapeadorDominioPersistencia::paraDominio)
                .collect(Collectors.toList());
        return List.ofAll(resultados);
    }
}
