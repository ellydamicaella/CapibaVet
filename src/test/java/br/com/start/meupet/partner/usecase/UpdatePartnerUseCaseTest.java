package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.service.ServiceUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.dto.PartnerRequestDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdatePartnerUseCaseTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private ServiceUtils serviceUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UpdatePartnerUseCase updatePartnerUseCase;

    @Test
    @DisplayName("Deve atualizar o parceiro com sucesso")
    void shouldUpdatePartnerSuccessfully() {
        UUID partnerId = UUID.randomUUID();
        Partner existingPartner =  getExistingPartnerAllIsOk(partnerId);
        PartnerRequestDTO newPartner = getPartnerRequestDTOAllIsOk();
        Partner updatedPartner = getUpdatedPartnerAllIsOk(partnerId, newPartner);

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(existingPartner));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
        when(partnerRepository.save(any(Partner.class))).thenReturn(updatedPartner);

        PartnerResponseDTO result = updatePartnerUseCase.execute(partnerId, newPartner);

        Assertions.assertEquals("New Name", result.getName());
        verify(partnerRepository, times(1)).findById(partnerId);
        verify(serviceUtils, times(1)).isPartnerAlreadyExists(existingPartner);
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(partnerRepository, times(1)).save(any(Partner.class));
    }


    @Test
    @DisplayName("Deve lançar exceção se o parceiro não for encontrado")
    void shouldThrowExceptionIfPartnerNotFound() {
        UUID partnerId = UUID.randomUUID();
        PartnerRequestDTO newPartner = new PartnerRequestDTO();

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> updatePartnerUseCase.execute(partnerId, newPartner)
        );

        Assertions.assertEquals("Not found", exception.getMessage());
        verify(partnerRepository, times(1)).findById(partnerId);
        verifyNoInteractions(serviceUtils);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("Deve validar e criptografar a senha corretamente")
    void shouldValidateAndEncodePassword() {
        // Arrange
        UUID partnerId = UUID.randomUUID();
        Partner existingPartner = getExistingPartnerAllIsOk(partnerId);

        PartnerRequestDTO newPartner = getPartnerRequestDTOAllIsOk();

        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(existingPartner));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        updatePartnerUseCase.execute(partnerId, newPartner);

        verify(passwordEncoder, times(1)).encode("newPassword");
    }

    Partner getExistingPartnerAllIsOk(UUID partnerId) {
        Partner existingPartner = new Partner();
        existingPartner.setId(partnerId);
        existingPartner.setName("Old Name");
        existingPartner.setPassword("oldPassword");
        existingPartner.setEmail(new Email("aaaaa@gmail.com"));
        existingPartner.setPhoneNumber(new PhoneNumber("(21) 22222-2222"));
        existingPartner.setPersonalRegistration(new PersonalRegistration("11.111.111/1111-11", DocumentType.CNPJ));
        return existingPartner;
    }

    PartnerRequestDTO getPartnerRequestDTOAllIsOk() {
        PartnerRequestDTO newPartner = new PartnerRequestDTO();
        newPartner.setName("New Name");
        newPartner.setPassword("newPassword");
        newPartner.setEmail("aaaaa@gmail.com");
        newPartner.setPhoneNumber("(21) 22222-2222");
        newPartner.setDocument("11.111.111/1111-11");
        newPartner.setDocumentType("CNPJ");
        return newPartner;
    }

    Partner getUpdatedPartnerAllIsOk(UUID partnerId, PartnerRequestDTO newPartner) {
        Partner updatedPartner = new Partner();
        updatedPartner.setId(partnerId);
        updatedPartner.setName(newPartner.getName());
        updatedPartner.setPassword("encodedPassword");
        updatedPartner.setEmail(new Email(newPartner.getEmail()));
        updatedPartner.setPhoneNumber(new PhoneNumber(newPartner.getPhoneNumber()));
        updatedPartner.setPersonalRegistration(new PersonalRegistration(newPartner.getDocument(), DocumentType.valueOf(newPartner.getDocumentType())));
        return updatedPartner;
    }
}
