package br.com.start.meupet.auth.interfaces;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class AuthenticableResponseDTO {
    private UUID id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;
}
