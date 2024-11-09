package br.com.start.meupet.domain.repository;

import br.com.start.meupet.domain.entities.VerifyAuthenticableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VerifyAuthenticableEntityRepository extends JpaRepository<VerifyAuthenticableEntity, Long> {
    VerifyAuthenticableEntity findByUuid(UUID uuid);
}
