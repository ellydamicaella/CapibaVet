package br.com.start.meupet.agendamento.usecase.animal;

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
public class DeleteAnimalUseCase {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;

    public DeleteAnimalUseCase(UserRepository userRepository, AnimalRepository animalRepository) {
        this.userRepository = userRepository;
        this.animalRepository = animalRepository;
    }

    public void execute(UUID userId, Long animalId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ProblemDetailsException("Usuario nao encontrado", "Usuario nao existe", HttpStatus.NOT_FOUND);
        }
        User user = userOpt.get();
        Animal animal = new Animal();
        animal.setId(animalId);
        animalRepository.delete(animal);
    }
}
