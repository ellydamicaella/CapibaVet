package br.com.start.meupet.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.start.meupet.domain.entities.Usuario;
import br.com.start.meupet.domain.entities.VerifyAuthenticableEntity;
import br.com.start.meupet.domain.repository.UsuarioRepository;
import br.com.start.meupet.dto.UserDTO;

@Service
public class UserService {

	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<UserDTO> listAll() {
		List<Usuario> usuarios = userRepository.findAll();
		return usuarios.stream().map(UserDTO::new).toList();
	}

	public void insert(UserDTO usuario) {
		Usuario user = new Usuario(usuario.getName(), usuario.getEmail(), usuario.getSenha(), usuario.getTelefone());
		user.setSenha(passwordEncoder.encode(usuario.getSenha()));

		userRepository.save(user);

		VerifyAuthenticableEntity verify = new VerifyAuthenticableEntity();
		verify.setUser(user);
		verify.setUuid(UUID.randomUUID());
		verify.setExpirationDate(Instant.now().plusMillis(300000));

	}

	public UserDTO update(UserDTO usuario) {
		Usuario userEntity = new Usuario(usuario.getName(), usuario.getEmail(), usuario.getSenha(),
				usuario.getTelefone());
		userEntity.setSenha(passwordEncoder.encode(userEntity.getSenha()));
		return new UserDTO(userRepository.save(userEntity));
	}

	public void delete(Long id) {
		Usuario userEntity = userRepository.findById(id).get();
		userRepository.delete(userEntity);
	}
	
	public UserDTO findById(Long id) {
		return new UserDTO(userRepository.findById(id).get());
	}

}
