package br.com.start.meupet.common.controllers;

import br.com.start.meupet.common.dto.PasswordRecoveryDTO;
import br.com.start.meupet.common.dto.PasswordResetDTO;
import br.com.start.meupet.common.service.PasswordRecoveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password-recovery")
@CrossOrigin
@Slf4j
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @GetMapping("/page")
    public String getPage(@RequestParam String token) {
        return passwordRecoveryService.loadPage(token);
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestPasswordRecovery(@RequestBody PasswordRecoveryDTO passwordRecovery) {
        passwordRecoveryService.generateRecoveryToken(passwordRecovery.email());
        return ResponseEntity.ok("Email enviado com sucesso!");
    }

    // http://localhost:8080/password-recovery/reset?token=12312515613251235151
    @PatchMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody PasswordResetDTO passwordReset) {
        log.info("senha: {}", passwordReset.password());
        System.out.println(passwordReset.password());
        passwordRecoveryService.resetPassword(token, passwordReset.password());
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }


}
