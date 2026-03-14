package br.com.docpass.adaptador.entrada.dto.requisicao;

import jakarta.validation.constraints.Pattern;

public class EnderecoRequisicao {
    public String logradouro;
    public String numero;
    public String complemento;
    public String bairro;
    public String cidade;

    @Pattern(regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$",
            message = "estado deve ser UF valida")
    public String estado;

    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "cep invalido")
    public String cep;

    @Pattern(regexp = "^[A-Z]{2}$", message = "pais deve seguir ISO 3166-1 alpha-2")
    public String pais;
}
