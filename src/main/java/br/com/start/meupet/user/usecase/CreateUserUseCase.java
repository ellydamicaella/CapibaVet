package br.com.start.meupet.user.usecase;

import br.com.start.meupet.common.security.jwt.JwtUtils;
import br.com.start.meupet.common.service.EmailService;
import br.com.start.meupet.common.service.ServiceUtils;
import br.com.start.meupet.common.utils.VerifyAuthenticable;
import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.user.mapper.UserMapper;
import br.com.start.meupet.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class CreateUserUseCase {

    private final ServiceUtils serviceUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    public CreateUserUseCase(ServiceUtils serviceUtils, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, EmailService emailService) {
        this.serviceUtils = serviceUtils;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
    }

    @Transactional
    public UserResponseDTO execute(UserRequestDTO userRequest) {
        User userEntity = UserMapper.userRequestToUser(userRequest);
        //valida usuario
        validateUser(userEntity);
        //criptografa senha
        encodePassword(userEntity, userRequest.getPassword());
        //gera Token com os dados do usuario
        String token = generateVerificationToken(userEntity);
        // envia o email
        sendVerificationEmail(userEntity, token);
        log.info("Usario criado, aguardando a confirmação da conta :{}", userEntity);

        return UserMapper.userToResponseDTO(userEntity);
    }


    private void validateUser(User userEntity) {
        serviceUtils.isUserAlreadyExists(userEntity);
    }

    private void encodePassword(User userEntity, String rawPassword) {
        userEntity.setPassword(passwordEncoder.encode(rawPassword));
    }

    private String generateVerificationToken(User userEntity) {
        return jwtUtils.generateTokenForUserVerifyAccount(
                userEntity.getEmail().toString(),
                userEntity.getName(),
                userEntity.getPhoneNumber().toString(),
                userEntity.getPassword()
        );
    }

    private void sendVerificationEmail(User userEntity, String token) {
        VerifyAuthenticable verifyEntity = new VerifyAuthenticable(token);
        emailService.sendEmailTemplate(
                userEntity.getEmail().toString(),
                "Novo usuário cadastrado",
                userEntity.getName(),
                verifyEntity.getToken()
        );
    }

}
