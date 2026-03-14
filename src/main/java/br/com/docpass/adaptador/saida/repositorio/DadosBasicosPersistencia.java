package br.com.docpass.adaptador.saida.repositorio;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class DadosBasicosPersistencia {
    @Column(name = "nome_completo", nullable = false)
    public String nomeCompleto;

    @Column(name = "cpf", nullable = false, unique = true)
    public String cpf;

    @Column(name = "data_nascimento", nullable = false)
    public LocalDate dataNascimento;

    @Column(name = "email", nullable = false, unique = true)
    public String email;

    @Column(name = "telefone", nullable = false)
    public String telefone;
}
