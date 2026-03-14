package br.com.docpass.adaptador.entrada.mapeador;

import br.com.docpass.adaptador.entrada.dto.requisicao.CriarEmpresaRequisicao;
import br.com.docpass.adaptador.entrada.dto.resposta.EmpresaResposta;
import br.com.docpass.dominio.modelo.Empresa;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface EmpresaMapeador {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chaveApi", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    Empresa paraEntidade(CriarEmpresaRequisicao requisicao);

    EmpresaResposta paraResposta(Empresa empresa);
}
