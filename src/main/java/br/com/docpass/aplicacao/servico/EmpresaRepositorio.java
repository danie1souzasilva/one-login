package br.com.docpass.aplicacao.servico;

import br.com.docpass.dominio.modelo.Empresa;
import io.vavr.control.Option;

import java.util.UUID;

public interface EmpresaRepositorio {
    Empresa salvar(Empresa empresa);

    Option<Empresa> buscarPorId(UUID id);

    Option<Empresa> buscarPorChaveApi(String chaveApi);
}
