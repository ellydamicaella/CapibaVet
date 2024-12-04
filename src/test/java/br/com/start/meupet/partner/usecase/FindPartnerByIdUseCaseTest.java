package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.dto.PartnerResponseDTO;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindPartnerByIdUseCaseTest {

    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private FindPartnerByIdUseCase findPartnerByIdUseCase;

    @Test
    @DisplayName("Should find user successfully")
    void listUsersCase1() {
        List<Partner> partners = createPartners();

        when(partnerRepository.findById(partners.getFirst().getId())).thenReturn(Optional.ofNullable(partners.getFirst()));

        PartnerResponseDTO result = findPartnerByIdUseCase.execute(partners.getFirst().getId());

        Assertions.assertEquals(partners.getFirst().getName(), result.getName());

        verify(partnerRepository, times(1)).findById(partners.getFirst().getId());
    }

    @Test
    @DisplayName("Should empty list successfully")
    void listUsersCase2() {
        UUID uuid = UUID.randomUUID();

        when(partnerRepository.findById(uuid)).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> findPartnerByIdUseCase.execute(uuid)
        );

        Assertions.assertEquals("Not found", exception.getMessage());
        verify(partnerRepository, times(1)).findById(uuid);
    }

    List<Partner> createPartners() {
        UUID partnerId = UUID.randomUUID();
        UUID partnerId2 = UUID.randomUUID();

        Partner partner1 = new Partner();
        partner1.setId(partnerId);
        partner1.setName("Partner1");
        partner1.setEmail(new Email("aaaaa@gmail.com"));
        partner1.setPassword("321312312");
        partner1.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        partner1.setPersonalRegistration(new PersonalRegistration("11111111111", DocumentType.CPF));

        Partner partner2 = new Partner();
        partner2.setId(partnerId2);
        partner2.setName("Partner2");
        partner2.setEmail(new Email("aaaaa@gmail.com"));
        partner2.setPassword("321312312");
        partner2.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        partner2.setPersonalRegistration(new PersonalRegistration("11111111111", DocumentType.CPF));

        return List.of(partner1, partner2);
    }