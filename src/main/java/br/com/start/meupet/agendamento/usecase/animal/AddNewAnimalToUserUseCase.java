package br.com.start.meupet.agendamento.usecase.animal;

import br.com.start.meupet.agendamento.dto.animal.AnimalRequestDTO;
import br.com.start.meupet.agendamento.enums.AnimalPorte;
import br.com.start.meupet.agendamento.enums.AnimalSexo;
import br.com.start.meupet.agendamento.enums.AnimalType;
import br.com.start.meupet.agendamento.model.Animal;
import br.com.start.meupet.agendamento.repository.AnimalRepository;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AddNewAnimalToUserUseCase {

    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    public AddNewAnimalToUserUseCase(AnimalRepository animalRepository, UserRepository userRepository) {
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
    }

    public void execute(UUID userId, AnimalRequestDTO animalRequest) {
        Optional<User> user = userRepository.findById(userId);
        Animal animal = new Animal();

        if(user.isEmpty()) {
            throw new ProblemDetailsException("Usuario nao encontrado", "O usuario nao existe", HttpStatus.NOT_FOUND);
        }

        try {
            animal.setName(animalRequest.name());
            animal.setPorte(AnimalPorte.valueOf(animalRequest.porte()));
            animal.setType(AnimalType.valueOf(animalRequest.type()));
            animal.setSexo(AnimalSexo.valueOf(animalRequest.sexo()));
            animal.setOwner(user.get());
        } catch (IllegalArgumentException e) {
            throw new ProblemDetailsException("Argumento inválido", "Algum argumento está incorreto", HttpStatus.BAD_REQUEST);
        }

        animalRepository.save(animal);
    }
}
