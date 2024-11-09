package br.com.start.meupet.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_verify")
public class VerifyAuthenticableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private UUID uuid;

    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", unique = true)
    private User user;

    public VerifyAuthenticableEntity() {
    }

    public VerifyAuthenticableEntity(UUID uuid, LocalDateTime expirationDate, User user) {
        this.uuid = uuid;
        this.expirationDate = expirationDate;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
