package br.com.start.meupet.agendamento.usecase.disponibilidade;

import br.com.start.meupet.agendamento.dto.disponibilidade.PartnerDisponibilidadeDTO;
import br.com.start.meupet.agendamento.model.Disponibilidade;
import br.com.start.meupet.common.enums.DocumentType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListAllPartnersAndAvailabilitiesUseCaseTest {
    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private ListAllPartnersAndAvailabilitiesUseCase listAllPartnersAndAvailabilitiesUseCase;

    @Test
    void shouldReturnAllPartnersAndTheirAvailabilitiesSuccessfully() {
        // Arrange
        Partner partner1 = new Partner();
        partner1.setId(UUID.randomUUID());
        partner1.setName("Partner 1");
        partner1.setEmail(new Email("aaaaa@gmail.com"));
        partner1.setPassword("321312312");
        partner1.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        partner1.setPersonalRegistration(new PersonalRegistration("11111111111111", DocumentType.CNPJ));
        Disponibilidade disponibilidade1 = new Disponibilidade();
        disponibilidade1.setId(1L);
        disponibilidade1.setStartTime(LocalTime.of(8, 0));
        disponibilidade1.setEndTime(LocalTime.of(17, 0));
        partner1.setDisponibilidades(List.of(disponibilidade1));

        Partner partner2 = new Partner();
        partner2.setId(UUID.randomUUID());
        partner2.setName("Partner 2");
        partner2.setEmail(new Email("aaaaa@gmail.com"));
        partner2.setPassword("321312312");
        partner2.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        partner2.setPersonalRegistration(new PersonalRegistration("11111111111111", DocumentType.CNPJ));
        Disponibilidade disponibilidade2 = new Disponibilidade();
        disponibilidade2.setId(2L);
        disponibilidade2.setStartTime(LocalTime.of(10, 0));
        disponibilidade2.setEndTime(LocalTime.of(15, 0));
        partner2.setDisponibilidades(List.of(disponibilidade2));

        when(partnerRepository.findAll()).thenReturn(List.of(partner1, partner2));

        // Act
        List<PartnerDisponibilidadeDTO> result = listAllPartnersAndAvailabilitiesUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verificar Partner 1
        assertEquals("Partner 1", result.get(0).getName());
        assertEquals(1, result.get(0).getDisponibilidades().size());
        assertEquals(LocalTime.of(8, 0), result.get(0).getDisponibilidades().get(0).openingHour());
        assertEquals(LocalTime.of(17, 0), result.get(0).getDisponibilidades().get(0).closingHour());

        // Verificar Partner 2
        assertEquals("Partner 2", result.get(1).getName());
        assertEquals(1, result.get(1).getDisponibilidades().size());
        assertEquals(LocalTime.of(10, 0), result.get(1).getDisponibilidades().get(0).openingHour());
        assertEquals(LocalTime.of(15, 0), result.get(1).getDisponibilidades().get(0).closingHour());

        verify(partnerRepository, times(1)).findAll();
    }
    @Test
    void shouldReturnEmptyListWhenNoPartnersExist() {
        // Arrange
        when(partnerRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<PartnerDisponibilidadeDTO> result = listAllPartnersAndAvailabilitiesUseCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(partnerRepository, times(1)).findAll();
    }
}
