package br.com.docpass.adaptador.entrada.dto.requisicao;

import org.hibernate.validator.constraints.URL;

public class RedesSociaisRequisicao {
    @URL(message = "linkedin deve ser URL valida")
    public String linkedin;

    @URL(message = "instagram deve ser URL valida")
    public String instagram;

    @URL(message = "twitter deve ser URL valida")
    public String twitter;

    @URL(message = "github deve ser URL valida")
    public String github;
}
