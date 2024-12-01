package br.com.start.meupet.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class AuthenticableDTO {
    private UUID id;
    private String name;
    private String socialName;
    private String email;
    private String phoneNumber;
    private String document;
    private String documentType;
    private String street;
    private String neighborhood;
    private String city;
    private String CEP;
    private String dateOfBirth;
    private Integer moedaCapiba;
}
