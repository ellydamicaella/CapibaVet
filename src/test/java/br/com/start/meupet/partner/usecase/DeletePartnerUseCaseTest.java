package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class DeletePartnerUseCaseTest {

    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private DeletePartnerUseCase deletePartnerUseCase;

    @Test
    void shouldDeleteUserSuccessfully() {
        UUID partnerId = UUID.randomUUID();
        Partner partner = new Partner();
        partner.setId(partnerId);
        partner.setName("John Doe");

        Mockito.when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));
        Mockito.doNothing().when(partnerRepository).delete(partner);

        deletePartnerUseCase.execute(partnerId);

        Mockito.verify(partnerRepository).findById(partnerId);
        Mockito.verify(partnerRepository).delete(partner);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UUID partnerId = UUID.randomUUID();

        Mockito.when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> deletePartnerUseCase.execute(partnerId));

        Assertions.assertEquals("Not found", exception.getMessage());
        Mockito.verify(partnerRepository).findById(partnerId);
        Mockito.verifyNoMoreInteractions(partnerRepository); // Garante que o método delete não foi chamado
    }

}
