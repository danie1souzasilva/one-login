package br.com.docpass.adaptador.saida.repositorio;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RedesSociaisPersistencia {
    @Column(name = "linkedin")
    public String linkedin;

    @Column(name = "instagram")
    public String instagram;

    @Column(name = "twitter")
    public String twitter;

    @Column(name = "github")
    public String github;
}
