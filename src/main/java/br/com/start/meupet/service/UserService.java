package br.com.start.meupet.service;

import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.entities.VerifyAuthenticableEntity;
import br.com.start.meupet.domain.repository.UserRepository;
import br.com.start.meupet.domain.valueobjects.CellPhoneNumber;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.dto.UserRequestDTO;
import br.com.start.meupet.dto.UserResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> listAll() {
        List<User> usuarios = userRepository.findAll();
        return usuarios.stream().map(UserResponseDTO::new).toList();
    }

    public UserResponseDTO insert(UserRequestDTO usuario) {
        User userEntity = new User(0, usuario.getName(), new Email(usuario.getEmail()), usuario.getPassword(),
                new CellPhoneNumber(usuario.getCellPhoneNumber()));

        userEntity.setPassword(passwordEncoder.encode(usuario.getPassword()));

        User userResponse = userRepository.save(userEntity);

        VerifyAuthenticableEntity verify = new VerifyAuthenticableEntity();
        verify.setUser(userEntity);
        verify.setUuid(UUID.randomUUID());
        verify.setExpirationDate(Instant.now().plusMillis(300000));

        return new UserResponseDTO(userResponse.getId().longValue(), userResponse.getName(), userResponse.getPassword(), userResponse.getEmail().toString(), userResponse.getCellPhoneNumber().toString());
    }

    public UserResponseDTO update(UserRequestDTO usuario) {
        User userEntity = new User(0, usuario.getName(), new Email(usuario.getEmail()), usuario.getPassword(),
                new CellPhoneNumber(usuario.getCellPhoneNumber()));

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return new UserResponseDTO(userRepository.save(userEntity));
    }


    public void delete(Long id) {
        User userEntity = userRepository.findById(id).get();
        userRepository.delete(userEntity);
    }


    public UserResponseDTO findById(Long id) {
        return new UserResponseDTO(userRepository.findById(id).get());
    }

}
