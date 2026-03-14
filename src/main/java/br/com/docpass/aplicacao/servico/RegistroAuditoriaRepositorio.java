package br.com.docpass.aplicacao.servico;

import br.com.docpass.dominio.modelo.RegistroAuditoria;

public interface RegistroAuditoriaRepositorio {
    RegistroAuditoria salvar(RegistroAuditoria registro);
}
