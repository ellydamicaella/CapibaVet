package br.com.start.meupet.agendamento.dto.atendimento;

import br.com.start.meupet.agendamento.dto.animal.AnimalAtendimentoDTO;
import br.com.start.meupet.agendamento.dto.servico.ServicoAtendimentoDTO;
import br.com.start.meupet.agendamento.enums.AtendimentoStatus;
import br.com.start.meupet.agendamento.model.Animal;
import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.partner.dto.PartnerAtendimentoDTO;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.user.dto.UserAtendimentoDTO;
import br.com.start.meupet.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtendimentoMarcadoDTO {
    private Long id;
    private LocalDate dataAgendamento;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private AtendimentoStatus status;
    private PartnerAtendimentoDTO partner;
    private UserAtendimentoDTO user;
    private ServicoAtendimentoDTO servico;
    private AnimalAtendimentoDTO animal;

    public AtendimentoMarcadoDTO(AtendimentoMarcado atendimentoMarcado, Partner partner, User user, ServicoPrestado servicoPrestado, Animal animal) {
        this.id = atendimentoMarcado.getId();
        this.dataAgendamento = atendimentoMarcado.getAppointmentDate();
        this.horaInicio = atendimentoMarcado.getStartTime();
        this.horaFim = atendimentoMarcado.getEndTime();
        this.status = atendimentoMarcado.getStatus();
        this.partner = new PartnerAtendimentoDTO(partner);
        this.user = new UserAtendimentoDTO(user);
        this.servico = new ServicoAtendimentoDTO(servicoPrestado);
        this.animal = new AnimalAtendimentoDTO(animal);
    }
}
