package br.com.start.meupet.partner.model;

import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.model.Disponibilidade;
import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.auth.interfaces.Authenticable;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

    @Column(name = "rua")
    private String street;

    @Column(name = "numeroResidencial")
    private String houseNumber;

    @Column(name = "cidade")
    private String city;

    @Column(name = "bairro")
    private String neighborhood;

    @Column
    private String CEP;

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    @Lob
    private byte[] profileImage;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ServicoPrestado> servicoPrestados;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Disponibilidade> disponibilidades;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AtendimentoMarcado> atendimentoMarcados;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public Partner(
            String name,
            Email email,
            String password,
            PhoneNumber phoneNumber,
            PersonalRegistration personalRegistration
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.personalRegistration = personalRegistration;
    }

    public Partner(
            UUID id,
            String name,
            Email email,
            String password,
            PhoneNumber phoneNumber,
            PersonalRegistration personalRegistration
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
