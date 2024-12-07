package br.com.start.meupet.partner.controller;

import br.com.start.meupet.agendamento.usecase.disponibilidade.AddAvailabilityToPartnerUseCase;
import br.com.start.meupet.agendamento.usecase.servico.AddServiceToPartnerUseCase;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.dto.*;
import br.com.start.meupet.partner.facade.PartnerFacade;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/partner")
@CrossOrigin
public class PartnerController {

    private static final Logger log = LoggerFactory.getLogger(PartnerController.class);

    private final PartnerFacade partnerFacade;
    private final PartnerRepository partnerRepository;
    private final AddServiceToPartnerUseCase addServiceToPartnerUseCase;
    private final AddAvailabilityToPartnerUseCase addAvailabilityToPartnerUseCase;

    public PartnerController(PartnerFacade partnerFacade, PartnerRepository partnerRepository, AddServiceToPartnerUseCase addServiceToPartnerUseCase, AddAvailabilityToPartnerUseCase addAvailabilityToPartnerUseCase) {
        this.partnerFacade = partnerFacade;
        this.partnerRepository = partnerRepository;
        this.addServiceToPartnerUseCase = addServiceToPartnerUseCase;
        this.addAvailabilityToPartnerUseCase = addAvailabilityToPartnerUseCase;
    }

    @GetMapping
    public ResponseEntity<List<PartnerResponseDTO>> listAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int items) {
        log.info("Requisicao GET: listando todos os parceiros");
        return ResponseEntity.ok(partnerFacade.listAll(page, items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerResponseDTO> listOne(@PathVariable UUID id) {
        log.info("Requisicao GET: listando um parceiros");
        PartnerResponseDTO partnerResponse = partnerFacade.getPartnerById(id);
        return ResponseEntity.ok().body(partnerResponse);
    }

    @PostMapping
    public ResponseEntity<PartnerResponseDTO> insertNewUser(@RequestBody @Valid PartnerRequestDTO partnerRequest) {
        PartnerResponseDTO partnerResponse = partnerFacade.insert(partnerRequest);
        log.info("Requisicao POST: inserindo um novo parceiro - {}", partnerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(partnerResponse);
    }

    @PatchMapping("/{partnerId}")
    public ResponseEntity<Void> updatePartner(@PathVariable UUID partnerId, @RequestBody PartnerUpdateDTO partnerRequest) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new EntityNotFoundException("Parceiro não encontrado com ID: " + partnerId));

        // Atualiza apenas os campos não nulos do DTO
        if (partnerRequest.getName() != null) {
            partner.setName(partnerRequest.getName());
        }
        if (partnerRequest.getPhoneNumber() != null) {
            partner.setPhoneNumber(new PhoneNumber(partnerRequest.getPhoneNumber()));
        }
        if (partnerRequest.getStreetAndNumber() != null) {
            partner.setStreetAndNumber(partnerRequest.getStreetAndNumber());
        }
        if (partnerRequest.getNeighborhood() != null) {
            partner.setNeighborhood(partnerRequest.getNeighborhood());
        }

        partnerRepository.save(partner); // Persistindo as alterações no banco
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<PartnerResponseDTO> update(@RequestParam UUID id, @RequestBody @Valid PartnerRequestDTO newPartner) {
        PartnerResponseDTO updatedPartner = partnerFacade.update(id, newPartner);
        log.info("Requisicao PUT: atualizando um parceiro já existente - {}", newPartner.toString());
        return ResponseEntity.ok().body(updatedPartner);
    }

    // http://localhost:8080/partner?id=3
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam UUID id) {
        partnerFacade.delete(id);
        log.info("Requisicao DELETE: deletando um parceiro");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/service/disponibilidade/{partnerId}")
    public ResponseEntity<Void> addServicesAndAvailability(@PathVariable UUID partnerId, @RequestBody ServicoListAndAvailabilityRequestDTO request) {
        addServiceToPartnerUseCase.execute(partnerId, request.services());
        addAvailabilityToPartnerUseCase.execute(partnerId, request.disponibilidade());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/agendamento/{partnerId}")
    public ResponseEntity<PartnerDTO> getAllForAgendamento(@PathVariable UUID partnerId) {
        Partner partner = partnerRepository.findById(partnerId).orElseThrow();
        PartnerDTO partnerDTO = new PartnerDTO(partner);
        return ResponseEntity.ok().body(partnerDTO);
    }

}
