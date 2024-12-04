package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.common.enums.DocumentType;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListPartnersUseCaseTest {

    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private ListPartnersUseCase listPartnersUseCase;

    @Test
    @DisplayName("Should list partners successfully")
    void listPartnersCase1() {
        // Arrange
        int page = 0;
        int pageSize = 2;

        List<Partner> users = createPartners();
        Page<Partner> userPage = new PageImpl<>(users, PageRequest.of(page, pageSize), users.size());

        when(partnerRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        // Act
        List<PartnerResponseDTO> result = listPartnersUseCase.execute(page, pageSize);

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Partner1", result.get(0).getName());
        Assertions.assertEquals("Partner2", result.get(1).getName());

        verify(partnerRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should empty list successfully")
    void listUsersCase2() {
        int page = 0;
        int pageSize = 2;

        List<Partner> partners = List.of();
        Page<Partner> partnerPage = new PageImpl<>(partners, PageRequest.of(page, pageSize), 0);

        when(partnerRepository.findAll(any(Pageable.class))).thenReturn(partnerPage);

        List<PartnerResponseDTO> result = listPartnersUseCase.execute(page, pageSize);

        Assertions.assertEquals(0, result.size());
        verify(partnerRepository, times(1)).findAll(any(Pageable.class));
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
}
