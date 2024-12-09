package br.com.start.meupet.agendamento.usecase.servico;

import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.agendamento.repository.ServicoPrestadoRepository;
import br.com.start.meupet.auth.dto.StatusResponseDTO;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DeleteServiceUseCase {

    private final PartnerRepository partnerRepository;
    private final ServicoPrestadoRepository servicoPrestadoRepository;

    public DeleteServiceUseCase(PartnerRepository partnerRepository, ServicoPrestadoRepository servicoPrestadoRepository) {
        this.partnerRepository = partnerRepository;
        this.servicoPrestadoRepository = servicoPrestadoRepository;
    }

    public void execute(UUID partnerId, Long servicoId) {
        Optional<Partner> partnerOpt = partnerRepository.findById(partnerId);
        if (partnerOpt.isEmpty()) {
            throw new ProblemDetailsException("Parceiro nao encontrado", "Parceiro nao existe", HttpStatus.NOT_FOUND);
        }
        Partner partner = partnerOpt.get();
        ServicoPrestado servico = new ServicoPrestado();
        servico.setId(servicoId);
        //servico.setPartner(partner);
        servicoPrestadoRepository.delete(servico);
    }
}
