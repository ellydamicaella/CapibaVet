package br.com.start.meupet.auth.controllers;

import br.com.start.meupet.auth.dto.PasswordRecoveryDTO;
import br.com.start.meupet.auth.dto.PasswordResetDTO;
import br.com.start.meupet.auth.dto.StatusResponseDTO;
import br.com.start.meupet.auth.service.PasswordRecoveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password-recovery")
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping("/request")
    public ResponseEntity<StatusResponseDTO> requestPasswordRecovery(@RequestBody PasswordRecoveryDTO passwordRecovery) {
        passwordRecoveryService.generateRecoveryToken(passwordRecovery.email());
        return ResponseEntity.ok().body(new StatusResponseDTO("success", "Email enviado com sucesso!"));
    }

    // http://localhost:8080/password-recovery/reset?token=12312515613251235151
    @PatchMapping("/reset")
    public ResponseEntity<StatusResponseDTO> resetPassword(@RequestParam String token, @RequestBody PasswordResetDTO passwordReset) {
        log.info("senha: {}", passwordReset.password());
        passwordRecoveryService.resetPassword(token, passwordReset.password());
        return ResponseEntity.ok().body(new StatusResponseDTO("success", "Senha alterada com sucesso!"));
    }

}
