package br.com.start.meupet.user.controller;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.user.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "http://localhost:5173")
//@Tag(name = "user", description = "metodos relacionados a rota /user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @Operation(summary = "lista todos os usuarios com paginação", method = "GET")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Listando usuarios com sucesso"),
//            @ApiResponse(responseCode = "401", description = "Nao autorizado"),
//            @ApiResponse(responseCode = "500", description = "Erro ao listar usuarios")
//    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int items) {
        log.info("Requisicao GET: listando todos usuarios");
        return ResponseEntity.ok(userService.listAll(page, items));
    }

//    @Operation(summary = "lista usuario por id", method = "GET")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "usuario retornado com sucesso"),
//            @ApiResponse(responseCode = "401", description = "Nao autorizado"),
//            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado"),
//            @ApiResponse(responseCode = "500", description = "Erro ao listar usuario")
//    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> listOne(@PathVariable UUID id) {
        log.info("Requisicao GET: listando um usuario");
        UserResponseDTO userResponse = userService.getUserById(id);
        return ResponseEntity.ok().body(userResponse);
    }

//    @Operation(summary = "lista usuario por id", method = "POST")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "usuario retornado com sucesso"),
//            @ApiResponse(responseCode = "401", description = "Nao autorizado"),
//            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado"),
//            @ApiResponse(responseCode = "500", description = "Erro ao listar usuario")
//    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> insertNewUser(@RequestBody @Valid UserRequestDTO userRequest) {
        UserResponseDTO userResponse = userService.insert(userRequest);
        log.info("Requisicao POST: inserindo um novo usuario - {}", userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@RequestParam UUID id, @RequestBody @Valid UserRequestDTO newUser) {
        UserResponseDTO updatedUser = userService.update(id, newUser);
        log.info("Requisicao PUT: atualizando um usuario já existente - {}", newUser.toString());
        return ResponseEntity.ok().body(updatedUser);
    }

    // http://localhost:8080/user?id=3
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam UUID id) {
        userService.delete(id);
        log.info("Requisicao DELETE: deletando um usuario");
        return ResponseEntity.noContent().build();
    }

}
