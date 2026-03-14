package br.com.docpass.aplicacao.servico;

import br.com.docpass.dominio.modelo.Usuario;
import io.vavr.control.Option;

import java.util.UUID;

public interface UsuarioRepositorio {
    Usuario salvar(Usuario usuario);

    Usuario atualizar(Usuario usuario);

    Option<Usuario> buscarPorId(UUID id);

    Option<Usuario> buscarPorEmail(String email);

    Option<Usuario> buscarPorCpf(String cpf);
}
