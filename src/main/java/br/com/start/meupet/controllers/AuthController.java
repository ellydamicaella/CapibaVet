package br.com.start.meupet.controllers;

import br.com.start.meupet.dto.AuthenticationDTO;
import br.com.start.meupet.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/confirm/{uuid}")
    public String confirmAccount(@PathVariable("uuid") String uuid) throws IOException {
        return authService.confirmAccount(uuid);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO authDto) {
        return ResponseEntity.ok(authService.login(authDto));
    }

    @PostMapping(value = "verifyNewUser/{uuid}")
    public ResponseEntity<String> verifyNewUser(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok().body(authService.verifyNewUser(uuid));
    }

}
