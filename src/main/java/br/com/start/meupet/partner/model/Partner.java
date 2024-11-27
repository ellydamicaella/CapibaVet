package br.com.start.meupet.partner.model;

import br.com.start.meupet.auth.interfaces.Authenticable;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Cacheable
@Entity
@Table(name = "parceiro")
@Data
@NoArgsConstructor
public class Partner implements Authenticable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "email")
    @NotNull
    private Email email;


    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "phone_number")
    @NotNull
    @Embedded
    private PhoneNumber phoneNumber;

    private PersonalRegistration personalRegistration;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public Partner(
                   @NotNull String name,
                   @NotNull Email email,
                   @NotNull String password,
                   @NotNull PhoneNumber phoneNumber,
                   @NotNull PersonalRegistration personalRegistration
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.personalRegistration = personalRegistration;
    }

    public Partner(
            @NotNull UUID id,
            @NotNull String name,
            @NotNull Email email,
            @NotNull String password,
            @NotNull PhoneNumber phoneNumber,
            @NotNull PersonalRegistration personalRegistration
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.personalRegistration = personalRegistration;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
}
