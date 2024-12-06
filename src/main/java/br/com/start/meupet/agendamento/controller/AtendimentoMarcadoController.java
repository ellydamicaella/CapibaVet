package br.com.start.meupet.agendamento.controller;

import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoDTO;
import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoRequestDTO;
import br.com.start.meupet.agendamento.enums.AtendimentoStatus;
import br.com.start.meupet.agendamento.model.Animal;
import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.agendamento.repository.AnimalRepository;
import br.com.start.meupet.agendamento.repository.AtendimentoMarcadoRepository;
import br.com.start.meupet.agendamento.repository.ServicoPrestadoRepository;
import br.com.start.meupet.agendamento.usecase.animal.AddNewAnimalToUserUseCase;
import br.com.start.meupet.auth.dto.StatusResponseDTO;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/agendamento/atendimento")
@CrossOrigin
@Slf4j
public class AtendimentoMarcadoController {

    private final AtendimentoMarcadoRepository atendimentoMarcadoRepository;
    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final ServicoPrestadoRepository servicoPrestadoRepository;
    private final PartnerRepository partnerRepository;
    private final AddNewAnimalToUserUseCase addNewAnimalToUserUseCase;

    public AtendimentoMarcadoController(AtendimentoMarcadoRepository atendimentoMarcadoRepository, UserRepository userRepository, AnimalRepository animalRepository, ServicoPrestadoRepository servicoPrestadoRepository, PartnerRepository partnerRepository, AddNewAnimalToUserUseCase addNewAnimalToUserUseCase) {
        this.atendimentoMarcadoRepository = atendimentoMarcadoRepository;
        this.userRepository = userRepository;
        this.animalRepository = animalRepository;
        this.servicoPrestadoRepository = servicoPrestadoRepository;
        this.partnerRepository = partnerRepository;
        this.addNewAnimalToUserUseCase = addNewAnimalToUserUseCase;
    }

    //usado pelas clinicas para mostrar os dias, e horarios disponiveis para atendimento
//    @PostMapping("/partner/disponibilidade/{partnerId}")
//    public ResponseEntity<List<Disponibilidade>> adicionaDisponibilidade(@PathVariable UUID partnerId, @RequestBody DisponibilidadeRequestDTO disponibilidadeRequest) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.saveDisponibilidades(partnerId, disponibilidadeRequest));
//    }

    @GetMapping
    public ResponseEntity<List<AtendimentoMarcadoDTO>> listaAtendimentosMarcados() {
        List<AtendimentoMarcado> atendimentoMarcados = atendimentoMarcadoRepository.findAll();
        List<AtendimentoMarcadoDTO> atendimentoMarcadoDTO = new ArrayList<>();
        atendimentoMarcados.forEach(atendimento -> {
            atendimentoMarcadoDTO.add(new AtendimentoMarcadoDTO(atendimento, atendimento.getPartner(), atendimento.getUser(), atendimento.getServicoPrestado(), atendimento.getAnimal()));
        });
        return ResponseEntity.ok().body(atendimentoMarcadoDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AtendimentoMarcadoDTO>> listaAtendimentoMarcado(@PathVariable UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Busca os atendimentos marcados associados ao usuário
        List<AtendimentoMarcado> atendimentosMarcados = atendimentoMarcadoRepository.findByUser(user);

        // Converte os atendimentos marcados para DTOs
        List<AtendimentoMarcadoDTO> atendimentosDTO = atendimentosMarcados.stream()
                .map(atendimento -> new AtendimentoMarcadoDTO(
                        atendimento,
                        atendimento.getPartner(),
                        atendimento.getUser(),
                        atendimento.getServicoPrestado(),
                        atendimento.getAnimal()
                ))
                .toList();
        return ResponseEntity.ok().body(atendimentosDTO);
    }

    @PostMapping
    public ResponseEntity<StatusResponseDTO> adicionaAtendimentoMarcado(@RequestBody AtendimentoMarcadoRequestDTO request) {
        try {
            // Valida se as entidades existem

            log.info("serviceId, {}", request.serviceId());
            Partner partner = partnerRepository.findById(request.partnerId())
                    .orElseThrow(() -> new EntityNotFoundException("Parceiro não encontrado"));
            ServicoPrestado servico = servicoPrestadoRepository.findById(request.serviceId())
                    .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));
            User user = userRepository.findById(request.userId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            Animal animal = addNewAnimalToUserUseCase.execute(request.userId(), request.animal());

            // Valida conflitos de horários
            boolean hasConflict = atendimentoMarcadoRepository.existsConflict(
                    partner.getId(), request.appointmentDate(), request.startTime(), request.endTime());

            if (hasConflict) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new StatusResponseDTO("error", "Já existe um atendimento marcado para esse horário."));
                //throw new BusinessException("Já existe um atendimento marcado para esse horário.");
            }

            // Cria o atendimento
            AtendimentoMarcado atendimento = new AtendimentoMarcado();
            atendimento.setPartner(partner);
            atendimento.setServicoPrestado(servico);
            atendimento.setUser(user);
            atendimento.setAnimal(animal);
            atendimento.setAppointmentDate(request.appointmentDate());
            atendimento.setStartTime(request.startTime());
            atendimento.setEndTime(request.endTime());
            atendimento.setStatus(AtendimentoStatus.PENDENTE);

            // Salva no banco de dados
            atendimentoMarcadoRepository.save(atendimento);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new StatusResponseDTO("success", "Atendimento marcado com sucesso!"));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StatusResponseDTO("error", e.getMessage()));
        }
        //catch (BusinessException e) {
        //    return ResponseEntity.status(HttpStatus.CONFLICT)
        //            .body(new StatusResponseDTO("error", e.getMessage()));
        //}
    }

    @PutMapping("/{partnerId}/{atendimentoMarcadoId}")
    public ResponseEntity<StatusResponseDTO> alteraStatusDeAtendimentoMarcado(@PathVariable UUID partnerId, @PathVariable Long atendimentoMarcadoId, @RequestParam String status) {
        AtendimentoStatus novoStatus;
        try {
            novoStatus = AtendimentoStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StatusResponseDTO("error", "Status inválido. Use PENDENTE, CANCELADO ou CONFIRMADO."));
        }

        // Verifica se o atendimento existe
        AtendimentoMarcado atendimentoMarcado = atendimentoMarcadoRepository.findById(atendimentoMarcadoId)
                .orElse(null);

        if (atendimentoMarcado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StatusResponseDTO("error", "Atendimento não encontrado."));
        }

        // Verifica se o parceiro é o dono do atendimento
        if (!atendimentoMarcado.getPartner().getId().equals(partnerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new StatusResponseDTO("error", "Você não tem permissão para alterar este atendimento."));
        }

        // Atualiza o status do atendimento
        atendimentoMarcado.setStatus(novoStatus);
        atendimentoMarcadoRepository.save(atendimentoMarcado);

        return ResponseEntity.ok(new StatusResponseDTO("success", "Status do atendimento atualizado com sucesso."));
    }


}
