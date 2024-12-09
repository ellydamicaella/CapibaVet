package br.com.start.meupet.agendamento.usecase.animal;

import br.com.start.meupet.agendamento.dto.animal.UserAnimalDTO;
import br.com.start.meupet.agendamento.model.Animal;
import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetUserAndAnimalsUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserAndAnimalsUseCase getUserAndAnimalsUseCase;

    @Test
    void shouldReturnUserAndAnimalsSuccessfully() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setName("John Doe");
        mockUser.setSocialName("User");
        mockUser.setEmail(new Email("aaaaa@gmail.com"));
        mockUser.setPassword("321312312");
        mockUser.setDateOfBirth(BirthDayUtils.convertToDate("2000-12-01"));
        mockUser.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        mockUser.setPersonalRegistration(new PersonalRegistration("111.111.111-11", DocumentType.CPF));


        Animal animal1 = new Animal();
        animal1.setId(1L);
        animal1.setName("Rex");

        Animal animal2 = new Animal();
        animal2.setId(2L);
        animal2.setName("Fluffy");

        mockUser.setAnimals(List.of(animal1, animal2));

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        UserAnimalDTO result = getUserAndAnimalsUseCase.execute(userId);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(2, result.getAnimais().size());
        assertEquals("Rex", result.getAnimais().get(0).name());
        assertEquals("Fluffy", result.getAnimais().get(1).name());
        verify(userRepository, times(1)).findById(userId);
    }
    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ProblemDetailsException exception = assertThrows(ProblemDetailsException.class, () -> {
            getUserAndAnimalsUseCase.execute(userId);
        });

        assertEquals("Usuario nao encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }
}
