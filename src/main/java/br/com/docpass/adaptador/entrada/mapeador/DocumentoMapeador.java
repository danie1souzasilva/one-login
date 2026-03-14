package br.com.docpass.adaptador.entrada.mapeador;

import br.com.docpass.adaptador.entrada.dto.requisicao.EnviarDocumentoRequisicao;
import br.com.docpass.adaptador.entrada.dto.resposta.DocumentoResposta;
import br.com.docpass.dominio.modelo.Documento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface DocumentoMapeador {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "urlArquivo", ignore = true)
    @Mapping(target = "statusVerificacao", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    Documento paraEntidade(EnviarDocumentoRequisicao requisicao);

    DocumentoResposta paraResposta(Documento documento);
}
