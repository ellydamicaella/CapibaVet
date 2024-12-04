package br.com.start.meupet.user.controller;

import br.com.start.meupet.auth.dto.StatusResponseDTO;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.user.dto.UserUpdateDTO;
import br.com.start.meupet.user.facade.UserFacade;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "user", description = "metodos relacionados a rota /user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserFacade userFacade;
    private final UserRepository userRepository;

    public UserController(UserFacade userFacade, UserRepository userRepository) {
        this.userFacade = userFacade;
        this.userRepository = userRepository;
    }

    @Operation(summary = "lista todos os usuarios com paginação", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando usuarios com sucesso", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "401", description = "Nao autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro ao listar usuarios")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int items) {
        log.info("Requisicao GET: listando todos usuarios");
        return ResponseEntity.ok(userFacade.listAll(page, items));
    }

    @Operation(summary = "lista usuario por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "usuario retornado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Nao autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao listar usuario")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> listOne(@PathVariable UUID id) {
        log.info("Requisicao GET: listando um usuario");
        UserResponseDTO userResponse = userFacade.getUserById(id);
        return ResponseEntity.ok().body(userResponse);
    }

    @Operation(summary = "Insere um novo usuário enviando um email para confirmação da conta", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "enviado email para usuario com sucesso"),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
            @ApiResponse(responseCode = "401", description = "Nao autorizado")
    })
    @PostMapping
    public ResponseEntity<StatusResponseDTO> insertNewUser(@RequestBody @Valid UserRequestDTO userRequest) {
        userFacade.insert(userRequest);
        log.info("Requisicao POST: inserindo um novo usuario - {}", userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponseDTO("success", "Email enviado ao usuario com sucesso, aguardando confirmação de conta"));
    }

    @PostMapping("/upload-image/{id}")
    public ResponseEntity<StatusResponseDTO> uploadUserImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        try {
            userFacade.saveUserImage(id, file);
            return ResponseEntity.ok(new StatusResponseDTO("success", "Imagem enviada com sucesso"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StatusResponseDTO("error", "Erro ao enviar imagem"));
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable UUID userId, @RequestBody UserUpdateDTO userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado com ID: " + userId));


        // Atualiza apenas os campos não nulos do DTO
        if (userRequest.getName() != null) {
            user.setName(userRequest.getName());
        }
        if (userRequest.getSocialName() != null) {
            user.setSocialName(userRequest.getSocialName());
        }
        if (userRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(new PhoneNumber(userRequest.getPhoneNumber()));
        }
        if (userRequest.getDateOfBirth() != null) {
            user.setDateOfBirth(BirthDayUtils.convertToDate(userRequest.getDateOfBirth()));
        }
        userRepository.save(user); // Persistindo as alterações no banco
        log.info("Requisicao PATCH: updateUser - {}", user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getUserImage(@RequestParam UUID id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent() && user.get().getProfileImage() != null) {
            byte[] image = user.get().getProfileImage();  // Recuperando a imagem do banco
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)  // Defina o tipo de mídia adequado
                    .body(image);
        } else {
            return ResponseEntity.notFound().build();  // Se não encontrar, retorna 404
        }
    }

    @Operation(summary = "Atualiza um usuário já existente", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "usuario atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
            @ApiResponse(responseCode = "401", description = "Nao autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    })
    @PutMapping
    public ResponseEntity<?> update(@RequestParam UUID id, @RequestBody @Valid UserRequestDTO newUser) {
        userFacade.update(id, newUser);
        log.info("Requisicao PUT: atualizando um usuario já existente - {}", newUser.toString());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // http://localhost:8080/user?id=3
    @Operation(summary = "Deleta um usuário", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "usuario deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
            @ApiResponse(responseCode = "401", description = "Nao autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam UUID id) {
        userFacade.delete(id);
        log.info("Requisicao DELETE: deletando um usuario");
        return ResponseEntity.noContent().build();
    }

}
