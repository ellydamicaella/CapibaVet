package br.com.start.meupet.agendamento.model;

import br.com.start.meupet.agendamento.enums.AtendimentoStatus;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
@Getter
@Setter
public class AtendimentoMarcado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_partner", referencedColumnName = "id", nullable = false)
    private Partner partner;

    @OneToOne
    @JoinColumn(name = "id_service", referencedColumnName = "id", nullable = false)
    private ServicoPrestado servicoPrestado;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_animal", referencedColumnName = "id", nullable = false)
    private Animal animal;

    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private AtendimentoStatus status;
}
