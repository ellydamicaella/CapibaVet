package br.com.start.meupet.user.usecase;


import br.com.start.meupet.auth.service.EmailService;
import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.security.jwt.JwtUtils;
import br.com.start.meupet.common.service.ServiceUtils;
import br.com.start.meupet.common.templates.TemplateNameEnum;
import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class InitUserRegistrationUseCaseTest {

    @Mock
    private ServiceUtils serviceUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private InitUserRegistrationUseCase initUserRegistrationUseCase;

    @Test
    void shouldExecuteSuccessfully() {
        UserRequestDTO userRequest = getUserRequestDTOAllIsOk();

        String encodedPassword = "encodedPassword123";
        String token = "testVerificationToken";

        Mockito.doNothing().when(serviceUtils).isUserAlreadyExists(Mockito.any(User.class));
        Mockito.when(passwordEncoder.encode(userRequest.getPassword())).thenReturn(encodedPassword);
        Mockito.when(jwtUtils.generateTokenForUserVerifyAccount(Mockito.any(User.class))).thenReturn(token);
        Mockito.doNothing().when(emailService).sendEmailTemplate(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(TemplateNameEnum.class),
                Mockito.anyString(),
                Mockito.anyString()
        );

        initUserRegistrationUseCase.execute(userRequest);

        Mockito.verify(serviceUtils).isUserAlreadyExists(Mockito.any(User.class));
        Mockito.verify(passwordEncoder).encode(userRequest.getPassword());
        Mockito.verify(jwtUtils).generateTokenForUserVerifyAccount(Mockito.any(User.class));
        Mockito.verify(emailService).sendEmailTemplate(
                userRequest.getEmail(),
                "Novo usuário cadastrado",
                TemplateNameEnum.EMAIL_CONFIRM_ACCOUNT,
                userRequest.getName(),
                token
        );
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        UserRequestDTO userRequest = getUserRequestDTOAllIsOk();

        Mockito.doThrow(new IllegalArgumentException("User already exists"))
                .when(serviceUtils).isUserAlreadyExists(Mockito.any(User.class));

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> initUserRegistrationUseCase.execute(userRequest));

        Assertions.assertEquals("User already exists", exception.getMessage());
        Mockito.verify(serviceUtils).isUserAlreadyExists(Mockito.any(User.class));
        Mockito.verifyNoInteractions(passwordEncoder, jwtUtils, emailService);
    }

    UserRequestDTO getUserRequestDTOAllIsOk() {
        UserRequestDTO newUser = new UserRequestDTO();
        newUser.setName("New Name");
        newUser.setPassword("newPassword");
        newUser.setSocialName("User");
        newUser.setEmail("aaaaa@gmail.com");
        newUser.setBirthDate("2000-12-01");
        newUser.setPhoneNumber("(21) 22222-2222");
        newUser.setDocument("111.111.111-11");
        newUser.setDocumentType("CPF");
        return newUser;
    }

    User getUserAllIsOk(UserRequestDTO newUser) {
        User user = new User();
        user.setName(newUser.getName());
        user.setPassword("encodedPassword");
        user.setEmail(new Email(newUser.getEmail()));
        user.setDateOfBirth(BirthDayUtils.convertToDate(newUser.getBirthDate()));
        user.setPhoneNumber(new PhoneNumber(newUser.getPhoneNumber()));
        user.setPersonalRegistration(new PersonalRegistration(newUser.getDocument(), DocumentType.valueOf(newUser.getDocumentType())));
        return user;
    }
}
