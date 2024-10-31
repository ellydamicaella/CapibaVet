package br.com.start.meupet.domain.entities;

import java.time.LocalDateTime;

import br.com.start.meupet.domain.valueobjects.Cnpj;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.Telefone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ong")
public class Ong {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "nome_fantasia")
	@NotNull
	private String nomeFantasia;

	@Column(name = "email")
	@NotNull
	private Email email;

	@Column(name = "cnpj")
	@NotNull
	private Cnpj cnpj;

	@Column(name = "senha")
	@NotNull
	private String senha;

	@Column(name = "telefone")
	@NotNull
	private Telefone telefone;

	@Column(name = "createdAt")
	private LocalDateTime createdAt;
	
	public Ong() {

	}

	public Ong( @NotNull String nomeFantasia, @NotNull Email email, @NotNull Cnpj cnpj, @NotNull String senha,
			@NotNull Telefone telefone) {
		this.nomeFantasia = nomeFantasia;
		this.email = email;
		this.cnpj = cnpj;
		this.senha = senha;
		this.telefone = telefone;
	}
	
	@PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
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

}
