package br.com.start.meupet.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyAuthenticableEntity {
    private long id;

    private String token;

    public VerifyAuthenticableEntity() {
    }

    public VerifyAuthenticableEntity(String token) {
        this.token = token;
    }
}
