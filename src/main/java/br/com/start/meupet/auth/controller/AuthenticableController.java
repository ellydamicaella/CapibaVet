package br.com.start.meupet.auth.controller;

import br.com.start.meupet.auth.dto.AuthenticableDTO;
import br.com.start.meupet.auth.dto.StatusResponseDTO;
import br.com.start.meupet.auth.service.AuthenticableService;
import br.com.start.meupet.partner.dto.PartnerDTO;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<AuthenticableDTO> getUserByEmail(@RequestParam String email) {
        AuthenticableDTO response = authenticableService.findUserByEmail(email);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Caso o usuário não seja encontrado
        }

        if(response instanceof PartnerDTO) {
            response.setMoedaCapiba(null);
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/confirmAccount/{token}")
    public String generateTemplateToConfirmAccount(@PathVariable String token){
        return authenticableService.generateTemplateToConfirmAccount(token);
    }

    @PostMapping(value = "/createAccount/{token}")
    public ResponseEntity<StatusResponseDTO> createNewAccountByToken(@PathVariable String token) {
        authenticableService.processUserRegistration(token);
        return ResponseEntity.ok().body(new StatusResponseDTO("success", "entidade criada com sucesso"));
    }

}
