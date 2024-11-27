package br.com.start.meupet.auth.controllers;

import br.com.start.meupet.auth.interfaces.AuthenticableResponseDTO;
import br.com.start.meupet.auth.services.AuthenticableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/authenticable")
@CrossOrigin
public class AuthenticableController {

    private final AuthenticableService authenticableService;

    public AuthenticableController(AuthenticableService authenticableService) {
        this.authenticableService = authenticableService;
    }

    @GetMapping(value = "/confirmAccount/{token}")
    public String generateTemplateToConfirmAccount(@PathVariable String token){
        return authenticableService.generateTemplateToConfirmAccount(token);
    }

    @PostMapping(value = "createAccount/{token}")
    public ResponseEntity<AuthenticableResponseDTO> createNewAccountByToken(@PathVariable String token) {
        return ResponseEntity.ok().body(authenticableService.processUserRegistration(token));
    }

}
