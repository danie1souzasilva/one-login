package br.com.docpass.adaptador.entrada.dto.resposta;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ConsentimentoResposta {
    public UUID id;
    public UUID usuarioId;
    public UUID empresaId;
    public List<String> escopos;
    public Instant criadoEm;
    public Instant expiraEm;
}
