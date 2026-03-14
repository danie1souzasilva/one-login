package br.com.docpass.adaptador.saida.repositorio;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EnderecoPersistencia {
    @Column(name = "logradouro")
    public String logradouro;

    @Column(name = "numero")
    public String numero;

    @Column(name = "complemento")
    public String complemento;

    @Column(name = "bairro")
    public String bairro;

    @Column(name = "cidade")
    public String cidade;

    @Column(name = "estado")
    public String estado;

    @Column(name = "cep")
    public String cep;

    @Column(name = "pais")
    public String pais;
}
