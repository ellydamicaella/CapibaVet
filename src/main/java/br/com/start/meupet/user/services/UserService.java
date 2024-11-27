package br.com.start.meupet.user.services;

import java.util.List;
import java.util.UUID;

import br.com.start.meupet.user.usecase.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final ListUsersUseCase listUsersUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final InitUserRegistrationUseCase initUserRegistrationUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserService(
            ListUsersUseCase listUsersUseCase,
            FindUserByIdUseCase findUserByIdUseCase,
            InitUserRegistrationUseCase initUserRegistrationUseCase,
            UpdateUserUseCase updateUserUseCase,
            DeleteUserUseCase deleteUserUseCase
    ) {
        this.listUsersUseCase = listUsersUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.initUserRegistrationUseCase = initUserRegistrationUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    public List<UserResponseDTO> listAll(int page, int pageSize) {
        return listUsersUseCase.execute(page, pageSize);
    }

    public UserResponseDTO getUserById(UUID id) {
        return findUserByIdUseCase.execute(id);
    }

    public UserResponseDTO insert(UserRequestDTO userRequest) {
      return initUserRegistrationUseCase.execute(userRequest);
    }


    public UserResponseDTO update(UUID id, UserRequestDTO newUser) {
       return updateUserUseCase.execute(id, newUser);
    }

    @Transactional
    public void delete(UUID id) {
       deleteUserUseCase.execute(id);
    }
}
