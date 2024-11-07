package br.com.start.meupet.service;

import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.entities.VerifyAuthenticableEntity;
import br.com.start.meupet.domain.repository.UserRepository;
import br.com.start.meupet.domain.valueobjects.CellPhoneNumber;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.dto.UserRequestDTO;
import br.com.start.meupet.dto.UserResponseDTO;
import br.com.start.meupet.exceptions.EmailAlreadyUsedException;
import br.com.start.meupet.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> listAll() {
        List<User> users = userRepository.findAll();
        log.info("Usuario listados :{}", users.stream().map(User::getId).collect(Collectors.toList()));
        return users.stream().map(UserResponseDTO::new).toList();
    }

    public UserResponseDTO getUserById(Long id) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserResponseDTO(userEntity);
    }

    @Transactional
    public UserResponseDTO insert(UserRequestDTO usuario) {
        User userEntity = new User(usuario.getName(), new Email(usuario.getEmail()), usuario.getPassword(),
                new CellPhoneNumber(usuario.getCellPhoneNumber()));

        if (isAlreadyHaveEmail(userEntity.getEmail())) {
            log.info("Esse email já existe!, {}", userEntity.getEmail().toString());
            throw new EmailAlreadyUsedException("There is already someone with that email: " + usuario.getEmail());
        }

        userEntity.setPassword(passwordEncoder.encode(usuario.getPassword()));

        User userResponse = userRepository.save(userEntity);
        log.info("Usuario criado :{}", userResponse.toString());
        VerifyAuthenticableEntity verify = new VerifyAuthenticableEntity();
        verify.setUser(userEntity);
        verify.setUuid(UUID.randomUUID());
        verify.setExpirationDate(Instant.now().plusMillis(300000));
        

        return new UserResponseDTO(userResponse);
    }

    @Transactional
    public UserResponseDTO update(long id, UserRequestDTO newUser) {

        User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        userEntity.setName(newUser.getName());
        userEntity.setEmail(new Email(newUser.getEmail()));
        userEntity.setCellPhoneNumber(new CellPhoneNumber(newUser.getCellPhoneNumber()));
        userEntity.setUpdatedAt(Instant.now());

        User updatedUser = userRepository.save(userEntity);
        log.info("Usuario atualizado :{}", updatedUser.toString());

        return new UserResponseDTO(updatedUser);

    }

    @Transactional
    public void delete(Long id) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(userEntity);
        log.info("Usuario deletado :{}", userEntity.toString());
    }

    private boolean isAlreadyHaveEmail(Email email) {
        Optional<User> result = Optional.ofNullable(userRepository.findByEmail(email));
        log.info("Verificando se já existe o email...");
        return result.isPresent();
    }

}
