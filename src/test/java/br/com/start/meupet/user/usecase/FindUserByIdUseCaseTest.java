package br.com.start.meupet.user.usecase;

import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindUserByIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserByIdUseCase findUserByIdUseCase;

    @Test
    @DisplayName("Should find user successfully")
    void listUsersCase1() {
        List<User> users = createUsers();

        when(userRepository.findById(users.getFirst().getId())).thenReturn(Optional.ofNullable(users.getFirst()));

        UserResponseDTO result = findUserByIdUseCase.execute(users.getFirst().getId());

        Assertions.assertEquals(users.getFirst().getName(), result.getName());

        verify(userRepository, times(1)).findById(users.getFirst().getId());
    }

    @Test
    @DisplayName("Should empty list successfully")
    void listUsersCase2() {

        List<User> users = List.of();

        UUID uuid = UUID.randomUUID();

        when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> findUserByIdUseCase.execute(uuid)
        );

        Assertions.assertEquals("Not found", exception.getMessage());
        verify(userRepository, times(1)).findById(uuid);
    }

    List<User> createUsers() {
        UUID userId = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        User user1 = new User();
        user1.setId(userId);
        user1.setName("User1");
        user1.setSocialName("User");
        user1.setEmail(new Email("aaaaa@gmail.com"));
        user1.setPassword("321312312");
        user1.setDateOfBirth(BirthDayUtils.convertToDate("2000-12-01"));
        user1.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        user1.setPersonalRegistration(new PersonalRegistration("111.111.111-11", DocumentType.CPF));

        User user2 = new User();
        user2.setId(userId2);
        user2.setName("User2");
        user2.setSocialName("User");
        user2.setEmail(new Email("aaaaa@gmail.com"));
        user2.setPassword("321312312");
        user2.setDateOfBirth(BirthDayUtils.convertToDate("2000-12-01"));
        user2.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        user2.setPersonalRegistration(new PersonalRegistration("111.111.111-11", DocumentType.CPF));

        return List.of(user1, user2);
    }
}
