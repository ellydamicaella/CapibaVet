package br.com.start.meupet.auth.controller;

import br.com.start.meupet.auth.dto.PasswordRecoveryDTO;
import br.com.start.meupet.auth.dto.PasswordResetDTO;
import br.com.start.meupet.auth.dto.StatusResponseDTO;
import br.com.start.meupet.auth.facade.PasswordRecoveryFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password-recovery")
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class PasswordRecoveryController {

    private final PasswordRecoveryFacade passwordRecoveryFacade;

    public PasswordRecoveryController(PasswordRecoveryFacade passwordRecoveryFacade) {
        this.passwordRecoveryFacade = passwordRecoveryFacade;
    }

    @PostMapping("/request")
    public ResponseEntity<StatusResponseDTO> requestPasswordRecovery(@RequestBody PasswordRecoveryDTO passwordRecovery) {
        passwordRecoveryFacade.generateRecoveryToken(passwordRecovery.email());
        return ResponseEntity.ok().body(new StatusResponseDTO("success", "Email enviado com sucesso!"));
    }

    // http://localhost:8080/password-recovery/reset?token=12312515613251235151
    @PatchMapping("/reset")
    public ResponseEntity<StatusResponseDTO> resetPassword(@RequestParam String token, @RequestBody PasswordResetDTO passwordReset) {
        log.info("senha: {}", passwordReset.password());
        passwordRecoveryFacade.resetPassword(token, passwordReset.password());
        return ResponseEntity.ok().body(new StatusResponseDTO("success", "Senha alterada com sucesso!"));
    }

}
