package br.com.start.meupet.agendamento.usecase.AtendimentoMarcado;

import br.com.start.meupet.agendamento.dto.animal.AnimalRequestDTO;
import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoRequestDTO;
import br.com.start.meupet.agendamento.model.Animal;
import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.model.ServicoPrestado;
import br.com.start.meupet.agendamento.repository.AtendimentoMarcadoRepository;
import br.com.start.meupet.agendamento.repository.ServicoPrestadoRepository;
import br.com.start.meupet.agendamento.usecase.animal.AddNewAnimalToUserUseCase;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.exceptions.SchedulingConflictException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdicionarAtendimentoMarcadoUseCaseTest {

    @Mock
    private AtendimentoMarcadoRepository atendimentoMarcadoRepository;

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private ServicoPrestadoRepository servicoPrestadoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddNewAnimalToUserUseCase addNewAnimalToUserUseCase;

    @InjectMocks
    private AdicionarAtendimentoMarcadoUseCase adicionarAtendimentoMarcadoUseCase;

    @Test
    void shouldAddAppointmentSuccessfully() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Long serviceId = 10L;
        UUID userId = UUID.randomUUID();
        LocalDate appointmentDate = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 0);
        // Criação do AnimalRequestDTO
        AnimalRequestDTO animalRequest = new AnimalRequestDTO(
                "Rex", // Nome do animal
                "2", // Idade
                "Nenhum histórico médico", // Histórico
                "CACHORRO", // Tipo do animal
                "M" // Sexo
        );

        AtendimentoMarcadoRequestDTO request = new AtendimentoMarcadoRequestDTO(
                partnerId, serviceId, userId, animalRequest, appointmentDate, startTime, endTime
        );

        Partner partner = new Partner();
        partner.setId(partnerId);

        ServicoPrestado servico = new ServicoPrestado();
        servico.setId(serviceId);

        User user = new User();
        user.setId(userId);

        Animal animal = new Animal();

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));
        when(servicoPrestadoRepository.findById(serviceId)).thenReturn(Optional.of(servico));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addNewAnimalToUserUseCase.execute(userId, animalRequest)).thenReturn(animal);
        when(atendimentoMarcadoRepository.existsConflict(partnerId, appointmentDate, startTime, endTime)).thenReturn(false);

        // Act
        adicionarAtendimentoMarcadoUseCase.execute(request);

        // Assert
        verify(atendimentoMarcadoRepository, times(1)).save(any(AtendimentoMarcado.class));
    }
    @Test
    void shouldThrowExceptionWhenPartnerNotFound() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        AnimalRequestDTO animalRequest = new AnimalRequestDTO(
                "Rex", // Nome do animal
                "2", // Idade
                "Nenhum histórico médico", // Histórico
                "CACHORRO", // Tipo do animal
                "M" // Sexo
        );
        AtendimentoMarcadoRequestDTO request = new AtendimentoMarcadoRequestDTO(
                partnerId, 10L, UUID.randomUUID(), animalRequest, LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(1)
        );

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> adicionarAtendimentoMarcadoUseCase.execute(request));
        verify(atendimentoMarcadoRepository, never()).save(any());
    }
    @Test
    void shouldThrowExceptionWhenServiceNotFound() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Long serviceId = 10L;
        AnimalRequestDTO animalRequest = new AnimalRequestDTO(
                "Rex", // Nome do animal
                "2", // Idade
                "Nenhum histórico médico", // Histórico
                "CACHORRO", // Tipo do animal
                "M" // Sexo
        );
        AtendimentoMarcadoRequestDTO request = new AtendimentoMarcadoRequestDTO(
                partnerId, 10L, UUID.randomUUID(), animalRequest, LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(1)
        );

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(new Partner()));
        when(servicoPrestadoRepository.findById(serviceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> adicionarAtendimentoMarcadoUseCase.execute(request));
        verify(atendimentoMarcadoRepository, never()).save(any());
    }
    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID partnerId = UUID.randomUUID();
        AnimalRequestDTO animalRequest = new AnimalRequestDTO(
                "Rex", // Nome do animal
                "2", // Idade
                "Nenhum histórico médico", // Histórico
                "CACHORRO", // Tipo do animal
                "M" // Sexo
        );
        AtendimentoMarcadoRequestDTO request = new AtendimentoMarcadoRequestDTO(
                partnerId, 10L, userId, animalRequest, LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(1)
        );

        when(partnerRepository.findById(any())).thenReturn(Optional.of(new Partner()));
        when(servicoPrestadoRepository.findById(any())).thenReturn(Optional.of(new ServicoPrestado()));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> adicionarAtendimentoMarcadoUseCase.execute(request));
        verify(atendimentoMarcadoRepository, never()).save(any());
    }
    @Test
    void shouldThrowExceptionWhenSchedulingConflictExists() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        AnimalRequestDTO animalRequest = new AnimalRequestDTO(
                "Rex", // Nome do animal
                "2", // Idade
                "Nenhum histórico médico", // Histórico
                "CACHORRO", // Tipo do animal
                "M" // Sexo
        );
        AtendimentoMarcadoRequestDTO request = new AtendimentoMarcadoRequestDTO(
                partnerId, 10L, UUID.randomUUID(), animalRequest, LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(1)
        );
        Partner partner = new Partner();
        partner.setId(partnerId);


        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));
        when(servicoPrestadoRepository.findById(any())).thenReturn(Optional.of(new ServicoPrestado()));
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(atendimentoMarcadoRepository.existsConflict(
                eq(request.partnerId()),
                eq(request.appointmentDate()),
                eq(request.startTime()),
                eq(request.endTime())
        )).thenReturn(true);

        // Act & Assert
        assertThrows(SchedulingConflictException.class, () -> adicionarAtendimentoMarcadoUseCase.execute(request));
        verify(atendimentoMarcadoRepository, never()).save(any());
    }
}
