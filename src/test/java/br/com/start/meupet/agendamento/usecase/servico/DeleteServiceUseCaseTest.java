package br.com.start.meupet.agendamento.usecase.servico;

import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.agendamento.repository.ServicoPrestadoRepository;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteServiceUseCaseTest {
    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private ServicoPrestadoRepository servicoPrestadoRepository;

    @InjectMocks
    private DeleteServiceUseCase deleteServiceUseCase;

    @Test
    void shouldDeleteServiceSuccessfully() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Long servicoId = 1L;

        Partner partner = new Partner();
        partner.setId(partnerId);
        partner.setName("Partner Test");

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

        // Act
        deleteServiceUseCase.execute(partnerId, servicoId);

        // Assert
        verify(partnerRepository, times(1)).findById(partnerId);
        verify(servicoPrestadoRepository, times(1)).delete(any(ServicoPrestado.class));
    }
    @Test
    void shouldThrowExceptionWhenPartnerNotFound() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Long servicoId = 1L;

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProblemDetailsException.class, () -> deleteServiceUseCase.execute(partnerId, servicoId));

        verify(partnerRepository, times(1)).findById(partnerId);
        verify(servicoPrestadoRepository, never()).delete(any());
    }
}
