package br.com.start.meupet.agendamento.services;

import br.com.start.meupet.agendamento.dto.DisponibilidadeRequestDTO;
import br.com.start.meupet.agendamento.model.Disponibilidade;
import br.com.start.meupet.agendamento.repositories.DisponibilidadeRepository;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AgendamentoService {

    private final PartnerRepository partnerRepository;
    private final DisponibilidadeRepository disponibilidadeRepository;

    public AgendamentoService(PartnerRepository partnerRepository, DisponibilidadeRepository disponibilidadeRepository) {
        this.partnerRepository = partnerRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
    }

    @Transactional
    public List<Disponibilidade> saveDisponibilidades(UUID partnerId, DisponibilidadeRequestDTO disponibilidadesRequest) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parceiro não encontrado"));

        List<Disponibilidade> disponibilidades = disponibilidadesRequest.dias().stream()
                .map(dia -> {
                    Disponibilidade disponibilidade = new Disponibilidade();
                    disponibilidade.setPartner(partner);
                    disponibilidade.setDayOfWeek(DayOfWeek.valueOf(dia.toUpperCase())); // Certifique-se de que o dia está em formato válido
                    disponibilidade.setStartTime(disponibilidadesRequest.horaDeInicio());
                    disponibilidade.setEndTime(disponibilidadesRequest.horaDeFim());
                    return disponibilidade;
                })
                .toList();

        return disponibilidadeRepository.saveAll(disponibilidades);
    }
}
