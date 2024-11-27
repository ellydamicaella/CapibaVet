package br.com.start.meupet.agendamento.model;

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
    @JoinColumn(name = "id_partner", nullable = false)
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "id_service", nullable = false)
    private ServicoPrestado service;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
}
