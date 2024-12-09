package br.com.start.meupet.agendamento.usecase.disponibilidade;

import br.com.start.meupet.agendamento.dto.disponibilidade.DisponibilidadeRequestDTO;
import br.com.start.meupet.agendamento.model.Disponibilidade;
import br.com.start.meupet.agendamento.repository.DisponibilidadeRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddAvailabilityToPartnerUseCaseTest {
    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private DisponibilidadeRepository disponibilidadeRepository;

    @InjectMocks
    private AddAvailabilityToPartnerUseCase addAvailabilityToPartnerUseCase;

    @Test
    void shouldAddAvailabilityToPartnerSuccessfully() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Partner mockPartner = new Partner();
        mockPartner.setId(partnerId);
        mockPartner.setName("John Partner");
        mockPartner.setDisponibilidades(new ArrayList<>());

        DisponibilidadeRequestDTO request = new DisponibilidadeRequestDTO("08:00", "17:00");

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(mockPartner));

        // Act
        addAvailabilityToPartnerUseCase.execute(partnerId, request);

        // Assert
        verify(partnerRepository, times(1)).findById(partnerId);
        verify(disponibilidadeRepository, times(1)).save(any(Disponibilidade.class));
        assertTrue(mockPartner.getDisponibilidades().isEmpty(), "Disponibilidades anteriores devem ser removidas");
    }
    @Test
    void shouldThrowExceptionWhenPartnerNotFound() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        DisponibilidadeRequestDTO request = new DisponibilidadeRequestDTO("08:00", "17:00");

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        // Act & Assert
        ProblemDetailsException exception = assertThrows(ProblemDetailsException.class, () -> {
            addAvailabilityToPartnerUseCase.execute(partnerId, request);
        });

        assertEquals("Parceiro nao encontrado", exception.getMessage());
        verify(partnerRepository, times(1)).findById(partnerId);
        verify(disponibilidadeRepository, never()).save(any(Disponibilidade.class));
    }
    @Test
    void shouldThrowExceptionWhenEndTimeIsBeforeStartTime() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Partner mockPartner = getPartner(partnerId);

        DisponibilidadeRequestDTO request = new DisponibilidadeRequestDTO("17:00", "08:00");

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(mockPartner));

        // Act & Assert
        ProblemDetailsException exception = assertThrows(ProblemDetailsException.class, () -> {
            addAvailabilityToPartnerUseCase.execute(partnerId, request);
        });

        assertEquals("Invalid argument", exception.getMessage());
        verify(partnerRepository, times(1)).findById(partnerId);
        verify(disponibilidadeRepository, never()).save(any(Disponibilidade.class));
    }

    @Test
    void shouldThrowExceptionWhenTimeFormatIsInvalid() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Partner mockPartner = getPartner(partnerId);

        DisponibilidadeRequestDTO request = new DisponibilidadeRequestDTO("invalid", "17:00");

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(mockPartner));

        // Act & Assert
        ProblemDetailsException exception = assertThrows(ProblemDetailsException.class, () -> {
            addAvailabilityToPartnerUseCase.execute(partnerId, request);
        });

        assertEquals("Invalid argument", exception.getMessage());
        verify(partnerRepository, times(1)).findById(partnerId);
        verify(disponibilidadeRepository, never()).save(any(Disponibilidade.class));
    }

    private Partner getPartner(UUID partnerId) {
        Partner mockPartner = new Partner();
        mockPartner.setId(partnerId);
        mockPartner.setName("John Partner");
        mockPartner.setEmail(new Email("aaaaa@gmail.com"));
        mockPartner.setPassword("321312312");
        mockPartner.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        mockPartner.setPersonalRegistration(new PersonalRegistration("11.111.111/1111-11", DocumentType.CNPJ));
        List<Disponibilidade> disponibilidades = new ArrayList<>();
        mockPartner.setDisponibilidades(disponibilidades);
        return mockPartner;
    }
}
