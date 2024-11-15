package br.com.start.meupet.domain.entities;

public class VerifyAuthenticableEntity {
    private long id;

    private String token;

    public VerifyAuthenticableEntity() {
    }

    public VerifyAuthenticableEntity(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
