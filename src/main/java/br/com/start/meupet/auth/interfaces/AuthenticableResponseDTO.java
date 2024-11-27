package br.com.start.meupet.auth.interfaces;

import java.util.UUID;

public abstract class AuthenticableResponseDTO {
    private UUID id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;
}
