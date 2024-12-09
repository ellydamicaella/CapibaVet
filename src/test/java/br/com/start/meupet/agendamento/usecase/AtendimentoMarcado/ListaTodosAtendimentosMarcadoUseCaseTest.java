package br.com.start.meupet.agendamento.usecase.AtendimentoMarcado;

import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoDTO;
import br.com.start.meupet.agendamento.enums.AnimalSexo;
import br.com.start.meupet.agendamento.enums.AnimalType;
import br.com.start.meupet.agendamento.enums.AtendimentoStatus;
import br.com.start.meupet.agendamento.enums.ServicoType;
import br.com.start.meupet.agendamento.model.Animal;
import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.agendamento.repository.AtendimentoMarcadoRepository;
import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListaTodosAtendimentosMarcadoUseCaseTest {

    @Mock
    private AtendimentoMarcadoRepository atendimentoMarcadoRepository;

    @InjectMocks
    private ListaTodosAtendimentosMarcadoUseCase listaTodosAtendimentosMarcadoUseCase;

    @Test
    void shouldReturnListOfAtendimentosSuccessfully() {
        // Arrange
        Partner partner = new Partner();
        partner.setId(UUID.randomUUID());
        partner.setName("Partner Test");
        partner.setEmail(new Email("aaaaa@gmail.com"));
        partner.setPassword("321312312");
        partner.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        partner.setPersonalRegistration(new PersonalRegistration("11.111.111/1111-11", DocumentType.CNPJ));

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("John Doe");
        user.setEmail(new Email("aaaaa@gmail.com"));
        user.setPassword("321312312");
        user.setDateOfBirth(BirthDayUtils.convertToDate("2000-12-01"));
        user.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        user.setPersonalRegistration(new PersonalRegistration("111.111.111-11", DocumentType.CPF));

        ServicoPrestado servico = new ServicoPrestado();
        servico.setId(1L);
        servico.setName(ServicoType.VACINAS);
        servico.setPrice("100.0");

        Animal animal = new Animal();
        animal.setName("Dog");
        animal.setType(AnimalType.CACHORRO);
        animal.setSexo(AnimalSexo.M);

        AtendimentoMarcado atendimentoMarcado = new AtendimentoMarcado();
        atendimentoMarcado.setId(1L);
        atendimentoMarcado.setPartner(partner);
        atendimentoMarcado.setUser(user);
        atendimentoMarcado.setServicoPrestado(servico);
        atendimentoMarcado.setAnimal(animal);
        atendimentoMarcado.setStatus(AtendimentoStatus.PENDENTE);

        List<AtendimentoMarcado> atendimentos = List.of(atendimentoMarcado);

        when(atendimentoMarcadoRepository.findAll()).thenReturn(atendimentos);

        // Act
        List<AtendimentoMarcadoDTO> result = listaTodosAtendimentosMarcadoUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(atendimentoMarcado.getId(), result.get(0).getId());
        assertEquals(user.getId(), result.get(0).getUser().getId());
        assertEquals(partner.getId(), result.get(0).getPartner().getId());
        assertEquals(servico.getId(), result.get(0).getServico().getId());
        assertEquals(animal.getName(), result.get(0).getAnimal().getName());

        verify(atendimentoMarcadoRepository, times(1)).findAll();
    }
    @Test
    void shouldReturnEmptyListWhenNoAtendimentosFound() {
        // Arrange
        when(atendimentoMarcadoRepository.findAll()).thenReturn(List.of());

        // Act
        List<AtendimentoMarcadoDTO> result = listaTodosAtendimentosMarcadoUseCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(atendimentoMarcadoRepository, times(1)).findAll();
    }
}
