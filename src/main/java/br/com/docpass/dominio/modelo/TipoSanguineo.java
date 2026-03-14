package br.com.docpass.dominio.modelo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum TipoSanguineo {
    A_POSITIVO("A+"),
    A_NEGATIVO("A-"),
    B_POSITIVO("B+"),
    B_NEGATIVO("B-"),
    AB_POSITIVO("AB+"),
    AB_NEGATIVO("AB-"),
    O_POSITIVO("O+"),
    O_NEGATIVO("O-");

    private final String codigo;

    TipoSanguineo(String codigo) {
        this.codigo = codigo;
    }

    @JsonValue
    public String getCodigo() {
        return codigo;
    }

    @JsonCreator
    public static TipoSanguineo deCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        String normalizado = codigo.trim().toUpperCase();
        return Arrays.stream(values())
                .filter(valor -> valor.codigo.equals(normalizado))
                .findFirst()
                .orElse(null);
    }
}
