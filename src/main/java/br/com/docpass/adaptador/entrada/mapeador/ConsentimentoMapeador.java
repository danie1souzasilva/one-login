package br.com.docpass.adaptador.entrada.mapeador;

import br.com.docpass.adaptador.entrada.dto.requisicao.CriarConsentimentoRequisicao;
import br.com.docpass.adaptador.entrada.dto.resposta.ConsentimentoResposta;
import br.com.docpass.dominio.modelo.Consentimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ConsentimentoMapeador {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    Consentimento paraEntidade(CriarConsentimentoRequisicao requisicao);

    ConsentimentoResposta paraResposta(Consentimento consentimento);
}
