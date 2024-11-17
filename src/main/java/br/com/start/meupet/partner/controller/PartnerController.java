package br.com.start.meupet.controllers;

import br.com.start.meupet.dto.PartnerRequestDTO;
import br.com.start.meupet.dto.PartnerResponseDTO;
import br.com.start.meupet.dto.UserResponseDTO;
import br.com.start.meupet.service.PartnerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(name = "/partner")
public class PartnerController {

    private static final Logger log = LoggerFactory.getLogger(PartnerController.class);

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    public ResponseEntity<List<PartnerResponseDTO>> listAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int items) {
        log.info("Requisicao GET: listando todos os parceiros");
        return ResponseEntity.ok(partnerService.listAll(page, items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerResponseDTO> listOne(@PathVariable UUID id) {
        log.info("Requisicao GET: listando um parceiros");
        PartnerResponseDTO partnerResponse = partnerService.getUserById(id);
        return ResponseEntity.ok().body(partnerResponse);
    }

    @PostMapping
    public ResponseEntity<PartnerResponseDTO> insertNewUser(@RequestBody @Valid PartnerRequestDTO partnerRequest) {
        PartnerResponseDTO partnerResponse = partnerService.insert(partnerRequest);
        log.info("Requisicao POST: inserindo um novo parceiro - {}", partnerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(partnerResponse);
    }

    @PutMapping
    public ResponseEntity<PartnerResponseDTO> update(@RequestParam UUID id, @RequestBody @Valid PartnerRequestDTO newPartner) {
        PartnerResponseDTO updatedPartner = partnerService.update(id, newPartner);
        log.info("Requisicao PUT: atualizando um parceiro já existente - {}", newPartner.toString());
        return ResponseEntity.ok().body(updatedPartner);
    }

    // http://localhost:8080/partner?id=3
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        partnerService.delete(id);
        log.info("Requisicao DELETE: deletando um parceiro");
        return ResponseEntity.noContent().build();
    }


}
