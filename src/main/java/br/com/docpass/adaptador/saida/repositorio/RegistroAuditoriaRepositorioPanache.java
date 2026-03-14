package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.aplicacao.servico.RegistroAuditoriaRepositorio;
import br.com.docpass.dominio.modelo.RegistroAuditoria;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class RegistroAuditoriaRepositorioPanache implements RegistroAuditoriaRepositorio, PanacheRepositoryBase<RegistroAuditoriaEntidade, UUID> {
    @Override
    public RegistroAuditoria salvar(RegistroAuditoria registro) {
        RegistroAuditoriaEntidade entidade = MapeadorDominioPersistencia.paraEntidade(registro);
        persist(entidade);
        return MapeadorDominioPersistencia.paraDominio(entidade);
    }
}
