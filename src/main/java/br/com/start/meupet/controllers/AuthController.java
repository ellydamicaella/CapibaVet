package br.com.start.meupet.controllers;

import br.com.start.meupet.dto.AuthenticationDTO;
import br.com.start.meupet.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDto) {

        return ResponseEntity.ok(authService.login(authDto));
    }
}
