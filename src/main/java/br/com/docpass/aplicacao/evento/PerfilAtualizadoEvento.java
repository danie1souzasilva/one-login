package br.com.docpass.aplicacao.evento;

import java.time.Instant;
import java.util.UUID;

public record PerfilAtualizadoEvento(UUID usuarioId, Instant ocorridoEm) {
}
