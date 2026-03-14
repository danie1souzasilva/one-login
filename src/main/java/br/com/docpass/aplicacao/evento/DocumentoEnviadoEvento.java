package br.com.docpass.aplicacao.evento;

import java.time.Instant;
import java.util.UUID;

public record DocumentoEnviadoEvento(UUID documentoId, UUID usuarioId, Instant ocorridoEm) {
}
