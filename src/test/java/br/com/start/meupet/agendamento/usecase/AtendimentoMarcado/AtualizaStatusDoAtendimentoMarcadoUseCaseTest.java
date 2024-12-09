package br.com.start.meupet.agendamento.usecase.AtendimentoMarcado;

import br.com.start.meupet.agendamento.enums.AtendimentoStatus;
import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.repository.AtendimentoMarcadoRepository;
import br.com.start.meupet.common.exceptions.ForbiddenActionException;
import br.com.start.meupet.common.exceptions.StatusInvalidException;
import br.com.start.meupet.partner.model.Partner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtualizaStatusDoAtendimentoMarcadoUseCaseTest {

    @Mock
    private AtendimentoMarcadoRepository atendimentoMarcadoRepository;

    @InjectMocks
    private AtualizaStatusDoAtendimentoMarcadoUseCase atualizaStatusDoAtendimentoMarcadoUseCase;

    @Test
    void shouldUpdateStatusSuccessfully() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Long atendimentoMarcadoId = 1L;
        String status = "CONFIRMADO";

        Partner partner = new Partner();
        partner.setId(partnerId);

        AtendimentoMarcado atendimentoMarcado = new AtendimentoMarcado();
        atendimentoMarcado.setId(atendimentoMarcadoId);
        atendimentoMarcado.setPartner(partner);
        atendimentoMarcado.setStatus(AtendimentoStatus.PENDENTE);

        when(atendimentoMarcadoRepository.findById(atendimentoMarcadoId)).thenReturn(Optional.of(atendimentoMarcado));

        // Act
        atualizaStatusDoAtendimentoMarcadoUseCase.execute(partnerId, atendimentoMarcadoId, status);

        // Assert
        assertEquals(AtendimentoStatus.CONFIRMADO, atendimentoMarcado.getStatus());
        verify(atendimentoMarcadoRepository, times(1)).save(atendimentoMarcado);
    }
    @Test
    void shouldThrowStatusInvalidExceptionWhenStatusIsInvalid() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Long atendimentoMarcadoId = 1L;
        String invalidStatus = "INVALIDO";

        // Act & Assert
        StatusInvalidException exception = assertThrows(StatusInvalidException.class, () ->
                atualizaStatusDoAtendimentoMarcadoUseCase.execute(partnerId, atendimentoMarcadoId, invalidStatus)
        );

        assertEquals("Bad request", exception.getMessage());
        verifyNoInteractions(atendimentoMarcadoRepository);
    }
    @Test
    void shouldThrowForbiddenActionExceptionWhenPartnerDoesNotOwnAtendimento() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        UUID anotherPartnerId = UUID.randomUUID();
        Long atendimentoMarcadoId = 1L;
        String status = "CANCELADO";

        Partner anotherPartner = new Partner();
        anotherPartner.setId(anotherPartnerId);

        AtendimentoMarcado atendimentoMarcado = new AtendimentoMarcado();
        atendimentoMarcado.setId(atendimentoMarcadoId);
        atendimentoMarcado.setPartner(anotherPartner);
        atendimentoMarcado.setStatus(AtendimentoStatus.PENDENTE);

        when(atendimentoMarcadoRepository.findById(atendimentoMarcadoId)).thenReturn(Optional.of(atendimentoMarcado));

        // Act & Assert
        ForbiddenActionException exception = assertThrows(ForbiddenActionException.class, () ->
                atualizaStatusDoAtendimentoMarcadoUseCase.execute(partnerId, atendimentoMarcadoId, status)
        );

        assertEquals("Forbidden", exception.getMessage());
        verify(atendimentoMarcadoRepository, never()).save(any(AtendimentoMarcado.class));
    }
}
