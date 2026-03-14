package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.aplicacao.servico.EmpresaRepositorio;
import br.com.docpass.dominio.modelo.Empresa;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.vavr.control.Option;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class EmpresaRepositorioPanache implements EmpresaRepositorio, PanacheRepositoryBase<EmpresaEntidade, UUID> {
    @Override
    public Empresa salvar(Empresa empresa) {
        EmpresaEntidade entidade = MapeadorDominioPersistencia.paraEntidade(empresa);
        persist(entidade);
        return MapeadorDominioPersistencia.paraDominio(entidade);
    }

    @Override
    public Option<Empresa> buscarPorId(UUID id) {
        java.util.Optional<EmpresaEntidade> opcional = find("id", id).firstResultOptional();
        return Option.ofOptional(opcional)
                .map(MapeadorDominioPersistencia::paraDominio);
    }

    @Override
    public Option<Empresa> buscarPorChaveApi(String chaveApi) {
        java.util.Optional<EmpresaEntidade> opcional = find("chaveApi", chaveApi).firstResultOptional();
        return Option.ofOptional(opcional)
                .map(MapeadorDominioPersistencia::paraDominio);
    }
}
