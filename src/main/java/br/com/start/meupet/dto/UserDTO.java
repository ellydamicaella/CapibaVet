package br.com.start.meupet.dto;

import org.springframework.beans.BeanUtils;

import br.com.start.meupet.domain.entities.Usuario;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.Telefone;

public class UserDTO {

	private Long id;
	private String name;
	private String senha;
	private Email email;
	private Telefone telefone;

	public UserDTO() {

	}

	public UserDTO(Usuario usuario) {
		BeanUtils.copyProperties(usuario, this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

}
