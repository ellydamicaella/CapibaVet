package br.com.start.meupet.auth.dto;

import jakarta.validation.Valid;

public record AuthenticationDTO(@Valid String email, @Valid String password) {

}

