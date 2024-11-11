package br.com.start.meupet.controllers;

import br.com.start.meupet.dto.AuthenticationDTO;
import br.com.start.meupet.service.AuthService;
import br.com.start.meupet.service.EmailService;
import br.com.start.meupet.service.UserService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final EmailService emailService;

    public AuthController(AuthService authService, UserService userService, EmailService emailService) {
        this.authService = authService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping(value = "/confirm/{uuid}")
    public String confirmAccount(@PathVariable("uuid") String uuid) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("templates/confirmacaoConta.html");
        String template = new String(classPathResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        template = template.replace("{{uuid}}", uuid);
        System.out.println(template);
        return template;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDto) {
        return ResponseEntity.ok(authService.login(authDto));
    }

    @PostMapping(value = "verifyNewUser/{uuid}")
    public ResponseEntity<String> verifyNewUser(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok().body(userService.verifyNewUser(uuid));
    }

}
