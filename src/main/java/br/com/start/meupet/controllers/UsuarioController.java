package br.com.start.meupet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.start.meupet.dto.UserDTO;
import br.com.start.meupet.service.UserService;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UsuarioController {
	
	@Autowired
	private UserService userService;

	@GetMapping
	public List<UserDTO> listAll() {
		return userService.listAll();
	}
	
	@PostMapping
	public void inserir(@RequestBody UserDTO usuario) {
		userService.insert(usuario);
	}
	
	@PutMapping
	public UserDTO alterar(@RequestBody UserDTO usuario) {
		return userService.update(usuario);
	}
	
	//http://endereco/usuario/3
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
		userService.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
