package br.com.start.meupet.controllers;

import br.com.start.meupet.dto.UserRequestDTO;
import br.com.start.meupet.dto.UserResponseDTO;
import br.com.start.meupet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll() {
        log.info("Requisicao GET: listando todos usuarios");
        return ResponseEntity.ok(userService.listAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> listOne(@PathVariable Long id) {
        log.info("Requisicao GET: listando um usuarios");
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> insert(@RequestBody UserRequestDTO usuario) {
        UserResponseDTO user = userService.insert(usuario);
        log.info("Requisicao POST: inserindo um novo usuario - {}", usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@RequestParam Long id, @RequestBody UserRequestDTO newUser) {
        UserResponseDTO updatedUser = userService.update(id, newUser);
        log.info("Requisicao PUT: atualizando um usuario já existente - {}", newUser.toString());
        return ResponseEntity.ok().body(updatedUser);
    }

    // http://localhost:8080/user?id=3
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        userService.delete(id);
        log.info("Requisicao DELETE: deletando um usuario");
        return ResponseEntity.noContent().build();
    }

}
