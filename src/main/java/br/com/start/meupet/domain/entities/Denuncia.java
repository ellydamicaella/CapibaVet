package br.com.start.meupet.domain.entities;

import java.sql.Blob;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "denuncia")
public class Denuncia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "informacao_do_denunciante")
	private String informacaoDoDenunciante;

	@Column(name = "localizacao")
	@NotNull
	private String localizacao;

	@Column(name = "animal_envolvido")
	@NotNull
	private String animalEnvolvido;

	@Column(name = "qnts_animais_envolvidos")
	@NotNull
	private int QtdAnimaisEnvolvidos;

	@Column(name = "descricao")
	@NotNull
	private String descricao;

	@Column(name = "foto")
	private Blob foto;

	@Column(name = "video")
	private Blob video;

	@Column(name = "data_denuncia")
	private LocalDateTime dataDenuncia;

	@Column(name = "id_user")
	private long idUser;

	public Denuncia() {

	}

	public Denuncia(String informacaoDoDenunciante, String localizacao, String animalEnvolvido,
			int qtdAnimaisEnvolvidos, String descricao, Blob foto, Blob video, LocalDateTime dataDenuncia,
			long idUser) {
		this.informacaoDoDenunciante = informacaoDoDenunciante;
		this.localizacao = localizacao;
		this.animalEnvolvido = animalEnvolvido;
		this.QtdAnimaisEnvolvidos = qtdAnimaisEnvolvidos;
		this.descricao = descricao;
		this.foto = foto;
		this.video = video;
		this.dataDenuncia = dataDenuncia;
		this.idUser = idUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInformacaoDoDenunciante() {
		return informacaoDoDenunciante;
	}

	public void setInformacaoDoDenunciante(String informacaoDoDenunciante) {
		this.informacaoDoDenunciante = informacaoDoDenunciante;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getAnimalEnvolvido() {
		return animalEnvolvido;
	}

	public void setAnimalEnvolvido(String animalEnvolvido) {
		this.animalEnvolvido = animalEnvolvido;
	}

	public int getQtdAnimaisEnvolvidos() {
		return QtdAnimaisEnvolvidos;
	}

	public void setQtdAnimaisEnvolvidos(int qtdAnimaisEnvolvidos) {
		QtdAnimaisEnvolvidos = qtdAnimaisEnvolvidos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
	}

	public Blob getVideo() {
		return video;
	}

	public void setVideo(Blob video) {
		this.video = video;
	}

	public LocalDateTime getDataDenuncia() {
		return dataDenuncia;
	}

	public void setDataDenuncia(LocalDateTime dataDenuncia) {
		this.dataDenuncia = dataDenuncia;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

}
