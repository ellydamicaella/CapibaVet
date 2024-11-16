package br.com.start.meupet.domain.entities;

import br.com.start.meupet.domain.interfaces.Authenticable;
import br.com.start.meupet.domain.interfaces.PersonalRegistration;
import br.com.start.meupet.domain.valueobjects.Cnpj;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "parceiro")
public class Partner implements Authenticable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome_fantasia")
    @NotNull
    private String name;

    @Column(name = "email")
    @NotNull
    private Email email;

    @Column(name = "registro_pessoal")
    @NotNull
    @Embedded
    private PersonalRegistration personalRegistration;

    @Column(name = "senha")
    @NotNull
    private String password;

    @Column(name = "telefone")
    @NotNull
    @Embedded
    private PhoneNumber phoneNumber;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public Partner() {

    }

    public Partner(
                   @NotNull String name,
                   @NotNull Email email,
                   @NotNull PersonalRegistration personalRegistration,
                   @NotNull String password,
                   @NotNull PhoneNumber phoneNumber) {
        this.name = name;
        this.email = email;
        this.personalRegistration = personalRegistration;
        this.password = password;
        this.phoneNumber = phoneNumber;
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

    public @NotNull PersonalRegistration getPersonalRegistration() {
        return personalRegistration;
    }

    public void setPersonalRegistration(@NotNull PersonalRegistration personalRegistration) {
        this.personalRegistration = personalRegistration;
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
        Partner other = (Partner) obj;
        return id == other.id;
    }

}
