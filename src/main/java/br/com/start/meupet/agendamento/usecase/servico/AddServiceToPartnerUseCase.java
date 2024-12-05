package br.com.start.meupet.agendamento.usecase.servico;

import br.com.start.meupet.agendamento.dto.servico.ServicoPrestadoRequestDTO;
import br.com.start.meupet.agendamento.enums.ServicoType;
import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.agendamento.repository.ServicoPrestadoRepository;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class AddServiceToPartnerUseCase {

    private final PartnerRepository partnerRepository;
    private final ServicoPrestadoRepository servicoPrestadoRepository;

    public AddServiceToPartnerUseCase(PartnerRepository partnerRepository, ServicoPrestadoRepository servicoPrestadoRepository) {
        this.partnerRepository = partnerRepository;
        this.servicoPrestadoRepository = servicoPrestadoRepository;
    }

    public void execute(UUID partnerId, List<ServicoPrestadoRequestDTO> servicos) {
        Optional<Partner> optionalPartner = partnerRepository.findById(partnerId);
        Partner partner = optionalPartner.orElseThrow(() -> new EntityNotFoundException("Parceiro não encontrado"));
        partner.getServicoPrestados().clear();
        log.info("Todos os serviços anteriores foram removidos para o parceiro: {}", partner.getName());
        log.info("{}", partner);

        for (ServicoPrestadoRequestDTO servicoDTO : servicos) {
                ServicoPrestado newServico = new ServicoPrestado();
                newServico.setName(ServicoType.valueOf(servicoDTO.name().toUpperCase()));
                newServico.setPrice(servicoDTO.price());
                newServico.setPartner(partner);

                servicoPrestadoRepository.save(newServico);

                log.info("Serviço {} com preço {} adicionado ao parceiro {}", servicoDTO.name(), servicoDTO.price(), partner.getName());
                partnerRepository.save(partner);
        }
    }
}
