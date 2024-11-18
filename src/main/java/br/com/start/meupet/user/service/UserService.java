package br.com.start.meupet.user.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.start.meupet.common.service.EmailService;
import br.com.start.meupet.common.service.utils.VerifyAuthenticable;
import br.com.start.meupet.common.service.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.user.service.mappers.UserMapper;
import br.com.start.meupet.common.security.jwt.JwtUtils;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ServiceUtils serviceUtils;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService, ServiceUtils serviceUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.serviceUtils = serviceUtils;
    }

    public List<UserResponseDTO> listAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<User> users = userRepository.findAll(pageable).getContent();
        log.info("Usuario listados :{}", users.stream().map(User::getId).collect(Collectors.toList()));
        return users.stream().map(UserResponseDTO::new).toList();
    }

    public UserResponseDTO getUserById(UUID id) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserMapper.userToResponseDTO(userEntity);
    }

    @Transactional
    public UserResponseDTO insert(UserRequestDTO userRequest) {
        User userEntity = UserMapper.userRequestToUser(userRequest);

        serviceUtils.isUserAlreadyExists(userEntity);
           
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        // userRepository.save(userEntity);

        log.info("Usuario criado, aguardando a confirmação da conta :{}", userEntity);

        log.info("getEmail :{}", userEntity.getEmail().toString());
        log.info("getName :{}", userEntity.getName());
        log.info("getPhoneNumber :{}", userEntity.getPhoneNumber().toString());
        log.info("getPassword :{}", userEntity.getPassword());

        String token = new JwtUtils().generateTokenForUserVerifyAccount(
                userEntity.getEmail().toString(),
                userEntity.getName(),
                userEntity.getPhoneNumber().toString(),
                userEntity.getPassword());

        log.info("token :{}", token);

        VerifyAuthenticable verifyEntity = new VerifyAuthenticable(token);

        log.info("verifyEntity :{}", verifyEntity);

        emailService.sendEmailTemplate(userEntity.getEmail().toString(), "Novo usuário cadastrado",
                userEntity.getName(), verifyEntity.getToken());

        return UserMapper.userToResponseDTO(userEntity);
    }

    @Transactional
    public UserResponseDTO update(UUID id, UserRequestDTO newUser) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        serviceUtils.isUserAlreadyExists(userEntity);

        User updatedUser = UserMapper.userBeforeToNewUser(userEntity, UserMapper.userRequestToUser(newUser));
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userRepository.save(updatedUser);

        log.info("Usuario atualizado :{}", updatedUser);
        return UserMapper.userToResponseDTO(updatedUser);
    }

    @Transactional
    public void delete(UUID id) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(userEntity);
        log.info("Usuario deletado :{}", userEntity);
    }
}
