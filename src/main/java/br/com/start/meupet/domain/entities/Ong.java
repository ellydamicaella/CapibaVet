package br.com.start.meupet.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import br.com.start.meupet.domain.interfaces.Authenticable;
import br.com.start.meupet.domain.valueobjects.Cnpj;
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
@Table(name = "ong")
public class Ong implements Authenticable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "nome_fantasia")
	@NotNull
	private String name;

	@Column(name = "email")
	@NotNull
	private Email email;

	@Column(name = "cnpj")
	@NotNull
	@Embedded
	private Cnpj cnpj;

	@Column(name = "senha")
	@NotNull
	private String senha;

	@Column(name = "telefone")
	@NotNull
	@Embedded
	private Telefone telefone;

	@Column(name = "createdAt")
	private LocalDateTime createdAt;

	public Ong() {

	}

	public Ong(@NotNull String name, @NotNull Email email, @NotNull Cnpj cnpj, @NotNull String senha,
			@NotNull Telefone telefone) {
		this.name = name;
		this.email = email;
		this.cnpj = cnpj;
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

	public void setId(int id) {
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

	public Cnpj getCnpj() {
		return cnpj;
	}

	public void setCnpj(Cnpj cnpj) {
		this.cnpj = cnpj;
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
		Ong other = (Ong) obj;
		return id == other.id;
	}

}
