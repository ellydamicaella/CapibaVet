package br.com.start.meupet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.entities.VerifyAuthenticableEntity;
import br.com.start.meupet.domain.repository.UserRepository;
import br.com.start.meupet.dto.UserRequestDTO;
import br.com.start.meupet.dto.UserResponseDTO;
import br.com.start.meupet.exceptions.UserNotFoundException;
import br.com.start.meupet.mappers.UserMapper;
import br.com.start.meupet.security.jwt.JwtUtils;

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

    public UserResponseDTO getUserById(Long id) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.userToResponseDTO(userEntity);
    }

    @Transactional
    public UserResponseDTO insert(UserRequestDTO userRequest) {
        User userEntity = UserMapper.userRequestToUser(userRequest);

        serviceUtils.isUserAlreadyExists(userEntity);
           
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        // userRepository.save(userEntity);

        log.info("Usuario criado :{}", userEntity);

        log.info("getEmail :{}", userEntity.getEmail().toString());
        log.info("getName :{}", userEntity.getName());
        log.info("getPhoneNumber :{}", userEntity.getPhoneNumber().toString());
        log.info("getPassword :{}", userEntity.getPassword());

        String token = new JwtUtils().generateTokenFromUserVerifyDetailsImpl(
                userEntity.getEmail().toString(),
                userEntity.getName(),
                userEntity.getPhoneNumber().toString(),
                userEntity.getPassword());

        log.info("token :{}", token);

        VerifyAuthenticableEntity verifyEntity = new VerifyAuthenticableEntity(token);

        log.info("verifyEntity :{}", verifyEntity);

        emailService.sendEmailTemplate(userEntity.getEmail().toString(), "Novo usuário cadastrado",
                userEntity.getName(), verifyEntity.getToken());

        return UserMapper.userToResponseDTO(userEntity);
    }

    @Transactional
    public UserResponseDTO update(long id, UserRequestDTO newUser) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!newUser.getEmail().equals(userEntity.getEmail().toString())) {
         serviceUtils.isUserAlreadyExists(userEntity);
        }
        if (!newUser.getPhoneNumber().equals(userEntity.getPhoneNumber().toString())) {
           serviceUtils.isUserAlreadyExists(userEntity);
        }

        User updatedUser = UserMapper.userBeforeToNewUser(userEntity, UserMapper.userRequestToUser(newUser));
        userRepository.save(updatedUser);

        log.info("Usuario atualizado :{}", updatedUser);
        return UserMapper.userToResponseDTO(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(userEntity);
        log.info("Usuario deletado :{}", userEntity);
    }
}
