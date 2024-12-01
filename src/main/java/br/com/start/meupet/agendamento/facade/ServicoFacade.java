package br.com.start.meupet.agendamento.facade;

import br.com.start.meupet.agendamento.dto.servico.PartnerServicoDTO;
import br.com.start.meupet.agendamento.dto.servico.ServicoPrestadoRequestDTO;
import br.com.start.meupet.agendamento.usecase.servico.*;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ServicoFacade {

    private final ListAllServicesAndPartnersUseCase listAllServicesAndPartnersUseCase;
    private final GetServicesAndPartnerUseCase getServicesAndPartnerUseCase;
    private final AddServiceToPartnerUseCase addServiceToPartnerUseCase;
    private final UpdateServiceUseCase updateServiceUseCase;
    private final DeleteServiceUseCase deleteServiceUseCase;

    public ServicoFacade(ListAllServicesAndPartnersUseCase listAllServicesAndPartnersUseCase, GetServicesAndPartnerUseCase getServicesAndPartnerUseCase, AddServiceToPartnerUseCase addServiceToPartnerUseCase, UpdateServiceUseCase updateServiceUseCase, DeleteServiceUseCase deleteServiceUseCase) {
        this.listAllServicesAndPartnersUseCase = listAllServicesAndPartnersUseCase;
        this.getServicesAndPartnerUseCase = getServicesAndPartnerUseCase;
        this.addServiceToPartnerUseCase = addServiceToPartnerUseCase;
        this.updateServiceUseCase = updateServiceUseCase;
        this.deleteServiceUseCase = deleteServiceUseCase;
    }

    public List<PartnerServicoDTO> listaParceirosESeusServicos() {
        return listAllServicesAndPartnersUseCase.execute();
    }

    public PartnerServicoDTO listaParceiroESeusServicos(UUID partnerId) {
        return getServicesAndPartnerUseCase.execute(partnerId);
    }

    public void adidionaServicoAoParceiro(UUID partnerId, ServicoPrestadoRequestDTO servicoPrestado) {
        addServiceToPartnerUseCase.execute(partnerId, servicoPrestado);
    }

    public void atualizaServico(UUID partnerId, Long servicoId, ServicoPrestadoRequestDTO servicoPrestadoDTO) {
        updateServiceUseCase.execute(partnerId, servicoId, servicoPrestadoDTO);
    }

    public void deletaServico(UUID partnerId, Long servicoId) {
        deleteServiceUseCase.execute(partnerId, servicoId);
    }
}
