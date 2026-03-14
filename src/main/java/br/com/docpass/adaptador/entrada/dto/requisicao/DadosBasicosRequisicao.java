package br.com.docpass.adaptador.entrada.dto.requisicao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public class DadosBasicosRequisicao {
    @NotBlank
    public String nomeCompleto;

    @NotBlank
    @CPF
    public String cpf;

    @NotNull
    @PastOrPresent
    public LocalDate dataNascimento;

    @NotBlank
    @Email
    public String email;

    @NotBlank
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$", message = "telefone com DDD invalido")
    public String telefone;
}
