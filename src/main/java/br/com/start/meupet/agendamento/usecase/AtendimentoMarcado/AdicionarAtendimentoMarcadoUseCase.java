package br.com.start.meupet.agendamento.usecase.AtendimentoMarcado;

import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoRequestDTO;
import br.com.start.meupet.agendamento.enums.AtendimentoStatus;
import br.com.start.meupet.agendamento.model.Animal;
import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.agendamento.repository.AtendimentoMarcadoRepository;
import br.com.start.meupet.agendamento.repository.ServicoPrestadoRepository;
import br.com.start.meupet.agendamento.usecase.animal.AddNewAnimalToUserUseCase;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.exceptions.SchedulingConflictException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdicionarAtendimentoMarcadoUseCase {

    private final AtendimentoMarcadoRepository atendimentoMarcadoRepository;
    private final PartnerRepository partnerRepository;
    private final ServicoPrestadoRepository servicoPrestadoRepository;
    private final UserRepository userRepository;
    private final AddNewAnimalToUserUseCase addNewAnimalToUserUseCase;

    public AdicionarAtendimentoMarcadoUseCase(AtendimentoMarcadoRepository atendimentoMarcadoRepository, PartnerRepository partnerRepository, ServicoPrestadoRepository servicoPrestadoRepository, UserRepository userRepository, AddNewAnimalToUserUseCase addNewAnimalToUserUseCase) {
        this.atendimentoMarcadoRepository = atendimentoMarcadoRepository;
        this.partnerRepository = partnerRepository;
        this.servicoPrestadoRepository = servicoPrestadoRepository;
        this.userRepository = userRepository;
        this.addNewAnimalToUserUseCase = addNewAnimalToUserUseCase;
    }

    public void execute(AtendimentoMarcadoRequestDTO atendimentoMarcado) {
        // Valida se as entidades existem

        log.info("serviceId, {}", atendimentoMarcado.serviceId());
        Partner partner = partnerRepository.findById(atendimentoMarcado.partnerId())
                .orElseThrow(() -> new EntityNotFoundException("Parceiro não encontrado"));
        ServicoPrestado servico = servicoPrestadoRepository.findById(atendimentoMarcado.serviceId())
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));
        User user = userRepository.findById(atendimentoMarcado.userId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Animal animal = addNewAnimalToUserUseCase.execute(atendimentoMarcado.userId(), atendimentoMarcado.animal());

        // Valida conflitos de horários
        boolean hasConflict = atendimentoMarcadoRepository.existsConflict(
                partner.getId(), atendimentoMarcado.appointmentDate(), atendimentoMarcado.startTime(), atendimentoMarcado.endTime());

        if (hasConflict) {
            throw new SchedulingConflictException("Já existe um atendimento marcado para esse horário.");
        }

        // Cria o atendimento
        AtendimentoMarcado atendimento = new AtendimentoMarcado();
        atendimento.setPartner(partner);
        atendimento.setServicoPrestado(servico);
        atendimento.setUser(user);
        atendimento.setAnimal(animal);
        atendimento.setAppointmentDate(atendimentoMarcado.appointmentDate());
        atendimento.setStartTime(atendimentoMarcado.startTime());
        atendimento.setEndTime(atendimentoMarcado.endTime());
        atendimento.setStatus(AtendimentoStatus.PENDENTE);

        // Salva no banco de dados
        atendimentoMarcadoRepository.save(atendimento);
    }

}