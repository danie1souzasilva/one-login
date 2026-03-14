package br.com.docpass.aplicacao.servico;

import br.com.docpass.dominio.modelo.Consentimento;
import io.vavr.collection.List;
import io.vavr.control.Option;

import java.util.UUID;

public interface ConsentimentoRepositorio {
    Consentimento salvar(Consentimento consentimento);

    List<Consentimento> buscarPorUsuarioId(UUID usuarioId);

    Option<Consentimento> buscarPorUsuarioIdEEmpresaId(UUID usuarioId, UUID empresaId);
}
