package br.com.start.meupet.user.service;

import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import br.com.start.meupet.user.service.mappers.UserMapper;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.Mockito.when;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    EntityManager entityManager;

    @InjectMocks
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        List<User> users = List.of(
                new User("Thiago1", new Email("usuario1@example.com"), "senhaSegura123",  new PhoneNumber("(11) 12 98765-4321")),
                new User("Thiago2", new Email("usuario2@example.com"), "senhaSegura456",  new PhoneNumber("(21) 23 91234-5678")),
                new User("Thiago3", new Email("usuario3@example.com"), "senhaSegura789",  new PhoneNumber("(31) 23 99876-5432")),
                new User("Thiago4", new Email("usuario4@example.com"), "senhaSegura012",  new PhoneNumber("(41) 23 91234-5678")),
                new User("Thiago5", new Email("usuario5@example.com"), "senhaSegura345",  new PhoneNumber("(51) 23 98765-4321")),
                new User("Thiago6", new Email("usuario6@example.com"), "senhaSegura678",  new PhoneNumber("(61) 32 99876-5432")),
                new User("Thiago7", new Email("usuario7@example.com"), "senhaSegura901",  new PhoneNumber("(71) 23 91234-5678")),
                new User("Thiago8", new Email("usuario8@example.com"), "senhaSegura234",  new PhoneNumber("(81) 23 98765-4321")),
                new User("Thiago9", new Email("usuario9@example.com"), "senhaSegura567",  new PhoneNumber("(91) 23 99876-5432")),
                new User("Thiago10",  new Email("usuario10@example.com"), "senhaSegura890",  new PhoneNumber("(99) 23 91234-5678"))
        );
        userRepository.saveAll(users);
        log.info("setUp em UserServiceTest completo!");
    }

    private void createUsers() {
        List<User> users = List.of(
                new User("Thiago1", new Email("usuario1@example.com"), "senhaSegura123",  new PhoneNumber("(11) 12 98765-4321")),
                new User("Thiago2", new Email("usuario2@example.com"), "senhaSegura456",  new PhoneNumber("(21) 23 91234-5678")),
                new User("Thiago3", new Email("usuario3@example.com"), "senhaSegura789",  new PhoneNumber("(31) 23 99876-5432")),
                new User("Thiago4", new Email("usuario4@example.com"), "senhaSegura012",  new PhoneNumber("(41) 23 91234-5678")),
                new User("Thiago5", new Email("usuario5@example.com"), "senhaSegura345",  new PhoneNumber("(51) 23 98765-4321")),
                new User("Thiago6", new Email("usuario6@example.com"), "senhaSegura678",  new PhoneNumber("(61) 32 99876-5432")),
                new User("Thiago7", new Email("usuario7@example.com"), "senhaSegura901",  new PhoneNumber("(71) 23 91234-5678")),
                new User("Thiago8", new Email("usuario8@example.com"), "senhaSegura234",  new PhoneNumber("(81) 23 98765-4321")),
                new User("Thiago9", new Email("usuario9@example.com"), "senhaSegura567",  new PhoneNumber("(91) 23 99876-5432")),
                new User("Thiago10",  new Email("usuario10@example.com"), "senhaSegura890",  new PhoneNumber("(99) 23 91234-5678"))
        );

        this.entityManager.persist(users);
    }

    @Test
    @DisplayName("Should list all users successfully when every is ok")
    void listAllCase1() {
        int pageDefault = 0;
        int pageSizeDefault = 10;

        Pageable pageable = PageRequest.of(pageDefault, pageSizeDefault);
        List<User> list = userRepository.findAll(pageable).getContent();
        List<UserResponseDTO> listResponseDTO = list.stream().map(UserResponseDTO::new).toList();
        when(userService.listAll(pageDefault, pageSizeDefault)).thenReturn(listResponseDTO);

        List<UserResponseDTO> listTesting = userService.listAll(pageDefault, pageSizeDefault);

    }

    @Test
    void getUserById() {
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}