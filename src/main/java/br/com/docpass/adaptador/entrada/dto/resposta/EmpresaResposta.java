package br.com.docpass.adaptador.entrada.dto.resposta;

import java.time.Instant;
import java.util.UUID;

public class EmpresaResposta {
    public UUID id;
    public String nome;
    public String chaveApi;
    public Instant criadoEm;
}
