package br.com.start.meupet.service;

import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.entities.VerifyAuthenticableEntity;
import br.com.start.meupet.domain.repository.UserRepository;
import br.com.start.meupet.domain.repository.VerifyAuthenticableEntityRepository;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.PhoneNumber;
import br.com.start.meupet.dto.UserRequestDTO;
import br.com.start.meupet.dto.UserResponseDTO;
import br.com.start.meupet.exceptions.EmailAlreadyUsedException;
import br.com.start.meupet.exceptions.PhoneNumberAlreadyUsedException;
import br.com.start.meupet.exceptions.UserNotFoundException;
import br.com.start.meupet.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final VerifyAuthenticableEntityRepository verifyAuthenticableEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, VerifyAuthenticableEntityRepository verifyAuthenticableEntityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verifyAuthenticableEntityRepository = verifyAuthenticableEntityRepository;
    }

    public List<UserResponseDTO> listAll() {
        List<User> users = userRepository.findAll();
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

        if (isAlreadyHaveEmail(userEntity.getEmail())) {
            log.info("Esse email já existe!, {}", userRequest.getEmail());
            throw new EmailAlreadyUsedException("There is already someone with that email: " + userRequest.getEmail());
        }

        if (isAlreadyHavePhoneNumber(userEntity.getPhoneNumber())) {
            log.info("Esse telefone já existe!, {}", userRequest.getPhoneNumber());
            throw new PhoneNumberAlreadyUsedException("There is already someone with that phoneNumber: " + userRequest.getPhoneNumber());
        }

        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.save(userEntity);
        log.info("Usuario criado :{}", userEntity.toString());
        VerifyAuthenticableEntity verifyEntity = new VerifyAuthenticableEntity(UUID.randomUUID(), LocalDateTime.now().plusMinutes(10), userEntity);
        verifyAuthenticableEntityRepository.save(verifyEntity);

        return UserMapper.userToResponseDTO(userEntity);
    }

    @Transactional
    public UserResponseDTO update(long id, UserRequestDTO newUser) {

        User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (isAlreadyHaveEmail(userEntity.getEmail())) {
            log.info("Esse email já existe!, {}", userEntity.getEmail().toString());
            throw new EmailAlreadyUsedException("There is already someone with that email: " + userEntity.getEmail());
        }

        if (isAlreadyHavePhoneNumber(userEntity.getPhoneNumber())) {
            log.info("Esse telefone já existe!, {}", userEntity.getPhoneNumber().toString());
            throw new PhoneNumberAlreadyUsedException("There is already someone with that phoneNumber: " + userEntity.getPhoneNumber());
        }

        userEntity = UserMapper.userRequestToUser(newUser);
        userEntity.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(userEntity);
        log.info("Usuario atualizado :{}", updatedUser.toString());

        return UserMapper.userToResponseDTO(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(userEntity);
        log.info("Usuario deletado :{}", userEntity.toString());
    }

    private boolean isAlreadyHavePhoneNumber(PhoneNumber PhoneNumber) {
        Optional<User> result = Optional.ofNullable(userRepository.findByPhoneNumber(PhoneNumber));
        log.info("Verificando se já existe o numero de telefone...");
        return result.isPresent();
    }

    private boolean isAlreadyHaveEmail(Email email) {
        Optional<User> result = Optional.ofNullable(userRepository.findByEmail(email));
        log.info("Verificando se já existe o email...");
        return result.isPresent();
    }

}
