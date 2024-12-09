package br.com.start.meupet.user.usecase;

import br.com.start.meupet.agendamento.dto.servico.ServicoPrestadoRequestDTO;
import br.com.start.meupet.agendamento.enums.ServicoType;
import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.agendamento.repository.ServicoPrestadoRepository;
import br.com.start.meupet.agendamento.usecase.servico.UpdateServiceUseCase;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
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
public class FinalizaUltimosDadosDoUsuarioUseCaseTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private ServicoPrestadoRepository servicoPrestadoRepository;

    @InjectMocks
    private UpdateServiceUseCase updateServiceUseCase;

    @Test
    void shouldUpdateServiceSuccessfully() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Long servicoId = 1L;

        Partner partner = new Partner();
        partner.setId(partnerId);
        partner.setName("Partner Test");


        ServicoPrestado existingService = new ServicoPrestado();
        existingService.setId(servicoId);
        existingService.setName(ServicoType.CASTRACAO);
        existingService.setPrice("50.0");

        partner.setServicoPrestados(List.of(existingService));

        ServicoPrestadoRequestDTO updateRequest = new ServicoPrestadoRequestDTO("VACINAS", "150.0");

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

        // Act
        updateServiceUseCase.execute(partnerId, servicoId, updateRequest);

        // Assert
        assertEquals(ServicoType.VACINAS, existingService.getName());
        assertEquals("150.0", existingService.getPrice());
        verify(servicoPrestadoRepository, times(1)).save(existingService);
    }
    @Test
    void shouldThrowExceptionWhenPartnerNotFound() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Long servicoId = 1L;
        ServicoPrestadoRequestDTO updateRequest = new ServicoPrestadoRequestDTO("VACINAS", "150.0");

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProblemDetailsException.class, () ->
                updateServiceUseCase.execute(partnerId, servicoId, updateRequest)
        );
        verify(servicoPrestadoRepository, never()).save(any());
    }
    @Test
    void shouldThrowExceptionWhenServiceNotFound() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Long servicoId = 2L; // ID that does not match the existing service

        Partner partner = new Partner();
        partner.setId(partnerId);
        partner.setName("Partner Test");

        ServicoPrestado existingService = new ServicoPrestado();
        existingService.setId(1L); // Different ID
        existingService.setName(ServicoType.CASTRACAO);
        existingService.setPrice("50.0");

        partner.setServicoPrestados(List.of(existingService));

        ServicoPrestadoRequestDTO updateRequest = new ServicoPrestadoRequestDTO("VACINAS", "150.0");

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

        // Act & Assert
        assertThrows(ProblemDetailsException.class, () ->
                updateServiceUseCase.execute(partnerId, servicoId, updateRequest)
        );
        verify(servicoPrestadoRepository, never()).save(any());
    }
}
