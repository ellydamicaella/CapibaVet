package br.com.start.meupet.common.controllers;

import java.io.IOException;

import br.com.start.meupet.common.interfaces.AuthenticableResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.start.meupet.common.dto.AuthenticationDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.common.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/confirm/{token}")
    public String confirmAccount(@PathVariable String token) throws IOException {
        return authService.confirmAccount(token);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO authDto) {
        return ResponseEntity.ok(authService.login(authDto));
    }

    @PostMapping(value = "verifyNewUser/{token}")
    public ResponseEntity<AuthenticableResponseDTO> verifyNewUser(@PathVariable String token) {
        return ResponseEntity.ok().body(authService.verifyNewUser(token));
    }
}
