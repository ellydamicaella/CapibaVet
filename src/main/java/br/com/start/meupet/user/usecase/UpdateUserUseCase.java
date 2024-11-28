package br.com.start.meupet.user.usecase;

import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.services.ServiceUtils;
import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.user.mapper.UserMapper;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
public class UpdateUserUseCase {


    private final UserRepository userRepository;
    private final ServiceUtils serviceUtils;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserUseCase(UserRepository userRepository, ServiceUtils serviceUtils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.serviceUtils = serviceUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO execute(UUID id, UserRequestDTO newUser) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        // valida usuario, verifica se a conta já existe
        validateUser(userEntity);
        // cria a nova entidade usuario atualiazda
        User updatedUser = UserMapper.userBeforeToNewUser(userEntity, UserMapper.userRequestToUser(newUser));
        // criptografa a senha
        encodePassword(updatedUser, updatedUser.getPassword());
        // salva no banco o usuario
        userRepository.save(updatedUser);

        log.info("Usuario atualizado :{}", updatedUser);
        return UserMapper.userToResponseDTO(updatedUser);
    }

    private void validateUser(User userEntity) {
        serviceUtils.isUserAlreadyExists(userEntity);
    }

    private void encodePassword(User userEntity, String rawPassword) {
        userEntity.setPassword(passwordEncoder.encode(rawPassword));
    }
}
