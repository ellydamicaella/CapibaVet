package br.com.start.meupet.agendamento.usecase.animal;

import br.com.start.meupet.agendamento.dto.animal.UserAnimalDTO;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
public class GetUserAndAnimalsUseCase {

    private final UserRepository userRepository;

    public GetUserAndAnimalsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserAnimalDTO execute(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new ProblemDetailsException("Usuario nao encontrado", "Usuario nao existe", HttpStatus.NOT_FOUND);
        }
        return new UserAnimalDTO(user.get());
    }
}
