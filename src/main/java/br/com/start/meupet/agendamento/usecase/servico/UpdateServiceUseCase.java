package br.com.start.meupet.agendamento.usecase.servico;

import br.com.start.meupet.agendamento.dto.servico.ServicoPrestadoRequestDTO;
import br.com.start.meupet.agendamento.enums.ServicoType;
import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.agendamento.repository.ServicoPrestadoRepository;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UpdateServiceUseCase {

    private final PartnerRepository partnerRepository;
    private final ServicoPrestadoRepository servicoPrestadoRepository;

    public UpdateServiceUseCase(PartnerRepository partnerRepository, ServicoPrestadoRepository servicoPrestadoRepository) {
        this.partnerRepository = partnerRepository;
        this.servicoPrestadoRepository = servicoPrestadoRepository;
    }

    public void execute(UUID partnerId, Long servicoId, ServicoPrestadoRequestDTO servicoPrestadoDTO) {

        Optional<Partner> partnerOpt = partnerRepository.findById(partnerId);

        if (partnerOpt.isEmpty()) {
            throw new ProblemDetailsException("Parceiro nao encontrado", "Parceiro nao existe", HttpStatus.NOT_FOUND);
        }

        Partner partner = partnerOpt.get();

        ServicoPrestado servico = partner.getServicoPrestados().stream()
                .filter(s -> s.getId().equals(servicoId))
                .findFirst()
                .orElse(null);

        if (servico == null) {
            throw new ProblemDetailsException("Servico nao encontrado", "Servico nao existe", HttpStatus.NOT_FOUND);
        }

        servico.setName(ServicoType.valueOf(servicoPrestadoDTO.name().toUpperCase()));
        servico.setPrice(servicoPrestadoDTO.price());

        servicoPrestadoRepository.save(servico);

    }
}
