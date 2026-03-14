package br.com.docpass.dominio.modelo;

import java.time.Instant;
import java.util.UUID;

public class Usuario {
    private UUID id;
    private DadosBasicos dadosBasicos;
    private DadosComplementares dadosComplementares;
    private Documentos documentos;
    private Endereco endereco;
    private DadosProfissionais dadosProfissionais;
    private DadosAdicionais dadosAdicionais;
    private RedesSociais redesSociais;
    private Instant criadoEm;

    public Usuario() {
    }

    public void atualizarPerfil(Usuario atualizado) {
        this.dadosBasicos = atualizado.dadosBasicos;
        this.dadosComplementares = atualizado.dadosComplementares;
        this.documentos = atualizado.documentos;
        this.endereco = atualizado.endereco;
        this.dadosProfissionais = atualizado.dadosProfissionais;
        this.dadosAdicionais = atualizado.dadosAdicionais;
        this.redesSociais = atualizado.redesSociais;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public DadosBasicos getDadosBasicos() {
        return dadosBasicos;
    }

    public void setDadosBasicos(DadosBasicos dadosBasicos) {
        this.dadosBasicos = dadosBasicos;
    }

    public DadosComplementares getDadosComplementares() {
        return dadosComplementares;
    }

    public void setDadosComplementares(DadosComplementares dadosComplementares) {
        this.dadosComplementares = dadosComplementares;
    }

    public Documentos getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Documentos documentos) {
        this.documentos = documentos;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public DadosProfissionais getDadosProfissionais() {
        return dadosProfissionais;
    }

    public void setDadosProfissionais(DadosProfissionais dadosProfissionais) {
        this.dadosProfissionais = dadosProfissionais;
    }

    public DadosAdicionais getDadosAdicionais() {
        return dadosAdicionais;
    }

    public void setDadosAdicionais(DadosAdicionais dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    public RedesSociais getRedesSociais() {
        return redesSociais;
    }

    public void setRedesSociais(RedesSociais redesSociais) {
        this.redesSociais = redesSociais;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }
}
