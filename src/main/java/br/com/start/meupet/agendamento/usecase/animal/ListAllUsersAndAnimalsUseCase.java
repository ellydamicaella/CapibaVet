package br.com.start.meupet.agendamento.usecase.animal;

import br.com.start.meupet.agendamento.dto.animal.UserAnimalDTO;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListAllUsersAndAnimalsUseCase {

    private final UserRepository userRepository;

    public ListAllUsersAndAnimalsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserAnimalDTO> execute() {
        List<User> users = userRepository.findAll();
        List<UserAnimalDTO> userAnimalDTO = new ArrayList<>();
        users.forEach(animal -> {
            userAnimalDTO.add(new UserAnimalDTO(animal));
        });
        return userAnimalDTO;
    }
}
