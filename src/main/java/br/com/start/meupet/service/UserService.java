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

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

	private static Logger log = LoggerFactory.getLogger(UserService.class);

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
		User userEntity = new User(usuario.getName(), new Email(usuario.getEmail()), usuario.getPassword(),
				new CellPhoneNumber(usuario.getCellPhoneNumber()));

		if (isAlreadyHaveEmail(userEntity.getEmail())) {
			throw new EmailAlreadyUsedException("There is already someone with that email: " + usuario.getEmail());
		}

		userEntity.setPassword(passwordEncoder.encode(usuario.getPassword()));
		
		User userResponse = userRepository.save(userEntity);

		VerifyAuthenticableEntity verify = new VerifyAuthenticableEntity();
		verify.setUser(userEntity);
		verify.setUuid(UUID.randomUUID());
		verify.setExpirationDate(Instant.now().plusMillis(300000));

		log.trace("Dentro do insert do if isAlreadyHaveEmail");

		return new UserResponseDTO(userResponse.getId().longValue(), userResponse.getName(), userResponse.getPassword(),
				userResponse.getEmail().toString(), userResponse.getCellPhoneNumber().toString());
	}

	public UserResponseDTO update(long id, UserRequestDTO newUser) {

		User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

		userEntity.setName(newUser.getName());
		userEntity.setEmail(new Email(newUser.getEmail()));
		userEntity.setCellPhoneNumber(new CellPhoneNumber(newUser.getCellPhoneNumber()));
		userEntity.setUpdatedAt(Instant.now());

		User updatedUser = userRepository.save(userEntity);

		return new UserResponseDTO(updatedUser);

	}

	public void delete(Long id) {
		User userEntity = userRepository.findById(id).get();
		userRepository.delete(userEntity);
	}

	public UserResponseDTO findById(Long id) {
		return new UserResponseDTO(userRepository.findById(id).get());
	}

	private boolean isAlreadyHaveEmail(Email email) {
		Optional<User> result = Optional.ofNullable(userRepository.findByEmail(email));
		log.trace("Dentro do isAlreadyHaveEmail - Result: {}", result);
		return result.isPresent();
	}

}
