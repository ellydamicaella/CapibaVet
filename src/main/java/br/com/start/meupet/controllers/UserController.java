package br.com.start.meupet.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.start.meupet.dto.UserRequestDTO;
import br.com.start.meupet.dto.UserResponseDTO;
import br.com.start.meupet.service.UserService;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> listAll() {
		return ResponseEntity.ok(userService.listAll());
	}

	@PostMapping
	public ResponseEntity<UserResponseDTO> insert(@RequestBody UserRequestDTO usuario) {
		UserResponseDTO user = userService.insert(usuario);
		log.info("Usuario criado :" + user.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@PutMapping
	public ResponseEntity<UserResponseDTO> update(@PathVariable long id, @RequestBody UserRequestDTO newUser) {
		UserResponseDTO updatedUser = userService.update(id, newUser);
		return ResponseEntity.ok().body(updatedUser);
	}

	// http://localhost:8080/user?id=3
	@DeleteMapping
	public ResponseEntity<Void> delete(@RequestParam Long id) {
		userService.delete(id);
		return ResponseEntity.ok().build();
	}

}
