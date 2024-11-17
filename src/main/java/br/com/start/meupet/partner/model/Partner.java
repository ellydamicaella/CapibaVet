package br.com.start.meupet.domain.entities;

import br.com.start.meupet.domain.interfaces.Authenticable;
import br.com.start.meupet.domain.interfaces.PersonalRegistration;
import br.com.start.meupet.domain.valueobjects.Cnpj;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "parceiro")
@Data
@NoArgsConstructor
public class Partner implements Authenticable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

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

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "phone_number")
    @NotNull
    @Embedded
    private PhoneNumber phoneNumber;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

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
}
