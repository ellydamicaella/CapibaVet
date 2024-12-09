package br.com.start.meupet.agendamento.controller;

import br.com.start.meupet.agendamento.dto.animal.AnimalRequestDTO;
import br.com.start.meupet.agendamento.dto.animal.UserAnimalDTO;
import br.com.start.meupet.agendamento.facade.AnimalFacade;
import br.com.start.meupet.auth.dto.StatusResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendamento/user/animal")
@CrossOrigin
public class AnimalController {

    private final AnimalFacade animalFacade;

    public AnimalController(AnimalFacade animalFacade) {
        this.animalFacade = animalFacade;
    }

    @GetMapping
    public ResponseEntity<List<UserAnimalDTO>> listaUsuariosESeusAnimais() {
        return ResponseEntity.ok().body(animalFacade.listAllUsersAndAnimals());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserAnimalDTO> listaUsuarioESeuAnimal(@PathVariable UUID userId) {
        return ResponseEntity.ok().body(animalFacade.getUserAndAnimals(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<StatusResponseDTO> adicionaAnimalAoParceiro(@PathVariable UUID userId, @RequestBody AnimalRequestDTO animalRequest) {
        animalFacade.addAnimalToUser(userId, animalRequest);
        return ResponseEntity.ok().body(new StatusResponseDTO("success", "Servico adicionado ao parceiro com sucesso!"));
    }

    @DeleteMapping("/{userId}/{animalId}")
    public ResponseEntity<StatusResponseDTO> deleteAnimal(@PathVariable UUID userId, @PathVariable Long animalId) {
        animalFacade.deleteAnimal(userId, animalId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
