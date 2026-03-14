package br.com.docpass.aplicacao.servico;

import br.com.docpass.dominio.modelo.RegistroAuditoria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ServicoAuditoria {
    private final RegistroAuditoriaRepositorio registroAuditoriaRepositorio;

    public ServicoAuditoria(RegistroAuditoriaRepositorio registroAuditoriaRepositorio) {
        this.registroAuditoriaRepositorio = registroAuditoriaRepositorio;
    }

    @Transactional
    public RegistroAuditoria registrar(RegistroAuditoria registro) {
        return registroAuditoriaRepositorio.salvar(registro);
    }
}
