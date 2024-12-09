package br.com.start.meupet.agendamento.usecase.servico;

import br.com.start.meupet.agendamento.dto.servico.PartnerServicoDTO;
import br.com.start.meupet.agendamento.enums.ServicoType;
import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetServicesAndPartnerUseCaseTest {
    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private GetServicesAndPartnerUseCase getServicesAndPartnerUseCase;

    @Test
    void shouldReturnPartnerWithServicesSuccessfully() {
        // Arrange
        UUID partnerId = UUID.randomUUID();

        Partner partner = new Partner();
        partner.setId(partnerId);
        partner.setName("Partner Test");
        partner.setEmail(new Email("aaaaa@gmail.com"));
        partner.setPassword("321312312");
        partner.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        partner.setPersonalRegistration(new PersonalRegistration("11111111111", DocumentType.CPF));

        ServicoPrestado service1 = new ServicoPrestado();
        service1.setName(ServicoType.CASTRACAO);
        service1.setPrice("50.0");

        ServicoPrestado service2 = new ServicoPrestado();
        service2.setName(ServicoType.VACINAS);
        service2.setPrice("150.0");

        partner.setServicoPrestados(List.of(service1, service2));

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

        // Act
        PartnerServicoDTO result = getServicesAndPartnerUseCase.execute(partnerId);

        // Assert
        assertEquals(partner.getId(), result.getId());
        assertEquals(partner.getName(), result.getName());
        assertEquals(2, result.getServicoPrestados().size());
        verify(partnerRepository, times(1)).findById(partnerId);
    }
    @Test
    void shouldThrowExceptionWhenPartnerNotFound() {
        // Arrange
        UUID partnerId = UUID.randomUUID();

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProblemDetailsException.class, () -> getServicesAndPartnerUseCase.execute(partnerId));
        verify(partnerRepository, times(1)).findById(partnerId);
    }
}
