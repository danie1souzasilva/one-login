package br.com.docpass.adaptador.saida.repositorio;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UsuarioEntidade {
    @Id
    public UUID id;

    @Embedded
    public DadosBasicosPersistencia dadosBasicos;

    @Embedded
    public DadosComplementaresPersistencia dadosComplementares;

    @Embedded
    public DocumentosPersistencia documentos;

    @Embedded
    public EnderecoPersistencia endereco;

    @Embedded
    public DadosProfissionaisPersistencia dadosProfissionais;

    @Embedded
    public DadosAdicionaisPersistencia dadosAdicionais;

    @Embedded
    public RedesSociaisPersistencia redesSociais;

    @Column(name = "created_at", nullable = false)
    public Instant criadoEm;

    @PrePersist
    void prePersist() {
        if (criadoEm == null) {
            criadoEm = Instant.now();
        }
    }
}
