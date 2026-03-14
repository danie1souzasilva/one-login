package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.aplicacao.servico.UsuarioRepositorio;
import br.com.docpass.dominio.modelo.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.vavr.control.Option;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UsuarioRepositorioPanache implements UsuarioRepositorio, PanacheRepositoryBase<UsuarioEntidade, UUID> {
    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntidade entidade = MapeadorDominioPersistencia.paraEntidade(usuario);
        persist(entidade);
        return MapeadorDominioPersistencia.paraDominio(entidade);
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        UsuarioEntidade entidade = MapeadorDominioPersistencia.paraEntidade(usuario);
        getEntityManager().merge(entidade);
        return MapeadorDominioPersistencia.paraDominio(entidade);
    }

    @Override
    public Option<Usuario> buscarPorId(UUID id) {
        java.util.Optional<UsuarioEntidade> opcional = find("id", id).firstResultOptional();
        return Option.ofOptional(opcional)
                .map(MapeadorDominioPersistencia::paraDominio);
    }

    @Override
    public Option<Usuario> buscarPorEmail(String email) {
        java.util.Optional<UsuarioEntidade> opcional = find("dadosBasicos.email", email).firstResultOptional();
        return Option.ofOptional(opcional)
                .map(MapeadorDominioPersistencia::paraDominio);
    }

    @Override
    public Option<Usuario> buscarPorCpf(String cpf) {
        java.util.Optional<UsuarioEntidade> opcional = find("dadosBasicos.cpf", cpf).firstResultOptional();
        return Option.ofOptional(opcional)
                .map(MapeadorDominioPersistencia::paraDominio);
    }
}
