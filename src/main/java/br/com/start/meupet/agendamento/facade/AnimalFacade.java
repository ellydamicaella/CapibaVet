package br.com.start.meupet.agendamento.facade;

import br.com.start.meupet.agendamento.dto.animal.AnimalRequestDTO;
import br.com.start.meupet.agendamento.dto.animal.UserAnimalDTO;
import br.com.start.meupet.agendamento.usecase.animal.AddNewAnimalToUserUseCase;
import br.com.start.meupet.agendamento.usecase.animal.DeleteAnimalUseCase;
import br.com.start.meupet.agendamento.usecase.animal.ListAllUsersAndAnimalsUseCase;
import br.com.start.meupet.agendamento.usecase.animal.GetUserAndAnimalsUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AnimalFacade {

    private final ListAllUsersAndAnimalsUseCase listAllUsersAndAnimalsUseCase;
    private final GetUserAndAnimalsUseCase getUserAndAnimalsUseCase;
    private final AddNewAnimalToUserUseCase addNewAnimalToUserUseCase;
    private final DeleteAnimalUseCase deleteAnimalUseCase;

    public AnimalFacade(ListAllUsersAndAnimalsUseCase listAllUsersAndAnimalsUseCase, GetUserAndAnimalsUseCase getUserAndAnimalsUseCase, AddNewAnimalToUserUseCase addNewAnimalToUserUseCase, DeleteAnimalUseCase deleteAnimalUseCase) {
        this.listAllUsersAndAnimalsUseCase = listAllUsersAndAnimalsUseCase;
        this.getUserAndAnimalsUseCase = getUserAndAnimalsUseCase;
        this.addNewAnimalToUserUseCase = addNewAnimalToUserUseCase;
        this.deleteAnimalUseCase = deleteAnimalUseCase;
    }

    public List<UserAnimalDTO> listAllUsersAndAnimals() {
       return listAllUsersAndAnimalsUseCase.execute();
    }

    public UserAnimalDTO getUserAndAnimals(UUID userId) {
        return getUserAndAnimalsUseCase.execute(userId);
    }

    public void addAnimalToUser(UUID userId, AnimalRequestDTO animalRequest) {
        addNewAnimalToUserUseCase.execute(userId, animalRequest);
    }

    public void deleteAnimal(UUID userId, Long animalId) {
        deleteAnimalUseCase.execute(userId, animalId);
    }
}
