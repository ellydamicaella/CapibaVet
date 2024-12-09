package br.com.start.meupet.user.usecase;

import br.com.start.meupet.common.enums.DocumentType;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListUsersUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ListUsersUseCase listUsersUseCase;

    @Test
    @DisplayName("Should list users successfully")
    void listUsersCase1() {
        // Arrange
        int page = 0;
        int pageSize = 2;

        List<User> users = createUsers();
        Page<User> userPage = new PageImpl<>(users, PageRequest.of(page, pageSize), users.size());

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        // Act
        List<UserResponseDTO> result = listUsersUseCase.execute(page, pageSize);

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("User1", result.get(0).getName());
        Assertions.assertEquals("User2", result.get(1).getName());

        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should empty list successfully")
    void listUsersCase2() {
        int page = 0;
        int pageSize = 2;

        List<User> users = List.of();
        Page<User> userPage = new PageImpl<>(users, PageRequest.of(page, pageSize), 0);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        List<UserResponseDTO> result = listUsersUseCase.execute(page, pageSize);

        Assertions.assertEquals(0, result.size());
        verify(userRepository, times(1)).findAll(any(Pageable.class));
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
