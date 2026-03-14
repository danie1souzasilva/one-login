package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.aplicacao.servico.ConsentimentoRepositorio;
import br.com.docpass.dominio.modelo.Consentimento;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.vavr.collection.List;
import io.vavr.control.Option;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ConsentimentoRepositorioPanache implements ConsentimentoRepositorio, PanacheRepositoryBase<ConsentimentoEntidade, UUID> {
    @Override
    public Consentimento salvar(Consentimento consentimento) {
        ConsentimentoEntidade entidade = MapeadorDominioPersistencia.paraEntidade(consentimento);
        persist(entidade);
        return MapeadorDominioPersistencia.paraDominio(entidade);
    }

    @Override
    public List<Consentimento> buscarPorUsuarioId(UUID usuarioId) {
        java.util.List<ConsentimentoEntidade> entidades = find("usuarioId", usuarioId).list();
        java.util.List<Consentimento> resultados = entidades.stream()
                .map(MapeadorDominioPersistencia::paraDominio)
                .collect(Collectors.toList());
        return List.ofAll(resultados);
    }

    @Override
    public Option<Consentimento> buscarPorUsuarioIdEEmpresaId(UUID usuarioId, UUID empresaId) {
        java.util.Optional<ConsentimentoEntidade> opcional = find("usuarioId = ?1 and empresaId = ?2", usuarioId, empresaId)
                .firstResultOptional();
        return Option.ofOptional(opcional)
                .map(MapeadorDominioPersistencia::paraDominio);
    }
}
