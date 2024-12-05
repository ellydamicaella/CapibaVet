package br.com.start.meupet.agendamento.usecase.disponibilidade;

import br.com.start.meupet.agendamento.dto.disponibilidade.DisponibilidadeRequestDTO;
import br.com.start.meupet.agendamento.model.Disponibilidade;
import br.com.start.meupet.agendamento.repository.DisponibilidadeRepository;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.common.utils.DateTimeUtils;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class AddAvailabilityToPartnerUseCase {

    private final PartnerRepository partnerRepository;
    private final DisponibilidadeRepository disponibilidadeRepository;

    public AddAvailabilityToPartnerUseCase(PartnerRepository partnerRepository, DisponibilidadeRepository disponibilidadeRepository) {
        this.partnerRepository = partnerRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
    }

    public void execute(UUID partnerId, DisponibilidadeRequestDTO disponibilidadeRequest) {
        Optional<Partner> partner = partnerRepository.findById(partnerId);

        if(partner.isEmpty()) {
            throw new ProblemDetailsException("Parceiro nao encontrado", "Parceiro nao existe", HttpStatus.NOT_FOUND);
        }
        log.info("Apagando as antigas disponibilidades do parceiro: {}", partner.get().getName());
        partner.get().getDisponibilidades().clear();

        LocalTime startTime;
        LocalTime endTime;

        try {
            startTime = DateTimeUtils.convertToDateTime(disponibilidadeRequest.openingHour());
            endTime = DateTimeUtils.convertToDateTime(disponibilidadeRequest.closingHour());
            if (endTime.isBefore(startTime)) {
                throw new ProblemDetailsException("Invalid argument", "Horário de fim não pode ser antes do horário de início", HttpStatus.BAD_REQUEST);
            }
        } catch (DateTimeParseException e) {
            throw new ProblemDetailsException("Invalid argument", "Formato de horário inválido. Use HH:mm", HttpStatus.BAD_REQUEST);
        }

//        if (!conflictingDays.isEmpty()) {
//            throw new ProblemDetailsException("Conflicts", String.format("Conflitos detectados nos dias: %s", conflictingDays), HttpStatus.CONFLICT);
//        }

//        List<Disponibilidade> disponibilidades = disponibilidadeRequest.dias().stream().map(dia -> {
            Disponibilidade disponibilidade = new Disponibilidade();
            disponibilidade.setPartner(partner.get());
//            disponibilidade.setDayOfWeek(dia);
            disponibilidade.setStartTime(LocalTime.parse(disponibilidadeRequest.openingHour()));
            disponibilidade.setEndTime(DateTimeUtils.convertToDateTime(disponibilidadeRequest.closingHour()));
//            return disponibilidade;
//        }).toList();

        log.info("Adicionado disponibilidade: {}, ao parceiro: {}", disponibilidade, partner.get().getName());
        disponibilidadeRepository.save(disponibilidade);

    }
}
