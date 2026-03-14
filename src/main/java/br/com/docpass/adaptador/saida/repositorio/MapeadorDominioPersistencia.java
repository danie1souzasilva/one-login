package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.dominio.modelo.DadosAdicionais;
import br.com.docpass.dominio.modelo.DadosBasicos;
import br.com.docpass.dominio.modelo.DadosComplementares;
import br.com.docpass.dominio.modelo.DadosProfissionais;
import br.com.docpass.dominio.modelo.Documentos;
import br.com.docpass.dominio.modelo.Empresa;
import br.com.docpass.dominio.modelo.Consentimento;
import br.com.docpass.dominio.modelo.Documento;
import br.com.docpass.dominio.modelo.Endereco;
import br.com.docpass.dominio.modelo.Usuario;
import br.com.docpass.dominio.modelo.WebhookEmpresa;
import br.com.docpass.dominio.modelo.RegistroAuditoria;
import br.com.docpass.dominio.modelo.RedesSociais;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

final class MapeadorDominioPersistencia {
    private MapeadorDominioPersistencia() {
    }

    static Usuario paraDominio(UsuarioEntidade entidade) {
        Usuario usuario = new Usuario();
        usuario.setId(entidade.id);
        usuario.setDadosBasicos(paraDadosBasicos(entidade.dadosBasicos));
        usuario.setDadosComplementares(paraDadosComplementares(entidade.dadosComplementares));
        usuario.setDocumentos(paraDocumentos(entidade.documentos));
        usuario.setEndereco(paraEndereco(entidade.endereco));
        usuario.setDadosProfissionais(paraDadosProfissionais(entidade.dadosProfissionais));
        usuario.setDadosAdicionais(paraDadosAdicionais(entidade.dadosAdicionais));
        usuario.setRedesSociais(paraRedesSociais(entidade.redesSociais));
        usuario.setCriadoEm(entidade.criadoEm);
        return usuario;
    }

    static UsuarioEntidade paraEntidade(Usuario usuario) {
        UsuarioEntidade entidade = new UsuarioEntidade();
        entidade.id = usuario.getId();
        entidade.dadosBasicos = paraDadosBasicosPersistencia(usuario.getDadosBasicos());
        entidade.dadosComplementares = paraDadosComplementaresPersistencia(usuario.getDadosComplementares());
        entidade.documentos = paraDocumentosPersistencia(usuario.getDocumentos());
        entidade.endereco = paraEnderecoPersistencia(usuario.getEndereco());
        entidade.dadosProfissionais = paraDadosProfissionaisPersistencia(usuario.getDadosProfissionais());
        entidade.dadosAdicionais = paraDadosAdicionaisPersistencia(usuario.getDadosAdicionais());
        entidade.redesSociais = paraRedesSociaisPersistencia(usuario.getRedesSociais());
        entidade.criadoEm = usuario.getCriadoEm();
        return entidade;
    }

    static Documento paraDominio(DocumentoEntidade entidade) {
        Documento documento = new Documento();
        documento.setId(entidade.id);
        documento.setUsuarioId(entidade.usuarioId);
        documento.setTipoDocumento(entidade.tipoDocumento);
        documento.setUrlArquivo(entidade.urlArquivo);
        documento.setStatusVerificacao(entidade.statusVerificacao);
        documento.setCriadoEm(entidade.criadoEm);
        return documento;
    }

    static DocumentoEntidade paraEntidade(Documento documento) {
        DocumentoEntidade entidade = new DocumentoEntidade();
        entidade.id = documento.getId();
        entidade.usuarioId = documento.getUsuarioId();
        entidade.tipoDocumento = documento.getTipoDocumento();
        entidade.urlArquivo = documento.getUrlArquivo();
        entidade.statusVerificacao = documento.getStatusVerificacao();
        entidade.criadoEm = documento.getCriadoEm();
        return entidade;
    }

    static Empresa paraDominio(EmpresaEntidade entidade) {
        Empresa empresa = new Empresa();
        empresa.setId(entidade.id);
        empresa.setNome(entidade.nome);
        empresa.setChaveApi(entidade.chaveApi);
        empresa.setCriadoEm(entidade.criadoEm);
        return empresa;
    }

    static EmpresaEntidade paraEntidade(Empresa empresa) {
        EmpresaEntidade entidade = new EmpresaEntidade();
        entidade.id = empresa.getId();
        entidade.nome = empresa.getNome();
        entidade.chaveApi = empresa.getChaveApi();
        entidade.criadoEm = empresa.getCriadoEm();
        return entidade;
    }

    static Consentimento paraDominio(ConsentimentoEntidade entidade) {
        Consentimento consentimento = new Consentimento();
        consentimento.setId(entidade.id);
        consentimento.setUsuarioId(entidade.usuarioId);
        consentimento.setEmpresaId(entidade.empresaId);
        consentimento.setEscopos(escoposDeString(entidade.escopos));
        consentimento.setCriadoEm(entidade.criadoEm);
        consentimento.setExpiraEm(entidade.expiraEm);
        return consentimento;
    }

    static ConsentimentoEntidade paraEntidade(Consentimento consentimento) {
        ConsentimentoEntidade entidade = new ConsentimentoEntidade();
        entidade.id = consentimento.getId();
        entidade.usuarioId = consentimento.getUsuarioId();
        entidade.empresaId = consentimento.getEmpresaId();
        entidade.escopos = escoposParaString(consentimento.getEscopos());
        entidade.criadoEm = consentimento.getCriadoEm();
        entidade.expiraEm = consentimento.getExpiraEm();
        return entidade;
    }

    static WebhookEmpresa paraDominio(WebhookEmpresaEntidade entidade) {
        WebhookEmpresa webhook = new WebhookEmpresa();
        webhook.setId(entidade.id);
        webhook.setEmpresaId(entidade.empresaId);
        webhook.setUrl(entidade.url);
        webhook.setEventosAssinados(escoposDeString(entidade.eventosAssinados));
        webhook.setCriadoEm(entidade.criadoEm);
        return webhook;
    }

    static WebhookEmpresaEntidade paraEntidade(WebhookEmpresa webhook) {
        WebhookEmpresaEntidade entidade = new WebhookEmpresaEntidade();
        entidade.id = webhook.getId();
        entidade.empresaId = webhook.getEmpresaId();
        entidade.url = webhook.getUrl();
        entidade.eventosAssinados = escoposParaString(webhook.getEventosAssinados());
        entidade.criadoEm = webhook.getCriadoEm();
        return entidade;
    }

    static RegistroAuditoria paraDominio(RegistroAuditoriaEntidade entidade) {
        RegistroAuditoria registro = new RegistroAuditoria();
        registro.setId(entidade.id);
        registro.setUsuarioId(entidade.usuarioId);
        registro.setEmpresaId(entidade.empresaId);
        registro.setTipoAcesso(entidade.tipoAcesso);
        registro.setEndpoint(entidade.endpoint);
        registro.setTimestamp(entidade.timestamp);
        registro.setIpRequisicao(entidade.ipRequisicao);
        return registro;
    }

    static RegistroAuditoriaEntidade paraEntidade(RegistroAuditoria registro) {
        RegistroAuditoriaEntidade entidade = new RegistroAuditoriaEntidade();
        entidade.id = registro.getId();
        entidade.usuarioId = registro.getUsuarioId();
        entidade.empresaId = registro.getEmpresaId();
        entidade.tipoAcesso = registro.getTipoAcesso();
        entidade.endpoint = registro.getEndpoint();
        entidade.timestamp = registro.getTimestamp();
        entidade.ipRequisicao = registro.getIpRequisicao();
        return entidade;
    }

    private static DadosBasicos paraDadosBasicos(DadosBasicosPersistencia persistencia) {
        if (persistencia == null) {
            return null;
        }
        DadosBasicos dados = new DadosBasicos();
        dados.setNomeCompleto(persistencia.nomeCompleto);
        dados.setCpf(persistencia.cpf);
        dados.setDataNascimento(persistencia.dataNascimento);
        dados.setEmail(persistencia.email);
        dados.setTelefone(persistencia.telefone);
        return dados;
    }

    private static DadosBasicosPersistencia paraDadosBasicosPersistencia(DadosBasicos dados) {
        if (dados == null) {
            return null;
        }
        DadosBasicosPersistencia persistencia = new DadosBasicosPersistencia();
        persistencia.nomeCompleto = dados.getNomeCompleto();
        persistencia.cpf = dados.getCpf();
        persistencia.dataNascimento = dados.getDataNascimento();
        persistencia.email = dados.getEmail();
        persistencia.telefone = dados.getTelefone();
        return persistencia;
    }

    private static DadosComplementares paraDadosComplementares(DadosComplementaresPersistencia persistencia) {
        if (persistencia == null) {
            return null;
        }
        DadosComplementares dados = new DadosComplementares();
        dados.setNomeSocial(persistencia.nomeSocial);
        dados.setGenero(persistencia.genero);
        dados.setSexo(persistencia.sexo);
        dados.setEstadoCivil(persistencia.estadoCivil);
        dados.setNacionalidade(persistencia.nacionalidade);
        dados.setNaturalidade(persistencia.naturalidade);
        dados.setNomeMae(persistencia.nomeMae);
        dados.setNomePai(persistencia.nomePai);
        return dados;
    }

    private static DadosComplementaresPersistencia paraDadosComplementaresPersistencia(DadosComplementares dados) {
        if (dados == null) {
            return null;
        }
        DadosComplementaresPersistencia persistencia = new DadosComplementaresPersistencia();
        persistencia.nomeSocial = dados.getNomeSocial();
        persistencia.genero = dados.getGenero();
        persistencia.sexo = dados.getSexo();
        persistencia.estadoCivil = dados.getEstadoCivil();
        persistencia.nacionalidade = dados.getNacionalidade();
        persistencia.naturalidade = dados.getNaturalidade();
        persistencia.nomeMae = dados.getNomeMae();
        persistencia.nomePai = dados.getNomePai();
        return persistencia;
    }

    private static Documentos paraDocumentos(DocumentosPersistencia persistencia) {
        if (persistencia == null) {
            return null;
        }
        Documentos documentos = new Documentos();
        documentos.setRgNumero(persistencia.rgNumero);
        documentos.setRgOrgaoEmissor(persistencia.rgOrgaoEmissor);
        documentos.setRgEstadoEmissor(persistencia.rgEstadoEmissor);
        documentos.setRgDataEmissao(persistencia.rgDataEmissao);
        documentos.setCnhNumero(persistencia.cnhNumero);
        documentos.setCnhCategoria(persistencia.cnhCategoria);
        documentos.setCnhValidade(persistencia.cnhValidade);
        documentos.setTituloEleitorNumero(persistencia.tituloEleitorNumero);
        documentos.setPassaporteNumero(persistencia.passaporteNumero);
        return documentos;
    }

    private static DocumentosPersistencia paraDocumentosPersistencia(Documentos documentos) {
        if (documentos == null) {
            return null;
        }
        DocumentosPersistencia persistencia = new DocumentosPersistencia();
        persistencia.rgNumero = documentos.getRgNumero();
        persistencia.rgOrgaoEmissor = documentos.getRgOrgaoEmissor();
        persistencia.rgEstadoEmissor = documentos.getRgEstadoEmissor();
        persistencia.rgDataEmissao = documentos.getRgDataEmissao();
        persistencia.cnhNumero = documentos.getCnhNumero();
        persistencia.cnhCategoria = documentos.getCnhCategoria();
        persistencia.cnhValidade = documentos.getCnhValidade();
        persistencia.tituloEleitorNumero = documentos.getTituloEleitorNumero();
        persistencia.passaporteNumero = documentos.getPassaporteNumero();
        return persistencia;
    }

    private static Endereco paraEndereco(EnderecoPersistencia persistencia) {
        if (persistencia == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setLogradouro(persistencia.logradouro);
        endereco.setNumero(persistencia.numero);
        endereco.setComplemento(persistencia.complemento);
        endereco.setBairro(persistencia.bairro);
        endereco.setCidade(persistencia.cidade);
        endereco.setEstado(persistencia.estado);
        endereco.setCep(persistencia.cep);
        endereco.setPais(persistencia.pais);
        return endereco;
    }

    private static EnderecoPersistencia paraEnderecoPersistencia(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        EnderecoPersistencia persistencia = new EnderecoPersistencia();
        persistencia.logradouro = endereco.getLogradouro();
        persistencia.numero = endereco.getNumero();
        persistencia.complemento = endereco.getComplemento();
        persistencia.bairro = endereco.getBairro();
        persistencia.cidade = endereco.getCidade();
        persistencia.estado = endereco.getEstado();
        persistencia.cep = endereco.getCep();
        persistencia.pais = endereco.getPais();
        return persistencia;
    }

    private static DadosProfissionais paraDadosProfissionais(DadosProfissionaisPersistencia persistencia) {
        if (persistencia == null) {
            return null;
        }
        DadosProfissionais dados = new DadosProfissionais();
        dados.setProfissao(persistencia.profissao);
        dados.setEmpresaAtual(persistencia.empresaAtual);
        dados.setRendaMensal(persistencia.rendaMensal);
        return dados;
    }

    private static DadosProfissionaisPersistencia paraDadosProfissionaisPersistencia(DadosProfissionais dados) {
        if (dados == null) {
            return null;
        }
        DadosProfissionaisPersistencia persistencia = new DadosProfissionaisPersistencia();
        persistencia.profissao = dados.getProfissao();
        persistencia.empresaAtual = dados.getEmpresaAtual();
        persistencia.rendaMensal = dados.getRendaMensal();
        return persistencia;
    }

    private static DadosAdicionais paraDadosAdicionais(DadosAdicionaisPersistencia persistencia) {
        if (persistencia == null) {
            return null;
        }
        DadosAdicionais dados = new DadosAdicionais();
        dados.setTipoSanguineo(persistencia.tipoSanguineo);
        dados.setTimeQueTorce(persistencia.timeQueTorce);
        dados.setFotoPerfilUrl(persistencia.fotoPerfilUrl);
        dados.setBiografia(persistencia.biografia);
        return dados;
    }

    private static DadosAdicionaisPersistencia paraDadosAdicionaisPersistencia(DadosAdicionais dados) {
        if (dados == null) {
            return null;
        }
        DadosAdicionaisPersistencia persistencia = new DadosAdicionaisPersistencia();
        persistencia.tipoSanguineo = dados.getTipoSanguineo();
        persistencia.timeQueTorce = dados.getTimeQueTorce();
        persistencia.fotoPerfilUrl = dados.getFotoPerfilUrl();
        persistencia.biografia = dados.getBiografia();
        return persistencia;
    }

    private static RedesSociais paraRedesSociais(RedesSociaisPersistencia persistencia) {
        if (persistencia == null) {
            return null;
        }
        RedesSociais redes = new RedesSociais();
        redes.setLinkedin(persistencia.linkedin);
        redes.setInstagram(persistencia.instagram);
        redes.setTwitter(persistencia.twitter);
        redes.setGithub(persistencia.github);
        return redes;
    }

    private static RedesSociaisPersistencia paraRedesSociaisPersistencia(RedesSociais redes) {
        if (redes == null) {
            return null;
        }
        RedesSociaisPersistencia persistencia = new RedesSociaisPersistencia();
        persistencia.linkedin = redes.getLinkedin();
        persistencia.instagram = redes.getInstagram();
        persistencia.twitter = redes.getTwitter();
        persistencia.github = redes.getGithub();
        return persistencia;
    }

    static String escoposParaString(List<String> escopos) {
        if (escopos == null || escopos.isEmpty()) {
            return "";
        }
        return String.join(",", escopos);
    }

    static List<String> escoposDeString(String escopos) {
        if (escopos == null || escopos.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(escopos.split(","))
                .map(String::trim)
                .filter(valor -> !valor.isBlank())
                .collect(Collectors.toList());
    }
}
