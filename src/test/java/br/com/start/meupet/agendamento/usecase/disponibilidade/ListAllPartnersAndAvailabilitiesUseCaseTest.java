package br.com.start.meupet.agendamento.usecase.disponibilidade;

import br.com.start.meupet.partner.repository.PartnerRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ListAllPartnersAndAvailabilitiesUseCaseTest {
    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private ListAllPartnersAndAvailabilitiesUseCase listAllPartnersAndAvailabilitiesUseCase;


}
