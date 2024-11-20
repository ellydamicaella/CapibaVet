package br.com.start.meupet.common.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyAuthenticable {
    private long id;

    private String token;

    public VerifyAuthenticable() {
    }

    public VerifyAuthenticable(String token) {
        this.token = token;
    }
}
