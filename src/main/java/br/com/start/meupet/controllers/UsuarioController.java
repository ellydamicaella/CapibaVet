package br.com.start.meupet.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

	@GetMapping("/teste")
	public String testando() {
		return "Hello World!";
	}
	
}
