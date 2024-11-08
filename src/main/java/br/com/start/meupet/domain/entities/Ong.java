package br.com.start.meupet.domain.entities;

import br.com.start.meupet.domain.interfaces.Authenticable;
import br.com.start.meupet.domain.valueobjects.Cnpj;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private String password;

    @Column(name = "telefone")
    @NotNull
    @Embedded
    private PhoneNumber phoneNumber;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    public Ong() {

    }

    public Ong(int id, @NotNull String name, @NotNull Email email, @NotNull Cnpj cnpj, @NotNull String password,
               @NotNull PhoneNumber phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cnpj = cnpj;
        this.password = password;
        this.phoneNumber = phoneNumber;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        Ong other = (Ong) obj;
        return id == other.id;
    }

}
