package br.com.start.meupet.passwordRecovery.controller;

import br.com.start.meupet.passwordRecovery.dto.PasswordRecoveryRequestDTO;
import br.com.start.meupet.passwordRecovery.service.PasswordRecoveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password-recovery")
@CrossOrigin
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestPasswordRecovery(@RequestBody PasswordRecoveryRequestDTO requestDTO) {
        passwordRecoveryService.generateRecoveryToken(requestDTO.email());
        return ResponseEntity.ok("Email enviado com sucesso!");
    }

    // http://localhost:8080/password-recovery/reset/12312
    @PostMapping("/reset/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody String newPassword) {
        passwordRecoveryService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}
