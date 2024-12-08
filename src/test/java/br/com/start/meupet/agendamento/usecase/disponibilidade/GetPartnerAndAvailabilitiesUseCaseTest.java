package br.com.start.meupet.agendamento.usecase.disponibilidade;

import br.com.start.meupet.partner.repository.PartnerRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetPartnerAndAvailabilitiesUseCaseTest {
    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private GetPartnerAndAvailabilitiesUseCase getPartnerAndAvailabilitiesUseCase;



}
