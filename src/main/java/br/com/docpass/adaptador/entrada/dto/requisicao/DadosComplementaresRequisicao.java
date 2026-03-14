package br.com.docpass.adaptador.entrada.dto.requisicao;

import br.com.docpass.dominio.modelo.EstadoCivil;
import br.com.docpass.dominio.modelo.Genero;
import br.com.docpass.dominio.modelo.Sexo;

public class DadosComplementaresRequisicao {
    public String nomeSocial;
    public Genero genero;
    public Sexo sexo;
    public EstadoCivil estadoCivil;
    public String nacionalidade;
    public String naturalidade;
    public String nomeMae;
    public String nomePai;
}
