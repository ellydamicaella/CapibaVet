package br.com.start.meupet.agendamento.usecase;

import br.com.start.meupet.agendamento.dto.animal.AnimalRequestDTO;
import br.com.start.meupet.agendamento.enums.AnimalSexo;
import br.com.start.meupet.agendamento.enums.AnimalType;
import br.com.start.meupet.agendamento.model.Animal;
import br.com.start.meupet.agendamento.repository.AnimalRepository;
import br.com.start.meupet.agendamento.usecase.animal.AddNewAnimalToUserUseCase;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddNewAnimalToUserUseCaseTest {
    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddNewAnimalToUserUseCase addNewAnimalToUserUseCase;

    @Test
    void shouldAddAnimalSuccessfully() {
        // Arrange
        UUID userId = UUID.randomUUID();
            AnimalRequestDTO animalRequest = new AnimalRequestDTO("Max", "1", "Doente", "CACHORRO", "M");
        User mockUser = new User();
        mockUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(animalRepository.save(any(Animal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Animal result = addNewAnimalToUserUseCase.execute(userId, animalRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Max", result.getName());
        assertEquals("1", result.getAge());
        assertEquals("Doente", result.getHistory());
        assertEquals(AnimalType.CACHORRO, result.getType());
        assertEquals(AnimalSexo.M, result.getSexo());
        verify(userRepository, times(1)).findById(userId);
        verify(animalRepository, times(1)).save(any(Animal.class));
    }

}
