package br.com.start.meupet.domain.entities;

import java.sql.Blob;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "resgate")
public class Resgate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "foto")
	private Blob foto;

	@Column(name = "situacao")
	@NotNull
	private String situacao;

	@Column(name = "localizacao")
	@NotNull
	private String localizacao;

	@Column(name = "id_ong")
	private int idOng;

	@Column(name = "id_user")
	private long idUser;

	@Column(name = "data_resgate")
	private LocalDateTime dataResgate;

	@Column(name = "data_chamado")
	@NotNull
	private LocalDateTime createdAt;

	public Resgate() {

	}

	public Resgate(Blob foto, @NotNull String situacao, @NotNull String localizacao, int idOng, long idUser,
			LocalDateTime dataResgate, @NotNull LocalDateTime createdAt) {
		this.foto = foto;
		this.situacao = situacao;
		this.localizacao = localizacao;
		this.idOng = idOng;
		this.idUser = idUser;
		this.dataResgate = dataResgate;
		this.createdAt = createdAt;
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

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public int getIdOng() {
		return idOng;
	}

	public void setIdOng(int idOng) {
		this.idOng = idOng;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public LocalDateTime getDataResgate() {
		return dataResgate;
	}

	public void setDataResgate(LocalDateTime dataResgate) {
		this.dataResgate = dataResgate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
