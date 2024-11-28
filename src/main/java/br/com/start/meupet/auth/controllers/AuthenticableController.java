package br.com.start.meupet.auth.controllers;

import br.com.start.meupet.auth.dto.AuthenticableGetByEmailDTO;
import br.com.start.meupet.auth.dto.AuthenticableLoginHeaderInfosDTO;
import br.com.start.meupet.auth.dto.StatusResponseDTO;
import br.com.start.meupet.auth.interfaces.AuthenticableResponseDTO;
import br.com.start.meupet.auth.services.AuthenticableService;
import br.com.start.meupet.partner.dto.PartnerResponseDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/authenticable")
@CrossOrigin
public class AuthenticableController {

    private final AuthenticableService authenticableService;

    public AuthenticableController(AuthenticableService authenticableService) {
        this.authenticableService = authenticableService;
    }

    @GetMapping
    public ResponseEntity<AuthenticableLoginHeaderInfosDTO> getUserByEmail(@RequestParam String email) {
        AuthenticableResponseDTO response = authenticableService.findUserByEmail(email);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Caso o usuário não seja encontrado
        }

        // Com base no tipo do response, podemos criar a DTO correta
        AuthenticableLoginHeaderInfosDTO dto = new AuthenticableLoginHeaderInfosDTO(
                response.getName(),
                response instanceof UserResponseDTO ? ((UserResponseDTO) response).getMoedaCapiba() : null
        );

        return ResponseEntity.ok(dto);
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
