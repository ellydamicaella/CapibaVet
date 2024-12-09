package br.com.start.meupet.agendamento.usecase.servico;

import br.com.start.meupet.agendamento.dto.servico.ServicoPrestadoRequestDTO;
import br.com.start.meupet.agendamento.enums.ServicoType;
import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.agendamento.repository.ServicoPrestadoRepository;
import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddServiceToPartnerUseCaseTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private ServicoPrestadoRepository servicoPrestadoRepository;

    @InjectMocks
    private AddServiceToPartnerUseCase addServiceToPartnerUseCase;

    @Test
    void shouldAddServicesToPartnerSuccessfully() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Partner partner = new Partner();
        partner.setId(partnerId);
        partner.setName("Partner Test");
        partner.setEmail(new Email("aaaaa@gmail.com"));
        partner.setPassword("321312312");
        partner.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        partner.setPersonalRegistration(new PersonalRegistration("11111111111111", DocumentType.CNPJ));
        List<ServicoPrestado> servicoPrestados=new ArrayList<>();
        partner.setServicoPrestados(servicoPrestados);

        List<ServicoPrestadoRequestDTO> servicos = List.of(
                new ServicoPrestadoRequestDTO("CASTRACAO", "100.0"),
                new ServicoPrestadoRequestDTO("VACINAS", "200.0")
        );

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

        // Act
        addServiceToPartnerUseCase.execute(partnerId, servicos);

        // Assert
        verify(partnerRepository, times(1)).findById(partnerId);
        verify(servicoPrestadoRepository, times(servicos.size())).save(any(ServicoPrestado.class));
        verify(partnerRepository, times(1)).save(partner);
    }
    @Test
    void shouldThrowExceptionWhenPartnerNotFound() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        List<ServicoPrestadoRequestDTO> servicos = List.of(
                new ServicoPrestadoRequestDTO("CASTRACAO", "100.0")
        );

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            addServiceToPartnerUseCase.execute(partnerId, servicos);
        });

        assertEquals("Not found", exception.getMessage());
        verify(partnerRepository, times(1)).findById(partnerId);
        verify(servicoPrestadoRepository, never()).save(any(ServicoPrestado.class));
    }
    @Test
    void shouldClearPreviousServicesBeforeAddingNewOnes() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Partner partner = new Partner();
        partner.setId(partnerId);
        partner.setName("Partner Test");

        ServicoPrestado existingService = new ServicoPrestado();
        existingService.setName(ServicoType.CASTRACAO);
        existingService.setPrice("50.0");

        partner.setServicoPrestados(new ArrayList<>(List.of(existingService)));

        List<ServicoPrestadoRequestDTO> servicos = List.of(
                new ServicoPrestadoRequestDTO("VACINAS", "150.0")
        );

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

        // Act
        addServiceToPartnerUseCase.execute(partnerId, servicos);

        // Assert
        assertEquals(0, partner.getServicoPrestados().size()); // Previous services cleared
        verify(servicoPrestadoRepository, times(1)).save(any(ServicoPrestado.class));
        verify(partnerRepository, times(1)).save(partner);
    }
}
