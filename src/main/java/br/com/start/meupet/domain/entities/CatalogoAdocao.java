package br.com.start.meupet.domain.entities;

import java.sql.Blob;

import br.com.start.meupet.enums.AnimalPorte;
import br.com.start.meupet.enums.AnimalType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "catalogo_adocao")

public class CatalogoAdocao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "nome")
	@NotNull
	private String nome;

	@Column(name = "idade")
	private int idade;

	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	@NotNull
	private AnimalType tipo;

	@Column(name = "porte")
	@Enumerated(EnumType.STRING)
	@NotNull
	private AnimalPorte porte;

	@Column(name = "personalidade")
	private String personalidade;

	@Column(name = "vacinado")
	@NotNull
	private boolean vacinado;

	@Column(name = "castrado")
	@NotNull
	private boolean castrado;

	@Column(name = "adotado")
	@NotNull
	private boolean adotado;

	@Column(name = "foto")
	private Blob foto;

	@Column(name = "localizacao")
	private String localizacao;

	@Column(name = "id_ong")
	@NotNull
	private int idOng;

	@Column(name = "id_user")
	private long idUser;

	public CatalogoAdocao() {

	}

	public CatalogoAdocao(String nome, int idade, AnimalType tipo, AnimalPorte porte, String personalidade,
			boolean vacinado, boolean castrado, boolean adotado, Blob foto, String localizacao, int idOng,
			long idUser) {
		this.nome = nome;
		this.idade = idade;
		this.tipo = tipo;
		this.porte = porte;
		this.personalidade = personalidade;
		this.vacinado = vacinado;
		this.castrado = castrado;
		this.adotado = adotado;
		this.foto = foto;
		this.localizacao = localizacao;
		this.idOng = idOng;
		this.idUser = idUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public AnimalType getTipo() {
		return tipo;
	}

	public void setTipo(AnimalType tipo) {
		this.tipo = tipo;
	}

	public AnimalPorte getPorte() {
		return porte;
	}

	public void setPorte(AnimalPorte porte) {
		this.porte = porte;
	}

	public String getPersonalidade() {
		return personalidade;
	}

	public void setPersonalidade(String personalidade) {
		this.personalidade = personalidade;
	}

	public boolean isVacinado() {
		return vacinado;
	}

	public void setVacinado(boolean vacinado) {
		this.vacinado = vacinado;
	}

	public boolean isCastrado() {
		return castrado;
	}

	public void setCastrado(boolean castrado) {
		this.castrado = castrado;
	}

	public boolean isAdotado() {
		return adotado;
	}

	public void setAdotado(boolean adotado) {
		this.adotado = adotado;
	}

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
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

}
