package br.com.start.meupet.agendamento.usecase;

import br.com.start.meupet.agendamento.dto.animal.UserAnimalDTO;
import br.com.start.meupet.agendamento.model.Animal;
import br.com.start.meupet.agendamento.usecase.animal.ListAllUsersAndAnimalsUseCase;
import br.com.start.meupet.common.enums.DocumentType;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListAllUsersAndAnimalsUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ListAllUsersAndAnimalsUseCase listAllUsersAndAnimalsUseCase;

    @Test
    void shouldReturnAllUsersAndTheirAnimals() {
        // Arrange
        List<User> mockUsers = new ArrayList<>();

        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setName("John Doe");
        user1.setSocialName("User");
        user1.setEmail(new Email("aaaaa@gmail.com"));
        user1.setPassword("321312312");
        user1.setDateOfBirth(BirthDayUtils.convertToDate("2000-12-01"));
        user1.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        user1.setPersonalRegistration(new PersonalRegistration("11111111111", DocumentType.CPF));


        Animal animal1 = new Animal();
        animal1.setId(1L);
        animal1.setName("Rex");
        user1.setAnimals(List.of(animal1));

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setName("Jane Smith");
        user2.setSocialName("User");
        user2.setEmail(new Email("aaaaa@gmail.com"));
        user2.setPassword("321312312");
        user2.setDateOfBirth(BirthDayUtils.convertToDate("2000-12-01"));
        user2.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        user2.setPersonalRegistration(new PersonalRegistration("11111111111", DocumentType.CPF));



        Animal animal2 = new Animal();
        animal2.setId(2L);
        animal2.setName("Fluffy");
        Animal animal3 = new Animal();
        animal3.setId(3L);
        animal3.setName("Buddy");
        user2.setAnimals(List.of(animal2, animal3));

        mockUsers.add(user1);
        mockUsers.add(user2);

        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        List<UserAnimalDTO> result = listAllUsersAndAnimalsUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verificar detalhes do usuário 1
        assertEquals("John Doe", result.get(0).getName());
        assertEquals(1, result.get(0).getAnimais().size());
        assertEquals("Rex", result.get(0).getAnimais().get(0).name());

        // Verificar detalhes do usuário 2
        assertEquals("Jane Smith", result.get(1).getName());
        assertEquals(2, result.get(1).getAnimais().size());
        assertEquals("Fluffy", result.get(1).getAnimais().get(0).name());
        assertEquals("Buddy", result.get(1).getAnimais().get(1).name());

        verify(userRepository, times(1)).findAll();
    }
}
