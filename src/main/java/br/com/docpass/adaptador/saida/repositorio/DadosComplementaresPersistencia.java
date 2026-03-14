package br.com.docpass.adaptador.saida.repositorio;

import br.com.docpass.dominio.modelo.EstadoCivil;
import br.com.docpass.dominio.modelo.Genero;
import br.com.docpass.dominio.modelo.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class DadosComplementaresPersistencia {
    @Column(name = "nome_social")
    public String nomeSocial;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    public Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    public Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    public EstadoCivil estadoCivil;

    @Column(name = "nacionalidade")
    public String nacionalidade;

    @Column(name = "naturalidade")
    public String naturalidade;

    @Column(name = "nome_mae")
    public String nomeMae;

    @Column(name = "nome_pai")
    public String nomePai;
}
