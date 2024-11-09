package br.com.start.meupet.controllers;

import br.com.start.meupet.dto.AuthenticationDTO;
import br.com.start.meupet.service.AuthService;
import br.com.start.meupet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDto) {
        return ResponseEntity.ok(authService.login(authDto));
    }

    @GetMapping(value = "verifyNewUser/{uuid}")
    public String verifyNewUser(@PathVariable("uuid") String uuid) {
        return userService.verifyNewUser(uuid);
    }
}
