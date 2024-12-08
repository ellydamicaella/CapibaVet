package br.com.start.meupet.agendamento.usecase.servico;

import br.com.start.meupet.agendamento.repository.ServicoPrestadoRepository;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddServiceToPartnerUseCaseTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private ServicoPrestadoRepository servicoPrestadoRepository;

    @InjectMocks
    private AddServiceToPartnerUseCase addServiceToPartnerUseCase;



}
