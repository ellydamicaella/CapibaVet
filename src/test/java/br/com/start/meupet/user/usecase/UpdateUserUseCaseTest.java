package br.com.start.meupet.user.usecase;

import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.service.ServiceUtils;
import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ServiceUtils serviceUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @Test
    @DisplayName("Deve atualizar o usuário com sucesso")
    void shouldUpdateUserSuccessfully() {
        UUID userId = UUID.randomUUID();
        User existingUser =  getExistingUserAllIsOk(userId);
        UserRequestDTO newUser = getUserRequestDTOAllIsOk();
        User updatedUser = getUpdatedUserAllIsOk(userId, newUser);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserResponseDTO result = updateUserUseCase.execute(userId, newUser);

        Assertions.assertEquals("New Name", result.getName());
        verify(userRepository, times(1)).findById(userId);
        verify(serviceUtils, times(1)).isUserAlreadyExists(existingUser);
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    @DisplayName("Deve lançar exceção se o usuário não for encontrado")
    void shouldThrowExceptionIfUserNotFound() {
        UUID userId = UUID.randomUUID();
        UserRequestDTO newUser = new UserRequestDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> updateUserUseCase.execute(userId, newUser)
        );

        Assertions.assertEquals("Not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verifyNoInteractions(serviceUtils);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("Deve validar e criptografar a senha corretamente")
    void shouldValidateAndEncodePassword() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User existingUser = getExistingUserAllIsOk(userId);

        UserRequestDTO newUser = getUserRequestDTOAllIsOk();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        updateUserUseCase.execute(userId, newUser);

        verify(passwordEncoder, times(1)).encode("newPassword");
    }

    User getExistingUserAllIsOk(UUID userId) {
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Old Name");
        existingUser.setPassword("oldPassword");
        existingUser.setSocialName("User");
        existingUser.setEmail(new Email("aaaaa@gmail.com"));
        existingUser.setDateOfBirth(BirthDayUtils.convertToDate("2000-12-01"));
        existingUser.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        existingUser.setPersonalRegistration(new PersonalRegistration("11111111111", DocumentType.CPF));
        return existingUser;
    }

    UserRequestDTO getUserRequestDTOAllIsOk() {
        UserRequestDTO newUser = new UserRequestDTO();
        newUser.setName("New Name");
        newUser.setPassword("newPassword");
        newUser.setSocialName("User");
        newUser.setEmail("aaaaa@gmail.com");
        newUser.setBirthDate("2000-12-01");
        newUser.setPhoneNumber("(21) 22222-2222");
        newUser.setDocument("11111111111");
        newUser.setDocumentType("CPF");
        return newUser;
    }

    User getUpdatedUserAllIsOk(UUID userId, UserRequestDTO newUser) {
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName(newUser.getName());
        updatedUser.setPassword("encodedPassword");
        updatedUser.setEmail(new Email(newUser.getEmail()));
        updatedUser.setDateOfBirth(BirthDayUtils.convertToDate(newUser.getBirthDate()));
        updatedUser.setPhoneNumber(new PhoneNumber(newUser.getPhoneNumber()));
        updatedUser.setPersonalRegistration(new PersonalRegistration(newUser.getDocument(), DocumentType.valueOf(newUser.getDocumentType())));
        return updatedUser;
    }
}
