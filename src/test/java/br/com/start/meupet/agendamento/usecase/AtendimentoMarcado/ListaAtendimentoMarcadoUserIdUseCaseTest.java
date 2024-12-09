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
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListaAtendimentoMarcadoUserIdUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AtendimentoMarcadoRepository atendimentoMarcadoRepository;

    @InjectMocks
    private ListaAtendimentoMarcadoUserIdUseCase listaAtendimentoMarcadoUserIdUseCase;

    @Test
    void shouldReturnListOfAtendimentosSuccessfully() {
        // Arrange
        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setSocialName("User");
        user.setEmail(new Email("aaaaa@gmail.com"));
        user.setPassword("321312312");
        user.setDateOfBirth(BirthDayUtils.convertToDate("2000-12-01"));
        user.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        user.setPersonalRegistration(new PersonalRegistration("111.111.111-11", DocumentType.CPF));

        Partner partner = new Partner();
        partner.setId(UUID.randomUUID());
        partner.setName("Partner Test");
        partner.setEmail(new Email("aaaaa@gmail.com"));
        partner.setPassword("321312312");
        partner.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        partner.setPersonalRegistration(new PersonalRegistration("11.111.111/1111-11", DocumentType.CNPJ));


        ServicoPrestado servico = new ServicoPrestado();
        servico.setId(1L);
        servico.setName(ServicoType.VACINAS);
        servico.setPrice("100.0");

        Animal animal = new Animal();
        animal.setName("Dog");
        animal.setType(AnimalType.CACHORRO);
        animal.setSexo(AnimalSexo.F);
        AtendimentoMarcado atendimentoMarcado = new AtendimentoMarcado();
        atendimentoMarcado.setId(1L);
        atendimentoMarcado.setPartner(partner);
        atendimentoMarcado.setUser(user);
        atendimentoMarcado.setServicoPrestado(servico);
        atendimentoMarcado.setAnimal(animal);
        atendimentoMarcado.setStatus(AtendimentoStatus.PENDENTE);

        List<AtendimentoMarcado> atendimentos = List.of(atendimentoMarcado);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(atendimentoMarcadoRepository.findByUser(user)).thenReturn(atendimentos);

        // Act
        List<AtendimentoMarcadoDTO> result = listaAtendimentoMarcadoUserIdUseCase.execute(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(atendimentoMarcado.getId(), result.get(0).getId());
        assertEquals(user.getId(), result.get(0).getUser().getId());
        assertEquals(partner.getId(), result.get(0).getPartner().getId());
        assertEquals(servico.getId(), result.get(0).getServico().getId());
        assertEquals(animal.getName(), result.get(0).getAnimal().getName());

        verify(userRepository, times(1)).findById(userId);
        verify(atendimentoMarcadoRepository, times(1)).findByUser(user);
    }
    @Test
    void shouldThrowEntityNotFoundExceptionWhenUserDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                listaAtendimentoMarcadoUserIdUseCase.execute(userId)
        );

        assertEquals("Not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(atendimentoMarcadoRepository, never()).findByUser(any(User.class));
    }
    @Test
    void shouldReturnEmptyListWhenNoAtendimentosFound() {
        // Arrange
        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setId(userId);
        user.setName("John Doe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(atendimentoMarcadoRepository.findByUser(user)).thenReturn(List.of());

        // Act
        List<AtendimentoMarcadoDTO> result = listaAtendimentoMarcadoUserIdUseCase.execute(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userRepository, times(1)).findById(userId);
        verify(atendimentoMarcadoRepository, times(1)).findByUser(user);
    }
}
