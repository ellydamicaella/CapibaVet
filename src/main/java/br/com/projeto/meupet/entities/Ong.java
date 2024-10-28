package br.com.projeto.meupet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String email;

    @Column(name = "cnpj")
    @NotNull
    private String cnpj;

    @Column(name = "senha")
    @NotNull
    private String senha;

    @Column(name = "telefone")
    @NotNull
    private String telefone;

    public Ong(String nomeFantasia, String email, String cnpj, String senha, String telefone) {
        this.nomeFantasia = nomeFantasia;
        this.email = email;
        this.cnpj = cnpj;
        this.senha = senha;
        this.telefone = telefone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
