package br.com.start.meupet.agendamento.usecase.disponibilidade;

import br.com.start.meupet.agendamento.dto.disponibilidade.PartnerDisponibilidadeDTO;
import br.com.start.meupet.agendamento.model.Disponibilidade;
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

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetPartnerAndAvailabilitiesUseCaseTest {
    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private GetPartnerAndAvailabilitiesUseCase getPartnerAndAvailabilitiesUseCase;

    @Test
    void shouldReturnPartnerAndAvailabilitiesSuccessfully() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Partner mockPartner = new Partner();
        mockPartner.setId(partnerId);
        mockPartner.setName("John Partner");
        mockPartner.setEmail(new Email("aaaaa@gmail.com"));
        mockPartner.setPassword("321312312");
        mockPartner.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        mockPartner.setPersonalRegistration(new PersonalRegistration("11.111.111/1111-11", DocumentType.CNPJ));

        Disponibilidade disponibilidade1 = new Disponibilidade();
        disponibilidade1.setId(1L);
        disponibilidade1.setStartTime(LocalTime.of(8, 0));
        disponibilidade1.setEndTime(LocalTime.of(17, 0));

        Disponibilidade disponibilidade2 = new Disponibilidade();
        disponibilidade2.setId(2L);
        disponibilidade2.setStartTime(LocalTime.of(18, 0));
        disponibilidade2.setEndTime(LocalTime.of(22, 0));

        mockPartner.setDisponibilidades(List.of(disponibilidade1, disponibilidade2));

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(mockPartner));

        // Act
        PartnerDisponibilidadeDTO result = getPartnerAndAvailabilitiesUseCase.execute(partnerId);

        // Assert
        assertNotNull(result);
        assertEquals("John Partner", result.getName());
        assertEquals(2, result.getDisponibilidades().size());
        assertEquals(LocalTime.of(8, 0), result.getDisponibilidades().get(0).openingHour());
        assertEquals(LocalTime.of(17, 0), result.getDisponibilidades().get(0).closingHour());
        assertEquals(LocalTime.of(18, 0), result.getDisponibilidades().get(1).openingHour());
        assertEquals(LocalTime.of(22, 0), result.getDisponibilidades().get(1).closingHour());

        verify(partnerRepository, times(1)).findById(partnerId);
    }

    @Test
    void shouldThrowExceptionWhenPartnerNotFound() {
        // Arrange
        UUID partnerId = UUID.randomUUID();

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        // Act & Assert
        ProblemDetailsException exception = assertThrows(ProblemDetailsException.class, () -> {
            getPartnerAndAvailabilitiesUseCase.execute(partnerId);
        });

        assertEquals("Parceiro nao encontrado", exception.getMessage());
        verify(partnerRepository, times(1)).findById(partnerId);
    }

}
