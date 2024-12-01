package br.com.start.meupet.agendamento.model;

import br.com.start.meupet.partner.model.Partner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "servico")
@Getter
@Setter
public class ServicoPrestado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "O nome do serviço é obrigatório.")
    private String name;

    @Column(length = 500)
    @Size(max = 500, message = "A descrição não pode ter mais que 500 caracteres.")
    private String description;

    @NotNull(message = "O preço do serviço é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero.")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_partner", referencedColumnName = "id")
    @JsonIgnore
    private Partner partner;

    public ServicoPrestado() {
    }

    public ServicoPrestado(String name, String description, BigDecimal price, Partner partner) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.partner = partner;
    }
}
