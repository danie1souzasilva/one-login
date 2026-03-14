package br.com.docpass.aplicacao.caso;

import br.com.docpass.adaptador.entrada.dto.requisicao.CriarEmpresaRequisicao;
import br.com.docpass.adaptador.entrada.dto.resposta.EmpresaResposta;
import br.com.docpass.adaptador.entrada.mapeador.EmpresaMapeador;
import br.com.docpass.aplicacao.erro.ErroDominio;
import br.com.docpass.aplicacao.erro.ErroValidacao;
import br.com.docpass.aplicacao.servico.EmpresaRepositorio;
import br.com.docpass.dominio.modelo.Empresa;

import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@ApplicationScoped
public class CriarEmpresaCasoUso {
    private final EmpresaRepositorio empresaRepositorio;
    private final EmpresaMapeador empresaMapeador;

    public CriarEmpresaCasoUso(EmpresaRepositorio empresaRepositorio, EmpresaMapeador empresaMapeador) {
        this.empresaRepositorio = empresaRepositorio;
        this.empresaMapeador = empresaMapeador;
    }

    @Transactional
    public Either<ErroDominio, EmpresaResposta> executar(CriarEmpresaRequisicao requisicao) {
        return Try.of(() -> persistirEmpresa(requisicao))
                .toEither()
                .mapLeft(ex -> new ErroValidacao("Falha ao registrar empresa"));
    }

    private EmpresaResposta persistirEmpresa(CriarEmpresaRequisicao requisicao) {
        Empresa empresa = empresaMapeador.paraEntidade(requisicao);
        empresa.setId(UUID.randomUUID());
        empresa.setChaveApi(gerarChaveApi());
        empresa.setCriadoEm(Instant.now());
        Empresa salva = empresaRepositorio.salvar(empresa);
        return empresaMapeador.paraResposta(salva);
    }

    private String gerarChaveApi() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
