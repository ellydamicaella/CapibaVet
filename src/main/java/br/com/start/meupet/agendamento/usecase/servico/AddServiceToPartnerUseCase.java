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
public class AddServiceToPartnerUseCase {

    private final PartnerRepository partnerRepository;
    private final ServicoPrestadoRepository servicoPrestadoRepository;

    public AddServiceToPartnerUseCase(PartnerRepository partnerRepository, ServicoPrestadoRepository servicoPrestadoRepository) {
        this.partnerRepository = partnerRepository;
        this.servicoPrestadoRepository = servicoPrestadoRepository;
    }

    public void execute(UUID partnerId, ServicoPrestadoRequestDTO servicoPrestado) {
        Optional<Partner> partner = partnerRepository.findById(partnerId);
        ServicoPrestado servico = new ServicoPrestado();
        if(partner.isPresent()) {
            servico.setName(ServicoType.valueOf(servicoPrestado.name().toUpperCase()));
            servico.setPrice(servicoPrestado.price());
            servico.setPartner(partner.get());
            servicoPrestadoRepository.save(servico);
        } else {
            throw new ProblemDetailsException("Parceiro nao encontrado", "Parceiro nao existe", HttpStatus.NOT_FOUND);
        }
    }
}
