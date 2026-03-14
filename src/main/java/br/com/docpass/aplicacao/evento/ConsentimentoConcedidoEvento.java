package br.com.docpass.aplicacao.evento;

import java.time.Instant;
import java.util.UUID;

public record ConsentimentoConcedidoEvento(UUID consentimentoId, UUID usuarioId, UUID empresaId, Instant ocorridoEm) {
}
