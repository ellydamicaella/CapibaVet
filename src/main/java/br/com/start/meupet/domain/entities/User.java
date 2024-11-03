package br.com.start.meupet.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import br.com.start.meupet.domain.interfaces.Authenticable;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.Telefone;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuario")
public class Usuario implements Authenticable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "nome_completo")
	@NotNull
	private String name;

	@Column(name = "email")
	@NotNull
	@Embedded
	private Email email;

	@Column(name = "senha")
	@NotNull
	private String senha;

	@Column(name = "telefone")
	@NotNull
	@Embedded
	private Telefone telefone;

	@Column(name = "createdAt")
	private LocalDateTime createdAt;

	public Usuario() {

	}

	public Usuario(@NotNull String name, @NotNull Email email, @NotNull String senha, @NotNull Telefone telefone) {
		this.name = name;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	public Number getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return id == other.id;
	}

}
