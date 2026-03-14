package br.com.docpass.seguranca;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class LimitadorRequisicoesEmpresa {
    private static final int LIMITE_POR_MINUTO = 100;
    private final ConcurrentHashMap<UUID, Janela> registros = new ConcurrentHashMap<>();

    public boolean permitir(UUID empresaId) {
        long minutoAtual = Instant.now().getEpochSecond() / 60;
        Janela janela = registros.computeIfAbsent(empresaId, id -> new Janela(minutoAtual, 0));
        synchronized (janela) {
            if (janela.minuto != minutoAtual) {
                janela.minuto = minutoAtual;
                janela.contador = 0;
            }
            if (janela.contador >= LIMITE_POR_MINUTO) {
                return false;
            }
            janela.contador++;
            return true;
        }
    }

    private static class Janela {
        private long minuto;
        private int contador;

        private Janela(long minuto, int contador) {
            this.minuto = minuto;
            this.contador = contador;
        }
    }
}
