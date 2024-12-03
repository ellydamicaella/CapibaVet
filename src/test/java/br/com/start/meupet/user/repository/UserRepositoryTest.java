package br.com.start.meupet.user.repository;

import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

//    @Test
//    @DisplayName("Should get user successfully from DB")
//    void findByEmailCase1() {
//        UserRequestDTO userRequest = new UserRequestDTO(
//        "Thiago",
//        "123123123",
//        "debora-corte@tuamaeaquelaursa.com",
//        "(52) 21 22222-2222"
//        );
//
//        createUser(userRequest);
//
//        User foundedUser = this.userRepository.findByEmail(new Email(userRequest.getEmail()));
//
//        assertThat(foundedUser != null).isTrue();
//    }
//
//    @Test
//    @DisplayName("Should not get user from DB when user not exists")
//    void findByEmailCase2() {
//        UserRequestDTO userRequest = new UserRequestDTO(
//                "Thiago",
//                "123123123",
//                "debora-corte@tuamaeaquelaursa.com",
//                "(52) 21 22222-2222"
//        );
//
//        User foundedUser = this.userRepository.findByEmail(new Email(userRequest.getEmail()));
//
//        assertThat(foundedUser == null).isTrue();
//    }
//
//    @Test
//    void findByPhoneNumberCase1() {
//        UserRequestDTO userRequest = new UserRequestDTO(
//        "Thiago",
//        "123123123",
//        "debora-corte@tuamaeaquelaursa.com",
//        "(52) 21 22222-2222"
//        );
//
//        createUser(userRequest);
//
//        User foundedUser = this.userRepository.findByPhoneNumber(new PhoneNumber(userRequest.getPhoneNumber()));
//
//        assertThat(foundedUser != null).isTrue();
//    }
//
//    @Test
//    void findByPhoneNumberCase2() {
//        UserRequestDTO userRequest = new UserRequestDTO(
//                "Thiago",
//                "123123123",
//                "debora-corte@tuamaeaquelaursa.com",
//                "(52) 21 22222-2222"
//        );
//
//        User foundedUser = this.userRepository.findByPhoneNumber(new PhoneNumber(userRequest.getPhoneNumber()));
//
//        assertThat(foundedUser == null).isTrue();
//    }
//
//    private void createUser(UserRequestDTO data) {
//        User newUser = UserMapper.userRequestToUser(data);
//        this.entityManager.persist(newUser);
//    }
}