package br.com.docpass.adaptador.entrada.dto.requisicao;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class CriarConsentimentoRequisicao {
    @NotNull
    public UUID usuarioId;

    @NotNull
    public UUID empresaId;

    @NotEmpty
    public List<String> escopos;

    public Instant expiraEm;
}
