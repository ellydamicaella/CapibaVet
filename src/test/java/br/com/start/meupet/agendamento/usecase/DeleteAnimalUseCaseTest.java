package br.com.start.meupet.agendamento.usecase;

import br.com.start.meupet.agendamento.model.Animal;
import br.com.start.meupet.agendamento.repository.AnimalRepository;
import br.com.start.meupet.agendamento.usecase.animal.DeleteAnimalUseCase;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAnimalUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private DeleteAnimalUseCase deleteAnimalUseCase;

    @Test
    void shouldDeleteAnimalSuccessfully() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Long animalId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        deleteAnimalUseCase.execute(userId, animalId);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(animalRepository, times(1)).delete(any(Animal.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Long animalId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ProblemDetailsException exception = assertThrows(ProblemDetailsException.class, () -> {
            deleteAnimalUseCase.execute(userId, animalId);
        });

        assertEquals("Usuario nao encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(animalRepository, never()).delete(any(Animal.class));
    }

    @Test
    void shouldCallDeleteWithCorrectAnimalId() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Long animalId = 42L;
        User mockUser = new User();
        mockUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        deleteAnimalUseCase.execute(userId, animalId);

        // Assert
        verify(animalRepository, times(1)).delete(argThat(animal -> animal.getId().equals(animalId)));
    }
}

