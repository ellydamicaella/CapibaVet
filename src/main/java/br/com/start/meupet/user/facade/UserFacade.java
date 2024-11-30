package br.com.start.meupet.user.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import br.com.start.meupet.user.usecase.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UserFacade {

    private static final Logger log = LoggerFactory.getLogger(UserFacade.class);
    private final ListUsersUseCase listUsersUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final InitUserRegistrationUseCase initUserRegistrationUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UploadImageUserUseCase uploadImageUserUseCase;

    public UserFacade(
            ListUsersUseCase listUsersUseCase,
            FindUserByIdUseCase findUserByIdUseCase,
            InitUserRegistrationUseCase initUserRegistrationUseCase,
            UpdateUserUseCase updateUserUseCase,
            DeleteUserUseCase deleteUserUseCase,
            UploadImageUserUseCase uploadImageUserUseCase) {
        this.listUsersUseCase = listUsersUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.initUserRegistrationUseCase = initUserRegistrationUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.uploadImageUserUseCase = uploadImageUserUseCase;
    }

    public List<UserResponseDTO> listAll(int page, int pageSize) {
        return listUsersUseCase.execute(page, pageSize);
    }

    public UserResponseDTO getUserById(UUID id) {
        return findUserByIdUseCase.execute(id);
    }

    public void insert(UserRequestDTO userRequest) {
        initUserRegistrationUseCase.execute(userRequest);
    }

    public UserResponseDTO update(UUID id, UserRequestDTO newUser) {
       return updateUserUseCase.execute(id, newUser);
    }

    public void delete(UUID id) {
       deleteUserUseCase.execute(id);
    }

    public void saveUserImage(UUID id, MultipartFile file) throws IOException {
        uploadImageUserUseCase.execute(id, file);
    }
}
