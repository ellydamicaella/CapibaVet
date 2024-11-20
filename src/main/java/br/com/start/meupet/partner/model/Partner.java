package br.com.start.meupet.partner.model;

import br.com.start.meupet.common.interfaces.Authenticable;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "parceiro")
@Data
@NoArgsConstructor
public class Partner implements Authenticable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
}
