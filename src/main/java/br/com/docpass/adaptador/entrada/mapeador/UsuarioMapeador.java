package br.com.docpass.adaptador.entrada.mapeador;

import br.com.docpass.adaptador.entrada.dto.requisicao.AtualizarUsuarioRequisicao;
import br.com.docpass.adaptador.entrada.dto.requisicao.CriarUsuarioRequisicao;
import br.com.docpass.adaptador.entrada.dto.resposta.UsuarioResposta;
import br.com.docpass.dominio.modelo.Usuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "cdi")
public interface UsuarioMapeador {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    Usuario paraEntidade(CriarUsuarioRequisicao requisicao);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "criadoEm", ignore = true)
    Usuario paraEntidade(UUID id, AtualizarUsuarioRequisicao requisicao);

    UsuarioResposta paraResposta(Usuario usuario);
}
